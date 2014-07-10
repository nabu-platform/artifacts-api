package be.nabu.libs.artifacts.api;

public interface ArtifactResolver<T extends Artifact> {
	public T resolve(String id);
}
