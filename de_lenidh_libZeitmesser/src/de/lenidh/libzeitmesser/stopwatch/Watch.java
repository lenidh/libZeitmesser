/**
 * Copyright (C) 2013 Moritz Heindl <lenidh[at]gmail[dot]com>
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

package de.lenidh.libzeitmesser.stopwatch;

import java.util.ArrayList;
import java.util.List;

/**
 * @brief A stopwatch
 * 
 * This is a stopwatch. It supports all basic tasks like starting, pausing and
 * laps.
 */
public class Watch {
	
	/**
	 * SystemTime object of the current application.
	 */
	private final SystemTime systemTime;
	
	/**
	 * A list of Displays that will be notified about changes.
	 */
	private final List<Display> displayList;
	
	/**
	 * The timestamp of measurement start increased by the time in pause mode.
	 */
	private Long baseTS = null;
	
	/**
	 * Timestamp when entering the pause mode.
	 */
	private Long pauseTS = null;
	
	/**
	 * The LapContainer holding the laps of this Watch.
	 */
	private LapContainer lapContainer;
	
	/**
	 * TODO
	 * @param systemTime TODO
	 */
	public Watch(SystemTime systemTime) {
		this.systemTime = systemTime;
		this.displayList = new ArrayList<Display>();
		this.lapContainer = new LapContainer(this);
		this.reset();
	}
	
	/**
	 * @brief Returns the LapContainer of this Watch
	 * 
	 * @return LapContainer of this Watch
	 */
	public LapContainer getLapContainer() {
		return this.lapContainer;
	}
	
	/**
	 * @brief Start or resume the time measurement.
	 * 
	 * If the Watch was just created or reseted, a new measurement is started.
	 * When the measurement was paused, it will be resumed.
	 */
	public void start() {
		long currentTime = this.systemTime.getTime();
		
		if(pauseTS == null) {
			// Start new measurement after a reset.
			this.baseTS = currentTime;
		} else {
			// If measurement was paused, offset and stamp must be increased by
			// the pause time.
			long pauseTime = currentTime - pauseTS;
			this.baseTS += pauseTime;
			
			this.pauseTS = null;
		}
	}
	
	/**
	 * @brief Stop the time measurement.
	 */
	public void stop() {
		this.pauseTS = this.systemTime.getTime();
	}
	
	/**
	 * @brief Add a new Lap to the LapContainer.
	 */
	public void record() {
		this.lapContainer.addLap();
	}
	
	/**
	 * @brief Reset the Watch.
	 * 
	 * This resets the Watch's state. It will also clear the LapContainer and
	 * marks all Laps as invalid. 
	 */
	public void reset() {
		this.baseTS = null;
		this.pauseTS = null;
		this.lapContainer.clear();
	}
	
	/**
	 * @brief Returns the elapsed Time in milliseconds.
	 * 
	 * @return elapsed time
	 */
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
	
	/**
	 * @brief Add a Display to be notified by this Watch.
	 * @param d the Display
	 * 
	 * @return TODO
	 */
	public boolean addDisplay(Display d) {
		boolean listChanged = false;
		boolean contains = this.displayList.contains(d);
		if(!contains) {
			listChanged = this.displayList.add(d);
		}
		return listChanged;
	}
	
	/**
	 * @brief Remove a Display from the Watch's list.
	 * @param d the Display
	 * @return TODO
	 */
	public boolean removeDisplay(Display d) {
		return this.displayList.remove(d);
	}
}
