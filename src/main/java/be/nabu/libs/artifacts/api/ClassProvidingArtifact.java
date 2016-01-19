package be.nabu.libs.artifacts.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ClassProvidingArtifact extends Artifact {
	public List<Class<?>> getImplementationsFor(Class<?> clazz) throws IOException;
	public Class<?> loadClass(String id) throws ClassNotFoundException;
	public InputStream loadResource(String id) throws IOException;
}
