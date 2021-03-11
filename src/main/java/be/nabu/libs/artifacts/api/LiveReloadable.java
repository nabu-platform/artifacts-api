package be.nabu.libs.artifacts.api;

public interface LiveReloadable extends Artifact {
	public default boolean canLiveReload() {
		return true;
	}
	public void liveReload();
}
