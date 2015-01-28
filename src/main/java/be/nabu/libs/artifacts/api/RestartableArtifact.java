package be.nabu.libs.artifacts.api;

import java.io.IOException;

public interface RestartableArtifact extends StartableArtifact, StoppableArtifact {
	public void restart() throws IOException;
}
