package be.nabu.libs.artifacts.api;

import java.io.IOException;

public interface StartableArtifact extends Artifact {
	public enum StartPhase {
		FIRST,
		EARLY,
		NORMAL,
		LATE,
		LAST
	}
	
	public void start() throws IOException;
	public boolean isStarted();
	
	public default StartPhase getPhase() {
		return StartPhase.NORMAL;
	}
}
