package be.nabu.libs.artifacts.api;

import java.io.IOException;

public interface CacheableArtifact extends Artifact {
	public void resetCache() throws IOException;
}
