package be.nabu.libs.artifacts.api;

public interface LazyArtifact extends Artifact {
	public void forceLoad();
}
