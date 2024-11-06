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

import java.net.URI;
import java.util.List;

public interface ExternalDependency {
	// the artifact this dependency belongs to
	public String getArtifactId();
	// the type of dependency, e.g. DATABASE, FILESERVER, API...
	public String getType();
	// the main endpoint which contains the protocol, host, port, optional path,...
	public URI getEndpoint();
	// some protocols have alternative endpoints due to client failover
	public List<URI> getAlternativeEndpoints();
	// relevant credentials (not meant to include secrets though)
	public String getCredentials();
	// if the external dependency has a global identifier we can reference
	public String getId();
	// you can group multiple dependencies together. for example if you call 5 different endpoints on the same target server, we can group them with a logical name
	public String getGroup();
	// this is mostly relevant for protocols that can share an endpoint but differentiate on some other criteria, in http this is based on method.
	public String getMethod();
	// free description
	public String getDescription();
}
