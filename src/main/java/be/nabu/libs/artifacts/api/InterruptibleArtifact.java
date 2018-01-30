package be.nabu.libs.artifacts.api;

public interface InterruptibleArtifact {
	public void interrupt();
	public boolean interrupted();
}
