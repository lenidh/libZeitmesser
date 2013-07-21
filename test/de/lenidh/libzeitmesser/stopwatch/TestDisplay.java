package de.lenidh.libzeitmesser.stopwatch;

public class TestDisplay implements Display {

	private int timeUpdated = 0;

	private int stateUpdated = 0;
	private int lapsUpdated = 0;
	
	@Override
	public void updateTime() {
		this.timeUpdated++;
	}

	@Override
	public void updateState() {
		this.stateUpdated++;
	}

	@Override
	public void updateLaps() {
		this.lapsUpdated++;
	}
	
	@Override
	public long getResolution() {
		return 1;
	}

	public int getTimeUpdated() {
		return timeUpdated;
	}

	public int getStateUpdated() {
		return stateUpdated;
	}

	public int getLapsUpdated() {
		return lapsUpdated;
	}

}
