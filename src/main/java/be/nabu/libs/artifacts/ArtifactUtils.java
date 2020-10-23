package be.nabu.libs.artifacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.nabu.libs.artifacts.api.ExceptionDescription;

public class ArtifactUtils {
	public static List<ExceptionDescription> unique(List<ExceptionDescription> descriptions) {
		Map<String, ExceptionDescription> result = new HashMap<String, ExceptionDescription>();
		for (ExceptionDescription description : descriptions) {
			result.put(description.getId() == null ? description.getCode() : description.getId(), description);
		}
		return new ArrayList<ExceptionDescription>(result.values());
	}
}
