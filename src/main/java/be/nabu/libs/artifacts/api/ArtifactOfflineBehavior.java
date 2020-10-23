package be.nabu.libs.artifacts.api;

public interface ArtifactOfflineBehavior {
	public enum OfflineBehavior {
		STOP,
		CONTINUE
	}
	public default OfflineBehavior getOfflineBehavior() {
		return OfflineBehavior.CONTINUE;
	}
}
