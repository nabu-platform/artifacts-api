package be.nabu.libs.artifacts.api;

import javax.sql.DataSource;

import be.nabu.libs.artifacts.api.Artifact;

public interface DataSourceProviderArtifact extends Artifact {
	public DataSource getDataSource();
	public boolean isAutoCommit();
}
