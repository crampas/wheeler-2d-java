package de.mw.scene2d.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class AngleTest
{
	@Test
	public void fromRad()
	{
		assertEquals(1.0, Angle.fromRad(0).x, 0.001);
		assertEquals(0.0, Angle.fromRad(0).y, 0.001);

		assertEquals(0.0, Angle.fromRad(Math.PI / 2).x, 0.001);
		assertEquals(1.0, Angle.fromRad(Math.PI / 2).y, 0.001);
		
		assertEquals(-1.0, Angle.fromRad(Math.PI).x, 0.001);
		assertEquals(0.0, Angle.fromRad(Math.PI).y, 0.001);

		assertEquals(0.0, Angle.fromRad(-Math.PI / 2).x, 0.001);
		assertEquals(-1.0, Angle.fromRad(-Math.PI / 2).y, 0.001);
	}

	@Test
	public void fromDegree()
	{
		assertEquals(1.0, Angle.fromDegree(0).x, 0.001);
		assertEquals(0.0, Angle.fromDegree(0).y, 0.001);

		assertEquals(0.0, Angle.fromDegree(90).x, 0.001);
		assertEquals(1.0, Angle.fromDegree(90).y, 0.001);
		
		assertEquals(-1.0, Angle.fromDegree(180).x, 0.001);
		assertEquals(0.0, Angle.fromDegree(180).y, 0.001);

		assertEquals(0.0, Angle.fromDegree(-90).x, 0.001);
		assertEquals(-1.0, Angle.fromDegree(-90).y, 0.001);
	}

	
	@Test
	public void getDegree()
	{
		assertEquals(0.0, Angle.getDegree(new Vector(1, 0)), 0.001);
		assertEquals(45.0, Angle.getDegree(new Vector(1, 1).normalize()), 0.001);
		assertEquals(90.0, Angle.getDegree(new Vector(0, 1)), 0.001);
		assertEquals(135.0, Angle.getDegree(new Vector(-1, 1).normalize()), 0.001);
		assertEquals(180.0, Angle.getDegree(new Vector(-1, 0)), 0.001);
		assertEquals(-45.0, Angle.getDegree(new Vector(1, -1).normalize()), 0.001);
		assertEquals(-90.0, Angle.getDegree(new Vector(0, -1)), 0.001);
		assertEquals(-135.0, Angle.getDegree(new Vector(-1, -1).normalize()), 0.001);
	}

}
