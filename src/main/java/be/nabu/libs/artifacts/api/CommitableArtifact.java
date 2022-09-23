package be.nabu.libs.artifacts.api;

// we may want to run certain routines before we commit this artifact
public interface CommitableArtifact extends Artifact {
	public void beforeCommit();
}
