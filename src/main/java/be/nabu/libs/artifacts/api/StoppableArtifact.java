package be.nabu.libs.artifacts.api;

import java.io.IOException;

public interface StoppableArtifact extends Artifact {
	public void stop() throws IOException;
}
