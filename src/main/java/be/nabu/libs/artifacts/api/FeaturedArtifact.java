package be.nabu.libs.artifacts.api;

import java.util.List;

public interface FeaturedArtifact extends Artifact {
	public List<Feature> getAvailableFeatures();
}
