package be.nabu.libs.artifacts.api;

public interface TwoPhaseStartableArtifact extends StartableArtifact {
	public void finish();
	public boolean isFinished();
}
