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
 * TODO: Comments
 */
public class LapContainer {
	
	private Watch watch;

	private List<Lap> elapsedTimeOrder;
	
	private List<Lap> lapTimeOrder;
	
	LapContainer(Watch watch) {
		this.watch = watch;
		this.elapsedTimeOrder = new ArrayList<Lap>();
		this.lapTimeOrder = new ArrayList<Lap>();
	}
	
	void addLap() {
		int numberOfLaps = lapTimeOrder.size();
		Lap lap = new Lap(this, this.watch.getElapsedTime());
		this.elapsedTimeOrder.add(lap);
		
		if(numberOfLaps <= 0) lapTimeOrder.add(lap);
		for(int i = 0; i < numberOfLaps; i++) {
			if(lapTimeOrder.get(i).getLapTime() > lap.getLapTime()) {
				lapTimeOrder.add(i, lap);
				break;
			}
		}
	}
	
	void clear() {
		for(Lap l : this.elapsedTimeOrder) {
			l.markInvalid();
		}
		this.elapsedTimeOrder.clear();
		this.lapTimeOrder.clear();
	}
	
	Lap getFirstLap() {
		Lap firstLap = null;
		if(this.elapsedTimeOrder.size() > 0) {
			firstLap = this.elapsedTimeOrder.get(0);
		}
		return firstLap;
	}
	
	Lap getShortestLap() {
		Lap shortestLap = null;
		if(this.lapTimeOrder.size() > 0) {
			shortestLap = this.lapTimeOrder.get(0);
		}
		return shortestLap;
	}
	
	Lap getPreviousLap(Lap lap) {
		Lap previousLap = null;
		int index = this.elapsedTimeOrder.indexOf(lap);
		if(index > 0) { // previous lap exists
			previousLap = this.elapsedTimeOrder.get(index-1);
		}
		return previousLap;
	}
	
	public int size() {
		return this.elapsedTimeOrder.size();
	}
	
	public Lap[] getLapsAsArray() {
		return (Lap[]) this.elapsedTimeOrder.toArray(new Lap[0]);
	}
}
