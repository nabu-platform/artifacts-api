/*
* Copyright (C) 2014 Alexander Verbruggen
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

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
			// special case, you often type TODO: do this
			// in that case, we don't want to include the ":"
			Todo todo = new Todo();
			todo.setId(id);
			if (todoContent != null && !todoContent.isEmpty()) {
				if (todoContent.indexOf(':') == 0) {
					todoContent = todoContent.substring(1).trim();
				}
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
				// if we have an id, we assume it points to somewhere in the tree, we always add the "project" itself as a tag
				if (id != null) {
					tags.add(id.replaceAll("^([^.]+).*", "$1"));
				}
				todo.setTags(tags);
			}
			todos.add(todo);
		}
		return todos;
	}
}
