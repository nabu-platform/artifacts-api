package be.nabu.libs.artifacts.api;

public interface DeployHookArtifact extends Artifact {
	public default void preDeployment() {
		// do nothing
	}
	public default void postDeployment() {
		// do nothing
	}
	public default void duringDeployment() {
		// do nothing
	}
}
