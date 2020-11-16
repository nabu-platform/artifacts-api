package be.nabu.libs.artifacts.api;

import java.io.IOException;

/**
 * It is currently presumed that any artifact that can be impacted by "offline" modus should have a start routine
 * If you don't have a start routine, what exactly will you be turning off during offline modus?
 */
public interface OfflineableArtifact extends StartableArtifact {
	
	// switch between online and offline mode
	public default void online() throws IOException {
		start();
	}
	
	public default void offline() throws IOException {
		if (this instanceof StoppableArtifact) {
			((StoppableArtifact) this).stop();
		}
	}
	
	// an alternative to the start() method
	public default void startOffline() throws IOException {
		// do nothing, we assume you don't want to start
	}
}
