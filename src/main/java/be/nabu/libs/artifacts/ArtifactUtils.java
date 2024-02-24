package be.nabu.libs.artifacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.nabu.libs.artifacts.api.ExceptionDescription;
import be.nabu.libs.artifacts.api.Todo;

public class ArtifactUtils {
	
	public static void main(String...args) {
		System.out.println(scanForTodos("test", "This is a TODO test @example do it now!"));
	}
	
	public static List<ExceptionDescription> unique(List<ExceptionDescription> descriptions) {
		Map<String, ExceptionDescription> result = new HashMap<String, ExceptionDescription>();
		for (ExceptionDescription description : descriptions) {
			result.put(description.getId() == null ? description.getCode() : description.getId(), description);
		}
		return new ArrayList<ExceptionDescription>(result.values());
	}
	
	public static List<Todo> scanForTodos(String id, String content) {
		List<Todo> todos = new ArrayList<Todo>();
		Pattern patternTodo = Pattern.compile("(?m)\\bTODO\\b(.*)");
		Pattern patternTag = Pattern.compile("(?m)@[\\w]+");
		Matcher matcher = patternTodo.matcher(content);
		while (matcher.find()) {
			String todoContent = matcher.group(1);
			todoContent = todoContent.trim();
			TodoImpl todo = new TodoImpl();
			todo.setId(id);
			if (todoContent != null && !todoContent.isEmpty()) {
				// check for tags
				Matcher matcherTag = patternTag.matcher(todoContent);
				List<String> tags = new ArrayList<String>();
				while (matcherTag.find()) {
					// skip the @
					tags.add(matcherTag.group().substring(1));
				}
				if (!todoContent.isEmpty()) {
					todo.setTodo(todoContent);
				}
				todo.setTags(tags);
			}
			todos.add(todo);
		}
		return todos;
	}
}
