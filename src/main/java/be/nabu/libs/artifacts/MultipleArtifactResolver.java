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

import java.util.List;

import be.nabu.libs.artifacts.api.Artifact;
import be.nabu.libs.artifacts.api.ArtifactResolver;

public class MultipleArtifactResolver implements ArtifactResolver<Artifact> {

	private List<ArtifactResolver<?>> resolvers;
	
	public MultipleArtifactResolver(List<ArtifactResolver<?>> resolvers) {
		this.resolvers = resolvers;
	}
	
	@Override
	public Artifact resolve(String id) {
		Artifact artifact = null;
		for (ArtifactResolver<?> resolver : resolvers) {
			artifact = resolver.resolve(id);
			if (artifact != null)
				break;
		}
		return artifact;
	}

}
