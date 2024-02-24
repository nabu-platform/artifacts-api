package be.nabu.libs.artifacts.api;

import java.util.List;

public class Todo {
	private String id, todo;
	private List<String> tags;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTodo() {
		return todo;
	}
	public void setTodo(String todo) {
		this.todo = todo;
	}
	
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
