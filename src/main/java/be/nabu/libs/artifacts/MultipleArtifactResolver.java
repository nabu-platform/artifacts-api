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
