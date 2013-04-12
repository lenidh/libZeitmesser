/**
 * Copyright (C) 2012 Moritz Heindl <lenidh[at]gmail[dot]com>
 *
 * This file is part of libZeitmesser.
 *
 * libZeitmesser is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * libZeitmesser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with libZeitmesser.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.lenidh.libzeitmesser;

import java.util.ArrayList;
import java.util.List;

public class Watch {
	
	private final SystemTime systemTime;
	
	private final List<Display> displayList;
	
	private Long baseTS = null;
	
	private long baseOffset = 0;
	
	private Long pauseTS = null;
	
	public Watch(SystemTime systemTime) {
		this.systemTime = systemTime;
		this.displayList = new ArrayList<Display>();
		this.reset();
	}
	
	public void start() {
		long currentTime = this.systemTime.getTime();
		
		if(pauseTS == null) {
			// Start new measurement after a reset.
			this.baseTS = currentTime;
		} else {
			// If measurement was paused, offset and stamp must be increased by the pause time.
			long pauseTime = currentTime - pauseTS;
			this.baseOffset += pauseTime;
			this.baseTS += pauseTime;
			
			this.pauseTS = null;
		}
	}
	
	public void stop() {
		this.pauseTS = this.systemTime.getTime();
	}
	
	public void record() {
		//TODO: Create a lap and add it to the container.
	}
	
	public void reset() {
		this.baseTS = null;
		this.baseOffset = 0;
		this.pauseTS = null;
	}
	
	public long getElapsedTime() {
		long elapsedTime = 0;
		if(this.baseTS != null) {
			if(this.pauseTS == null) {
				elapsedTime = this.systemTime.getTime() - this.baseTS;
			} else {
				elapsedTime = this.pauseTS - this.baseTS;
			}
		}
		return elapsedTime;
	}
	
	public long getBaseOffset() {
		return this.baseOffset;
	}
	
	public boolean addDisplay(Display d) {
		boolean listChanged = false;
		boolean contains = this.displayList.contains(d);
		if(!contains) {
			listChanged = this.displayList.add(d);
		}
		return listChanged;
	}
	
	public boolean removeDisplay(Display d) {
		return this.displayList.remove(d);
	}
}
