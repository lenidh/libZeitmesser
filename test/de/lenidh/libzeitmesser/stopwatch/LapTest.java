package de.lenidh.libzeitmesser.stopwatch;

import static org.junit.Assert.*;

import org.junit.Test;

public class LapTest {

	@Test
	public void testGetElapsedTime() {
		TestLapContainer container = new TestLapContainer(null);
		Lap firstLap = container.getFirstLap();
		Lap shortestLap = container.getShortestLap();
		Lap lastLap = container.getLastLap();
		
		assertEquals("Elapsed time of first lap is 10000.", 10000, firstLap.getElapsedTime(1));
		assertEquals("Elapsed time of shortest lap is 11000.", 11000, shortestLap.getElapsedTime(1));
		assertEquals("Elapsed time of last lap is 15000.", 15000, lastLap.getElapsedTime(1));
	}

	@Test
	public void testGetElapsedTimeDiff() {
		TestLapContainer container = new TestLapContainer(null);
		Lap firstLap = container.getFirstLap();
		Lap shortestLap = container.getShortestLap();
		Lap lastLap = container.getLastLap();

		assertEquals("First lap time difference to first lap is 0.", 0, firstLap.getElapsedTimeDiff(1));
		assertEquals("Shortest lap time difference to first lap is 1000.", 1000, shortestLap.getElapsedTimeDiff(1));
		assertEquals("Last lap time difference to first lap is 5000.", 5000, lastLap.getElapsedTimeDiff(1));
	}

	@Test
	public void testGetLapTime() {
		TestLapContainer container = new TestLapContainer(null);
		Lap firstLap = container.getFirstLap();
		Lap shortestLap = container.getShortestLap();
		Lap lastLap = container.getLastLap();

		assertEquals("Lap time of first lap is 10000.", 10000, firstLap.getLapTime(1));
		assertEquals("Lap time of shortest lap is 1000.", 1000, shortestLap.getLapTime(1));
		assertEquals("Lap time of last lap is 4000.", 4000, lastLap.getLapTime(1));
	}

	@Test
	public void testGetLapTimeDiff() {
		TestLapContainer container = new TestLapContainer(null);
		Lap firstLap = container.getFirstLap();
		Lap shortestLap = container.getShortestLap();
		Lap lastLap = container.getLastLap();

		assertEquals("First lap time difference to shortest lap is 9000.", 9000, firstLap.getLapTimeDiff(1));
		assertEquals("Shortest lap time difference to shortest lap is 0.", 0, shortestLap.getLapTimeDiff(1));
		assertEquals("Last lap time difference to shortest lap is 3000.", 3000, lastLap.getLapTimeDiff(1));
	}

}
