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

package be.nabu.libs.artifacts.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import be.nabu.libs.artifacts.LocalClassLoader;

public interface ClassProvidingArtifact extends Artifact {
	public List<Class<?>> getImplementationsFor(Class<?> clazz) throws IOException;
	public Class<?> loadClass(String id) throws ClassNotFoundException;
	public InputStream loadResource(String id) throws IOException;
	public Collection<LocalClassLoader> getClassLoaders();
}
