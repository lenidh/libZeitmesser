package de.lenidh.libzeitmesser.stopwatch;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import de.lenidh.libzeitmesser.stopwatch.LapContainer.Order;

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
		
		assertEquals(10000, laps[0].getElapsedTime(1));
		assertEquals(11000, laps[1].getElapsedTime(1));
		assertEquals(15000, laps[2].getElapsedTime(1));
	}
	
	@Test
	public void testToList() {
		TestWatch watch = new TestWatch();
		LapContainer container = watch.getLapContainer();
		container.addLap();
		container.addLap();
		container.addLap();
		List<Lap> laps = container.toList();
		
		assertEquals(10000, laps.get(0).getElapsedTime(1));
		assertEquals(11000, laps.get(1).getElapsedTime(1));
		assertEquals(15000, laps.get(2).getElapsedTime(1));
		
		laps = container.toList(Order.elapsedTime);
		
		assertEquals(10000, laps.get(0).getElapsedTime(1));
		assertEquals(11000, laps.get(1).getElapsedTime(1));
		assertEquals(15000, laps.get(2).getElapsedTime(1));
		
		laps = container.toList(Order.lapTime);

		assertEquals(1000, laps.get(0).getLapTime(1));
		assertEquals(4000, laps.get(1).getLapTime(1));
		assertEquals(10000, laps.get(2).getLapTime(1));
	}
}
