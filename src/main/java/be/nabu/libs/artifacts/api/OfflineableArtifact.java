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

/**
 * It is currently presumed that any artifact that can be impacted by "offline" modus should have a start routine
 * If you don't have a start routine, what exactly will you be turning off during offline modus?
 */
public interface OfflineableArtifact extends StartableArtifact {
	
	// switch between online and offline mode
	public default void online() throws IOException {
		start();
	}
	
	public default void offline() throws IOException {
		if (this instanceof StoppableArtifact) {
			((StoppableArtifact) this).stop();
		}
	}
	
	// an alternative to the start() method
	public default void startOffline() throws IOException {
		// do nothing, we assume you don't want to start
	}
}
