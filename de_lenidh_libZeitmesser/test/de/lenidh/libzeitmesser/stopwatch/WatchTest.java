package de.lenidh.libzeitmesser.stopwatch;

import static org.junit.Assert.*;

import org.junit.Test;

public class WatchTest {

	@Test
	public void testGetLapContainer() {
		Watch watch = new Watch(new TestSystemTime());
		LapContainer container = watch.getLapContainer();
		assertNotNull(container);
	}

	@Test
	public void testStart() {
		Watch watch = new Watch(new TestSystemTime());
		watch.start();
		
		assertTrue(watch.isRunning());
	}

	@Test
	public void testStop() {
		Watch watch = new Watch(new TestSystemTime());
		watch.start();
		watch.stop();
		
		assertFalse(watch.isRunning());
	}

	@Test
	public void testRecord() {
		Watch watch = new Watch(new TestSystemTime());
		LapContainer container = watch.getLapContainer();
		
		watch.start();
		watch.record();
		watch.record();
		watch.stop();
		watch.record();
		assertEquals(2, container.size());
		
		watch.reset();
		assertEquals(0, container.size());
	}

	@Test
	public void testReset() {
		Watch watch = new Watch(new TestSystemTime());
		LapContainer container = watch.getLapContainer();
		
		watch.start();
		watch.record();
		watch.record();
		watch.reset();
		
		assertEquals(0, watch.getElapsedTime());
		assertEquals(0, container.size());
	}

	@Test
	public void testGetElapsedTime() {
		Watch watch = new Watch(new TestSystemTime());
		watch.start();
		
		assertEquals(1, watch.getElapsedTime());
	}

	@Test
	public void testIsRunning() {
		Watch watch = new Watch(new TestSystemTime());
		
		watch.start();
		assertTrue(watch.isRunning());
		
		watch.stop();
		assertFalse(watch.isRunning());
		
		watch.reset();
		assertFalse(watch.isRunning());
		
		watch.start();
		assertTrue(watch.isRunning());
		
		watch.reset();
		assertFalse(watch.isRunning());
	}

	@Test
	public void testAddDisplay() {
		Watch watch = new Watch(new TestSystemTime());
		TestDisplay display = new TestDisplay();
		watch.addDisplay(display);
		
		watch.start();
		try { Thread.sleep(1200); } catch (InterruptedException e) {}
		watch.stop();
		watch.reset();
		
		assertEquals(3, display.getTimeUpdated()); // 0, 1000 and reset
		assertEquals(3, display.getStateUpdated()); // start, stop and reset
		assertEquals(1, display.getLapsUpdated()); // reset
	}

	@Test
	public void testRemoveDisplay() {
		Watch watch = new Watch(new TestSystemTime());
		TestDisplay display = new TestDisplay();
		watch.addDisplay(display);
		watch.removeDisplay(display);
		
		watch.start();
		try { Thread.sleep(1200); } catch (InterruptedException e) {}
		watch.stop();
		watch.reset();
		
		assertEquals(0, display.getTimeUpdated());
		assertEquals(0, display.getStateUpdated());
		assertEquals(0, display.getLapsUpdated());
	}

}
