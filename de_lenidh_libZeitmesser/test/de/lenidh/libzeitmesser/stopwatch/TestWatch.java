package de.lenidh.libzeitmesser.stopwatch;

import java.util.ArrayList;
import java.util.List;

public class TestWatch extends Watch {
	
	List<Long> timeList;
	
	boolean containerInitialized = false;
	
	public TestWatch() {
		this.timeList = new ArrayList<Long>();
		this.timeList.add(10000L);
		this.timeList.add(11000L);
		this.timeList.add(15000L);
	}

	public LapContainer getLapContainer() {
		return super.getLapContainer();
	}
	
	public void start() {
		throw new UnsupportedOperationException();
	}
	
	public void stop() {
		throw new UnsupportedOperationException();
	}
	
	public void record() {
		throw new UnsupportedOperationException();
	}
	
//	public void reset() {
//		throw new UnsupportedOperationException();
//	}
	
	public long getElapsedTime() {
		if(this.timeList.size() > 0) {
			long time = this.timeList.get(0);
			this.timeList.remove(0);
			return time;
		} else {
			throw new IllegalStateException("Calling this method is allowed only three times.");
		}
	}
	
	public boolean addDisplay(Display d) {
		throw new UnsupportedOperationException();
	}
	
	public boolean removeDisplay(Display d) {
		throw new UnsupportedOperationException();
	}
}
