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

import java.io.IOException;

public interface StartableArtifact extends Artifact {
	public enum StartPhase {
		// JDBC pools start up
		FIRST,
		// DDL sync
		SECOND,
		// deployment actions (usually DML sync)
		THIRD,
		// frameworks with listeners
		EARLY,
		// normal stuff
		NORMAL,
		LATE,
		LAST
	}
	
	public void start() throws IOException;
	public boolean isStarted();
	
	public default StartPhase getPhase() {
		return StartPhase.NORMAL;
	}
}
