/*
* Copyright (C) 2014 Alexander Verbruggen
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

package be.nabu.libs.artifacts;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * This classloader achieves two purposes:
 * 
 * - perform local lookup before global lookup to allow local versions of classes
 * - allow non-recursive lookup of classes and resources that does not chain back to the parent
 * 
 * The latter is important because someone has an overview of all the artifacts and as such of all the classloaders that are available
 * If we can not find it in this classloader, we ask the parent, who in turn will ask all the classloaders it knows, including this one
 * It will ask so in a non-recursive manner though so we don't get stuck in infinite recursion
 */
abstract public class LocalClassLoader extends ClassLoader {

	private static boolean registered;
	private List<String> resourceWhitelist, resourceBlacklist;

	static {
		ClassLoader.registerAsParallelCapable();
	}
	
	public static void register() {
		if (!registered) {
			synchronized(LocalClassLoaderURLStreamHandlerFactory.class){
				if (!registered) {
					URL.setURLStreamHandlerFactory(new LocalClassLoaderURLStreamHandlerFactory());
					registered = true;
				}
			}
		}
	}
	
	/**
	 * The findLoadedClass keeps track of successfully loaded classes, we also want to keep track of failed loaded classes so we can simply skip to parent
	 */
	private Set<String> failed = new HashSet<String>();

	private String id = UUID.randomUUID().toString().replace("-", "");
	
	private static List<WeakReference<LocalClassLoader>> loaders = new ArrayList<WeakReference<LocalClassLoader>>();
	
	public LocalClassLoader(ClassLoader parent) {
		super(parent);
		// register this in the known loaders
		synchronized(loaders) {
			loaders.add(new WeakReference<LocalClassLoader>(this));
		}
	}
	
	abstract protected Collection<String> findFiles(String fileName, boolean stopAfterFirst);
	abstract protected byte [] readFile(String fileName);
	
	final public Class<?> loadClassNonRecursively(String name) throws ClassNotFoundException {
		return loadClass(name, false, false);
	}
	
	@Override
	final protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		return loadClass(name, resolve, true);
	}
	
	private Class<?> loadClass(String name, boolean resolve, boolean recurse) throws ClassFormatError, ClassNotFoundException {
		// don't load system classes
		if (name.startsWith("java.") || (name.startsWith("com.sun.") && !name.startsWith("com.sun.jna")) || name.startsWith("sun.") || name.startsWith("javax.xml")) { // name.startsWith("javax.")
			return recurse ? getParent().loadClass(name) : null;
		}
		synchronized (getClassLoadingLock(name)) {
			Class<?> clazz = findLoadedClass(name);
			if (clazz == null) {
				// non-synchronized lookup
				if (failed.contains(name)) {
					return recurse ? getParent().loadClass(name) : null;
				}
				else {
					synchronized(failed) {
						failed.add(name);
					}
					// first search locally (allow different versions of shared libraries)
					try {
						clazz = findClass(name);
					}
					catch (ClassNotFoundException e) {
						// not found, try parent
					}
					if (clazz == null && recurse) {
						clazz = getParent().loadClass(name);
					}
				}
			}
			if (clazz != null && resolve) {
				resolveClass(clazz);
			}
			return clazz;
		}
	}
	
	@Override
	final protected Class<?> findClass(String name) throws ClassNotFoundException {
		Collection<String> findFiles = findFiles(name.replace('.', '/') + ".class", true);
		byte [] content = findFiles.isEmpty() ? null : readFile(findFiles.iterator().next());
		if (content == null) {
			throw new ClassNotFoundException("Could not find " + name);
		}
		else {
			int index = name.lastIndexOf('.');
			if (index >= 0) {
				String packageName = name.substring(0, index);
				if (getPackage(packageName) == null) {
					definePackage(packageName, null, null, null, null, null, null, null);
				}
			}
			return defineClass(name, content, 0, content.length);
		}
	}
	
	final public String getId() {
		return id;
	}

	@Override
	final public URL getResource(String name) {
		// first try to find locally
		Collection<URL> localResources = findResourcesNonRecursively(name, true);
		// then try the parent if necessary
		return localResources.isEmpty() ? getParent().getResource(name) : localResources.iterator().next();
	}
	
	final public Collection<URL> findResourcesNonRecursively(String name, boolean stopAfterFirst) {
		List<URL> urls = new ArrayList<URL>();
		if (resourceWhitelist != null && !resourceWhitelist.isEmpty()) {
			boolean allow = false;
			for (String regex : resourceWhitelist) {
				if (name.matches(regex)) {
					allow = true;
					break;
				}
			}
			if (!allow) {
				return urls;
			}
		}
		if (resourceBlacklist != null && !resourceBlacklist.isEmpty()) {
			boolean allow = true;
			for (String regex : resourceBlacklist) {
				if (name.matches(regex)) {
					allow = false;
					break;
				}
			}
			if (!allow) {
				return urls;
			}
		}
		for (String fileName : findFiles(name, stopAfterFirst)) {
			try {
				urls.add(new URL(LocalClassLoader.class.getName() + "://" + getId() + "/" + fileName));
			}
			catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
		}
		return urls;
	}
	
	@Override
	final public Enumeration<URL> getResources(String name) throws IOException {
		Set<URL> resources = new LinkedHashSet<URL>();
		resources.addAll(findResourcesNonRecursively(name, false));
		Enumeration<URL> parentResources = getParent().getResources(name);
		if (parentResources != null) {
			while (parentResources.hasMoreElements()) {
				resources.add(parentResources.nextElement());
			}
		}
		return Collections.enumeration(resources);
	}

	@Override
	final public InputStream getResourceAsStream(String name) {
		Collection<String> findFiles = findFiles(name, true);
		byte [] content = findFiles.isEmpty() ? null : readFile(findFiles.iterator().next());
		return content == null ? getParent().getResourceAsStream(name) : new ByteArrayInputStream(content);
	}
	
	public static class LocalClassLoaderURLStreamHandlerFactory implements URLStreamHandlerFactory {
		@Override
		public URLStreamHandler createURLStreamHandler(String protocol) {
			if (LocalClassLoader.class.getName().equalsIgnoreCase(protocol)) {
				return new URLStreamHandler() {
					@Override
					protected URLConnection openConnection(URL url) throws IOException {
						return new URLConnection(url) {
							@Override
							public void connect() throws IOException {
								// do nothing
							}
							@Override
							public InputStream getInputStream() throws IOException {
								// the authority indicates which classloader it comes from
								String authority = url.getAuthority();
								for (WeakReference<LocalClassLoader> reference : loaders) {
									LocalClassLoader localClassLoader = reference.get();
									// has been gc-ed
									if (localClassLoader == null) {
										continue;
									}
									else if (localClassLoader.getId().equals(authority)) {
										String path = url.getPath();
										if (path.startsWith("/")) {
											path = path.substring(1);
										}
										byte[] bytes = localClassLoader.readFile(path);
										if (bytes == null) {
											throw new FileNotFoundException("Can not find the file: " + url);
										}
										return new ByteArrayInputStream(bytes);
									}
								}
								throw new FileNotFoundException("Can not find the loader for: " + url);
							}
						};
					}
				};
			}
			return null;
		}
	}

	public List<String> getResourceWhitelist() {
		return resourceWhitelist;
	}

	public void setResourceWhitelist(List<String> resourceWhitelist) {
		this.resourceWhitelist = resourceWhitelist;
	}

	public List<String> getResourceBlacklist() {
		return resourceBlacklist;
	}

	public void setResourceBlacklist(List<String> resourceBlacklist) {
		this.resourceBlacklist = resourceBlacklist;
	}
}
