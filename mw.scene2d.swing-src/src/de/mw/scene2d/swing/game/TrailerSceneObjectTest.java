package de.mw.scene2d.swing.game;

import de.mw.scene2d.model.ObjectConfig;
import de.mw.scene2d.model.TrailerSceneObject;
import de.mw.scene2d.model.Vector;
import junit.framework.TestCase;

public class TrailerSceneObjectTest extends TestCase
{
    public void testPull1()
    {
        TrailerSceneObject trailer = new TrailerSceneObject(new ObjectConfig());
        trailer.pull(new Vector(1.0f, 0f));
        assertEquals(1.0f, trailer.x, 0.01);
        assertEquals(0.0f, trailer.y, 0.01);
        assertEquals(0.0f, trailer.rotation, 0.01);
    }

    public void testPull2()
    {
        TrailerSceneObject trailer = new TrailerSceneObject(new ObjectConfig());
        trailer.pull(new Vector(-1.0f, 0f));
        assertEquals(-1.0f, trailer.x, 0.01);
        assertEquals(0.0f, trailer.y, 0.01);
        assertEquals(0.0f, trailer.rotation, 0.01);
    }

    public void testPull3()
    {
        TrailerSceneObject trailer = new TrailerSceneObject(new ObjectConfig());
        trailer.pull(new Vector(0.0f, 1.0f));
        assertEquals(0.0f, trailer.x, 0.01);
        assertEquals(1.0f, trailer.y, 0.01);
        assertEquals(8.130f, trailer.rotation, 0.01);
    }

    public void testPull4()
    {
        TrailerSceneObject trailer = new TrailerSceneObject(new ObjectConfig());
        trailer.pull(new Vector(0.0f, -1.0f));
        assertEquals(0.0f, trailer.x, 0.01);
        assertEquals(-1.0f, trailer.y, 0.01);
        assertEquals(-8.130f, trailer.rotation, 0.01);
    }

}
