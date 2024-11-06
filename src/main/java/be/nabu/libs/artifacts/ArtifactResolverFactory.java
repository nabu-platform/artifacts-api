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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import be.nabu.libs.artifacts.api.ArtifactResolver;

public class ArtifactResolverFactory {

	private static ArtifactResolverFactory instance;
	
	public static ArtifactResolverFactory getInstance() {
		if (instance == null)
			instance = new ArtifactResolverFactory();
		return instance;
	}
	
	private List<ArtifactResolver<?>> resolvers = new ArrayList<ArtifactResolver<?>>();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArtifactResolver<?> getResolver() {
		if (resolvers.isEmpty()) {
			try {
				// let's try this with custom service loading based on a configuration
				Class<?> clazz = getClass().getClassLoader().loadClass("be.nabu.utils.services.ServiceLoader");
				Method declaredMethod = clazz.getDeclaredMethod("load", Class.class);
				resolvers.addAll((List<ArtifactResolver<?>>) declaredMethod.invoke(null, ArtifactResolver.class));
			}
			catch (ClassNotFoundException e) {
				// ignore, the framework is not present
			}
			catch (NoSuchMethodException e) {
				// corrupt framework?
				throw new RuntimeException(e);
			}
			catch (SecurityException e) {
				throw new RuntimeException(e);
			}
			catch (IllegalAccessException e) {
				// ignore
			}
			catch (InvocationTargetException e) {
				// ignore
			}
			if (resolvers.isEmpty()) {
				ServiceLoader<ArtifactResolver> serviceLoader = ServiceLoader.load(ArtifactResolver.class);
				for (ArtifactResolver<?> resolver : serviceLoader) {
					resolvers.add(resolver);
				}
			}
		}
		return new MultipleArtifactResolver(resolvers);
	}
	
	public void addResolver(ArtifactResolver<?> resolver) {
		resolvers.add(resolver);
	}
	
	public void removeResolver(ArtifactResolver<?> resolver) {
		resolvers.remove(resolver);
	}
	
	@SuppressWarnings("unused")
	private void activate() {
		instance = this;
	}
	@SuppressWarnings("unused")
	private void deactivate() {
		instance = null;
	}
}
