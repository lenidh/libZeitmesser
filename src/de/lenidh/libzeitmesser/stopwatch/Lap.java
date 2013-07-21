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

/**
 * @brief A lap of a stopwatch.
 * 
 * TODO
 */
public class Lap {

	/**
	 * The LapContainer containing this lap.
	 */
	private LapContainer container;
	
	/**
	 * The time elapsed until this Lap was created.
	 */
	private long elapsedTime;
	
	/**
	 * TODO
	 * @param container TODO
	 * @param elapsedTime TODO
	 */
	Lap(LapContainer container, long elapsedTime) {
		this.container = container;
		this.elapsedTime = elapsedTime;
	}
	
	/**
	 * Returns the time elapsed until this Lap was created.
	 * 
	 * @return elapsed time
	 * @throws InvalidLapException
	 */
	public long getElapsedTime(Display d) {
		return getElapsedTime(d.getResolution());
	}
	
	long getElapsedTime(long res) {
		if(container == null) return -1;
		return this.elapsedTime / res;
	}
	
	public long getElapsedTimeDiff(Display d) {
		return getElapsedTimeDiff(d.getResolution());
	}
	
	long getElapsedTimeDiff(long res) {
		if(container == null) return -1;
		long elapsedTimeDiff;
		Lap firstLap = this.container.getFirstLap();
		if(firstLap == this || firstLap == null) {
			elapsedTimeDiff = 0;
		} else {
			elapsedTimeDiff = this.elapsedTime / res - firstLap.elapsedTime / res;
		}
		return elapsedTimeDiff;
	}
	
	public long getLapTime(Display d) {
		return getLapTime(d.getResolution());
	}
	
	long getLapTime(long res) {
		if(container == null) return -1;
		long lapTime;
		Lap previousLap = this.container.getPreviousLap(this);
		if(previousLap == null) {
			lapTime = this.elapsedTime / res;
		} else {
			lapTime = this.elapsedTime / res - previousLap.elapsedTime / res;
		}
		return lapTime;
	}
	
	public long getLapTimeDiff(Display d) {
		return getLapTimeDiff(d.getResolution());
	}
	
	long getLapTimeDiff(long res) {
		if(container == null) return -1;
		long lapTimeDiff;
		Lap shortestLap = this.container.getShortestLap();
		if(shortestLap == this || shortestLap == null) {
			lapTimeDiff = 0;
		} else {
			lapTimeDiff = this.getLapTime(res) - shortestLap.getLapTime(res);
		}
		return lapTimeDiff;
	}
	
	public boolean isInvalid() {
		if(this.container == null) return true;
		return false;
	}
	
	void markInvalid() {
		this.container = null;
	}
}
