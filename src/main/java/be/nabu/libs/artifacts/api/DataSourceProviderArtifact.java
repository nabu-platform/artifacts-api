package be.nabu.libs.artifacts.api;

import javax.sql.DataSource;

import be.nabu.libs.artifacts.api.Artifact;

public interface DataSourceProviderArtifact extends Artifact {
	public DataSource getDataSource();
	public boolean isAutoCommit();
	// the context for the data source provider
	// based on the context we can do better matching for which provider to use when
	public default String getContext() { return null; }
}
