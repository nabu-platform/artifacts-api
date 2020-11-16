package be.nabu.libs.artifacts.api;

public interface TwoPhaseOfflineableArtifact extends OfflineableArtifact, TwoPhaseStartableArtifact {
	public default void onlineFinish() {
		finish();
	}
	public default void offlineFinish() {
		// do nothing
	}
}
