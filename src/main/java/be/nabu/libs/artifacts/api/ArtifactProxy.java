package be.nabu.libs.artifacts.api;

// an artifact that actually references another artifact
public interface ArtifactProxy {
	public Artifact getProxied();
}
