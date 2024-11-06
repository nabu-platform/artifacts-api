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

package be.nabu.libs.artifacts.api;

import java.util.List;

public interface ExceptionDescription {
	
	public enum ExceptionType {
		// an exception that follows from a design flaw, this can generally only be fixed by fixing the code and redeploying
		DESIGN,
		// an (unintentional) technical runtime exception that is thrown by the system
		// this is a catch all for any exception that is not further specified, it could be an external system, a transient problem, a fundamental design issue (that is not flagged as such) etc
		TECHNICAL,
		// an intentional exception thrown as part of the business requirements
		BUSINESS
	}
	
	// codes are not guaranteed unique, they can happen in different circumstances with different messages/descriptions
	// if you add an id, we can make sure we take every exception only once
	public String getId();
	public String getCode();
	public String getMessage();
	public String getDescription();
	// a context explaining where it occurs (if relevant)
	public List<String> getContext();
	public boolean isTransient();
	public ExceptionType getType();
}
