package be.nabu.libs.artifacts;

import java.util.List;

import be.nabu.libs.artifacts.api.Todo;

public class TodoImpl implements Todo {
	private String id, todo;
	private List<String> tags;
	
	@Override
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String getTodo() {
		return todo;
	}
	public void setTodo(String todo) {
		this.todo = todo;
	}
	
	@Override
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public String toString() {
		return "[" + id + "] " + todo + " (" + tags + ")";
	}
}
