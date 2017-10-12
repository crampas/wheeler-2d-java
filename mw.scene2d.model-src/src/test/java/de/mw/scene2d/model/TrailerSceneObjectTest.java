package de.mw.scene2d.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TrailerSceneObjectTest
{
	@Test
    public void testPull1()
    {
        TrailerSceneObject trailer = new TrailerSceneObject(new ObjectConfig());
        trailer.pull(new Vector(1.0f, 0f));
        assertEquals(1.0f, trailer.x, 0.01);
        assertEquals(0.0f, trailer.y, 0.01);
        assertEquals(0.0f, trailer.getRotation(), 0.01);
    }

	@Test
    public void testPull2()
    {
        TrailerSceneObject trailer = new TrailerSceneObject(new ObjectConfig());
        trailer.pull(new Vector(-1.0f, 0f));
        assertEquals(-1.0f, trailer.x, 0.01);
        assertEquals(0.0f, trailer.y, 0.01);
        assertEquals(0.0f, trailer.getRotation(), 0.01);
    }

	@Test
    public void testPull3()
    {
        TrailerSceneObject trailer = new TrailerSceneObject(new ObjectConfig());
        trailer.pull(new Vector(0.0f, 1.0f));
        assertEquals(0.0f, trailer.x, 0.01);
        assertEquals(1.0f, trailer.y, 0.01);
        assertEquals(15.94f, trailer.getRotation(), 0.01);
    }

	@Test
    public void testPull4()
    {
        TrailerSceneObject trailer = new TrailerSceneObject(new ObjectConfig());
        trailer.pull(new Vector(0.0f, -1.0f));
        assertEquals(0.0f, trailer.x, 0.01);
        assertEquals(-1.0f, trailer.y, 0.01);
        assertEquals(-15.94f, trailer.getRotation(), 0.01);
    }

}
