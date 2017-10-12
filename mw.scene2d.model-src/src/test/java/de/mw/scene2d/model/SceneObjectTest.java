package de.mw.scene2d.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SceneObjectTest
{
	@Test
	public void rotate()
	{
		SceneObject o = new SceneObject();
		o.rotate((float)45);
		
		o.rotate(5f);
		assertEquals(50, o.getRotation(), 0.001);

		o.rotate(5f);
		assertEquals(55, o.getRotation(), 0.001);

		o.rotate(200f);
		assertEquals(-105, o.getRotation(), 0.001);
	}

	
	@Test
	public void addPositionRelative()
	{
		SceneObject o = new SceneObject();
		o.rotate((float)45);
		
		o.addPositionRelative(1, 0);
		
		assertEquals(0.707, o.x, 0.001);
		assertEquals(0.707, o.y, 0.001);
	}


	@Test
	public void getAbsolute_Vector()
	{
		SceneObject o = new SceneObject();
		o.x = 3;
		o.y = 1;
		o.rotate((float)30);
		
		assertEquals(0.0, o.getAbsolute(new Vector(0, 0)).x, 0.001);
		assertEquals(0.0, o.getAbsolute(new Vector(0, 0)).y, 0.001);

		assertEquals(0.866, o.getAbsolute(new Vector(1, 0)).x, 0.001);
		assertEquals(0.5, o.getAbsolute(new Vector(1, 0)).y, 0.001);
	}

	@Test
	public void getAbsolute_Point()
	{
		SceneObject o = new SceneObject();
		o.x = 3;
		o.y = 1;
		o.rotate((float)30);
		
		assertEquals(3.0, o.getAbsolute(new Point(0, 0)).x, 0.001);
		assertEquals(1.0, o.getAbsolute(new Point(0, 0)).y, 0.001);

		assertEquals(3.866, o.getAbsolute(new Point(1, 0)).x, 0.001);
		assertEquals(1.5, o.getAbsolute(new Point(1, 0)).y, 0.001);
	}

	
	@Test
	public void getRelative()
	{
		SceneObject o = new SceneObject();
		o.x = 1;
		o.y = 3;
		o.rotate((float)30);
		
		assertEquals(-2.366, o.getRelative(new Point(0, 0)).x, 0.001);
		assertEquals(-2.098, o.getRelative(new Point(0, 0)).y, 0.001);

		assertEquals(-1.5, o.getRelative(new Point(1, 0)).x, 0.001);
		assertEquals(-2.598, o.getRelative(new Point(1, 0)).y, 0.001);
	}

}
