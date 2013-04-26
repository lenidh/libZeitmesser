package de.lenidh.libzeitmesser.stopwatch;

public class TestSystemTime implements SystemTime {

	private long currentTime = 0;
	
	@Override
	public long getTime() {
		return this.currentTime++;
	}

}
