package de.lenidh.libzeitmesser.stopwatch;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class LapContainerTest {

	@Test
	public void testAddLap() {
		TestWatch watch = new TestWatch();
		LapContainer container = watch.getLapContainer();
		container.addLap();
		container.addLap();
		container.addLap();
		Lap[] laps = container.toArray();
		
		assertEquals(3, laps.length);
	}

	@Test
	public void testClear() {
		TestWatch watch = new TestWatch();
		LapContainer container = watch.getLapContainer();
		container.addLap();
		container.addLap();
		container.addLap();
		container.clear();
		Lap[] laps = container.toArray();
		
		assertEquals(0, laps.length);
	}

	@Test
	public void testGetFirstLap() {
		TestWatch watch = new TestWatch();
		LapContainer container = watch.getLapContainer();
		container.addLap();
		container.addLap();
		container.addLap();
		Lap[] laps = container.toArray();
		
		assertSame(laps[0], container.getFirstLap());
	}

	@Test
	public void testGetShortestLap() {
		TestWatch watch = new TestWatch();
		LapContainer container = watch.getLapContainer();
		container.addLap();
		container.addLap();
		container.addLap();
		Lap[] laps = container.toArray();
		
		assertSame(laps[1], container.getShortestLap());
	}

	@Test
	public void testGetPreviousLap() {
		TestWatch watch = new TestWatch();
		LapContainer container = watch.getLapContainer();
		container.addLap();
		container.addLap();
		container.addLap();
		Lap[] laps = container.toArray();
		Lap firstLap = laps[0];
		Lap secondLap = laps[1];
		Lap thirdLap = laps[2];
		
		Lap previousLap;
		previousLap = container.getPreviousLap(firstLap);
		assertNull(previousLap);
		previousLap = container.getPreviousLap(secondLap);
		assertSame(firstLap, previousLap);
		previousLap = container.getPreviousLap(thirdLap);
		assertSame(secondLap, previousLap);
	}

	@Test
	public void testToArray() {
		TestWatch watch = new TestWatch();
		LapContainer container = watch.getLapContainer();
		container.addLap();
		container.addLap();
		container.addLap();
		Lap[] laps = container.toArray();
		
		assertEquals(10000, laps[0].getElapsedTime());
		assertEquals(11000, laps[1].getElapsedTime());
		assertEquals(15000, laps[2].getElapsedTime());
	}
	
	@Test
	public void testToList() {
		TestWatch watch = new TestWatch();
		LapContainer container = watch.getLapContainer();
		container.addLap();
		container.addLap();
		container.addLap();
		List<Lap> laps = container.toList();
		
		assertEquals(10000, laps.get(0).getElapsedTime());
		assertEquals(11000, laps.get(1).getElapsedTime());
		assertEquals(15000, laps.get(2).getElapsedTime());
	}
}
