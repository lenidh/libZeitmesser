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
	// TODO: Remove this crap of validity check.

	/**
	 * The LapContainer containing this lap.
	 */
	private LapContainer container;
	
	/**
	 * The time elapsed until this Lap was created.
	 */
	private long elapsedTime;
	
	/**
	 * Whether this Lap was removed from its LapContainer.
	 */
	private boolean invalid = false;
	
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
	 * Checks if this Lap was removed from its LapContainer.
	 * 
	 * @throws InvalidLapException The lap was removed from its LapContainer.
	 */
	private void checkValidity() throws InvalidLapException {
		if(this.invalid) {
			throw new InvalidLapException();
		}
	}
	
	/**
	 * Returns the time elapsed until this Lap was created.
	 * 
	 * @return elapsed time
	 * @throws InvalidLapException
	 */
	public long getElapsedTime() throws InvalidLapException {
		this.checkValidity();
		return this.elapsedTime;
	}
	
	public long getElapsedTimeDiff() throws InvalidLapException {
		this.checkValidity();
		long elapsedTimeDiff;
		Lap firstLap = this.container.getFirstLap();
		if(firstLap == this || firstLap == null) {
			elapsedTimeDiff = 0;
		} else {
			elapsedTimeDiff = this.elapsedTime - firstLap.elapsedTime;
		}
		return elapsedTimeDiff;
	}
	
	public long getLapTime() throws InvalidLapException {
		this.checkValidity();
		long lapTime;
		Lap previousLap = this.container.getPreviousLap(this);
		if(previousLap == null) {
			lapTime = this.elapsedTime;
		} else {
			lapTime = this.elapsedTime - previousLap.elapsedTime;
		}
		return lapTime;
	}
	
	public long getLapTimeDiff() throws InvalidLapException {
		this.checkValidity();
		long lapTimeDiff;
		Lap shortestLap = this.container.getShortestLap();
		if(shortestLap == this || shortestLap == null) {
			lapTimeDiff = 0;
		} else {
			lapTimeDiff = this.getLapTime() - shortestLap.getLapTime();
		}
		return lapTimeDiff;
	}
	
	void markInvalid() {
		this.invalid = true;
	}
}
