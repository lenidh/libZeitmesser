package de.lenidh.libzeitmesser.stopwatch;

public class TestLapContainer extends LapContainer {
	
	private Lap firstLap;
	
	private Lap shortestLap;
	
	private Lap lastLap;

	TestLapContainer(Watch watch) {
		super(null);
		
		this.firstLap = new Lap(this, 10000);
		this.shortestLap = new Lap(this, 11000);
		this.lastLap = new Lap(this, 15000);
	}
	
	void addLap() {
		throw new UnsupportedOperationException();
	}
	
	void clear() {
		throw new UnsupportedOperationException();
	}
	
	Lap getFirstLap() {
		return this.firstLap;
	}
	
	Lap getShortestLap() {
		return this.shortestLap;
	}
	
	Lap getLastLap() {
		return this.lastLap;
	}
	
	Lap getPreviousLap(Lap lap) {
		if(lap == this.lastLap) {
			return this.shortestLap;
		} else if(lap == this.shortestLap) {
			return this.firstLap;
		} else {
			return null;
		}
	}
	
	public Lap[] getLapsAsArray() {
		Lap[] laps = new Lap[3];
		laps[0] = this.firstLap;
		laps[1] = this.shortestLap;
		laps[2] = this.lastLap;
		return laps;
	}

}
