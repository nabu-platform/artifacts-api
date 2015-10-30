package be.nabu.libs.artifacts.api;

import java.io.IOException;

public interface StartableArtifact extends Artifact {
	public void start() throws IOException;
	public boolean isStarted();
}
