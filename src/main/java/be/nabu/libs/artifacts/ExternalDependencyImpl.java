package be.nabu.libs.artifacts;

import java.net.URI;
import java.util.List;

import be.nabu.libs.artifacts.api.ExternalDependency;

public class ExternalDependencyImpl implements ExternalDependency {

	private String type, credentials, id, group, method, description, artifactId;
	private URI endpoint;
	private List<URI> alternativeEndpoints;
	
	@Override
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getCredentials() {
		return credentials;
	}
	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}
	
	@Override
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	@Override
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	@Override
	public URI getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(URI endpoint) {
		this.endpoint = endpoint;
	}
	
	@Override
	public List<URI> getAlternativeEndpoints() {
		return alternativeEndpoints;
	}
	public void setAlternativeEndpoints(List<URI> alternativeEndpoints) {
		this.alternativeEndpoints = alternativeEndpoints;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String getArtifactId() {
		return artifactId;
	}
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}
	
}
