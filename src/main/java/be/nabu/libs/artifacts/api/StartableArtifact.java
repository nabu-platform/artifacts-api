package be.nabu.libs.artifacts.api;

import java.io.IOException;

public interface StartableArtifact extends Artifact {
	public enum StartPhase {
		// JDBC pools start up
		FIRST,
		// DDL sync
		SECOND,
		// deployment actions (usually DML sync)
		THIRD,
		// frameworks with listeners
		EARLY,
		// normal stuff
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
