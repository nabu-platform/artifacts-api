package be.nabu.libs.artifacts.api;

import java.io.IOException;

public interface StartableArtifact extends Artifact {
	public enum StartPhase {
		EARLY,
		NORMAL,
		LATE
	}
	
	public void start() throws IOException;
	public boolean isStarted();
	
	public default StartPhase getPhase() {
		return StartPhase.NORMAL;
	}
}
