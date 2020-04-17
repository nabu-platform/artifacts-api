package be.nabu.libs.artifacts.api;

import java.util.List;

public interface ExternalDependencyArtifact extends Artifact {
	public List<ExternalDependency> getExternalDependencies();
}
