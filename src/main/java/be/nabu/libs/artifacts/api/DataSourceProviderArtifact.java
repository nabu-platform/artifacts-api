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

import javax.sql.DataSource;

import be.nabu.libs.artifacts.api.Artifact;

public interface DataSourceProviderArtifact extends Artifact {
	public DataSource getDataSource();
	public boolean isAutoCommit();
	// the context for the data source provider
	// based on the context we can do better matching for which provider to use when
	public default String getContext() { return null; }
}
