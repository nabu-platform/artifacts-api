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

import java.util.List;

import be.nabu.libs.artifacts.api.ExceptionDescription;

public class ExceptionDescriptionImpl implements ExceptionDescription {

	private String id, code, message, description;
	private List<String> context;
	private ExceptionType type;
	// whether or not the exception is transient (default is false)
	private boolean isTransient;
	
	public ExceptionDescriptionImpl() {
		// auto
	}

	public ExceptionDescriptionImpl(String id, String code, String message, String description) {
		this(id, code, message, description, ExceptionType.TECHNICAL);
	}
	
	public ExceptionDescriptionImpl(String id, String code, String message, String description, ExceptionType type) {
		this.id = id;
		this.code = code;
		this.message = message;
		this.description = description;
		this.type = type;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getContext() {
		return context;
	}
	public void setContext(List<String> context) {
		this.context = context;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ExceptionType getType() {
		return type;
	}
	public void setType(ExceptionType type) {
		this.type = type;
	}
	public boolean isTransient() {
		return isTransient;
	}
	public void setTransient(boolean isTransient) {
		this.isTransient = isTransient;
	}
}
