package be.nabu.libs.artifacts;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import be.nabu.libs.artifacts.api.ArtifactResolver;

public class ArtifactResolverFactory {

	private static ArtifactResolverFactory instance;
	
	public static ArtifactResolverFactory getInstance() {
		if (instance == null)
			instance = new ArtifactResolverFactory();
		return instance;
	}
	
	private List<ArtifactResolver<?>> resolvers = new ArrayList<ArtifactResolver<?>>();
	
	@SuppressWarnings("rawtypes")
	public ArtifactResolver<?> getResolver() {
		if (resolvers.isEmpty()) {
			ServiceLoader<ArtifactResolver> serviceLoader = ServiceLoader.load(ArtifactResolver.class);
			for (ArtifactResolver<?> resolver : serviceLoader)
				resolvers.add(resolver);
		}
		return new MultipleArtifactResolver(resolvers);
	}
	
	public void addResolver(ArtifactResolver<?> resolver) {
		resolvers.add(resolver);
	}
	
	public void removeResolver(ArtifactResolver<?> resolver) {
		resolvers.remove(resolver);
	}
	
	@SuppressWarnings("unused")
	private void activate() {
		instance = this;
	}
	@SuppressWarnings("unused")
	private void deactivate() {
		instance = null;
	}
}
