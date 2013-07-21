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
import java.util.Timer;
import java.util.TimerTask;

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
	
	private Timer timer = new Timer();
	
	private TimerTask timerTask = null;
	
	private long interval;
	
	public Watch(SystemTime systemTime) {
		this(systemTime, 1000);
	}
	
	/**
	 * TODO
	 * @param systemTime TODO
	 */
	public Watch(SystemTime systemTime, long interval) {
		if(systemTime == null) throw new IllegalArgumentException("systemTime was null");
		this.systemTime = systemTime;
		this.displayList = new ArrayList<Display>();
		this.lapContainer = new LapContainer(this);
		this.interval = interval;
		this.reset();
	}
	
	/**
	 * @brief This constructor is for test purposes only.
	 */
	Watch() {
		this.systemTime = null;
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
		// If already running, do nothing.
		if(this.isRunning()) return;
		
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
		
		this.notifyAllStateChanged();
		if(this.displayList.size() > 0) this.startTimerTask();
	}
	
	/**
	 * @brief Stop the time measurement.
	 */
	public void stop() {
		this.pauseTS = this.systemTime.getTime();
		this.stopTimerTask();
		this.notifyAllStateChanged();
	}
	
	/**
	 * @brief Add a new Lap to the LapContainer.
	 * 
	 * Calling this method will add a new Lap to the Watch's LapContainer, if
	 * the Watch is running.
	 */
	public void record() {
		if(this.isRunning()) {
			this.lapContainer.addLap();
			this.notifyAllLapsChanged();
		}
	}
	
	/**
	 * @brief Reset the Watch.
	 * 
	 * This resets the Watch's state. It will also clear the LapContainer and
	 * marks all Laps as invalid. 
	 */
	public void reset() {
		this.stopTimerTask();
		this.baseTS = null;
		this.pauseTS = null;
		this.lapContainer.clear();
		this.notifyAllStateChanged();
		this.notifyAllTimeChanged();
		this.notifyAllLapsChanged();
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
	 * @brief Returns the measurement state.
	 * 
	 * @return true if running, false else.
	 */
	public boolean isRunning() {
		return (this.baseTS != null && this.pauseTS == null);
	}
	
	private void startTimerTask() {
		if(this.timerTask == null) {
			this.timerTask = new TimerTask() {
				
				@Override
				public void run() {
					notifyAllTimeChanged();
				}
			};
			this.timer.scheduleAtFixedRate(this.timerTask, 0, this.interval);
		}
	}
	
	private void stopTimerTask() {
		if(this.timerTask != null) {
			this.timerTask.cancel();
			this.timerTask = null;
		}
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
		if(this.displayList.size() > 0 && this.isRunning()) {
			this.startTimerTask();
		}
		return listChanged;
	}
	
	/**
	 * @brief Remove a Display from the Watch's list.
	 * @param d the Display
	 * @return TODO
	 */
	public boolean removeDisplay(Display d) {
		boolean retVal = this.displayList.remove(d);
		if(this.displayList.size() <= 0) this.stopTimerTask();
		return retVal;
	}
	
	private void notifyAllTimeChanged() {
		for(Display d : this.displayList) {
			d.updateTime();
		}
	}
	
	private void notifyAllStateChanged() {
		for(Display d : this.displayList) {
			d.updateState();
		}
	}
	
	private void notifyAllLapsChanged() {
		for(Display d : this.displayList) {
			d.updateLaps();
		}
	}
}
