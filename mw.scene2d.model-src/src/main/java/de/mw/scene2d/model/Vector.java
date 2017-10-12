package de.mw.scene2d.model;

import java.io.Serializable;

public class Vector implements Serializable
{
    public float x;
    public float y;
    
    public Vector()
    {}
    
    public Vector(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
    
    public Vector sub(Vector rhs)
    {
        float x = this.x - rhs.x;
        float y = this.y - rhs.y;
        return new Vector(x, y);
    }
    
    public Vector normalize()
    {
        float length2 = x * x + y * y;
        float length = (float)Math.sqrt(length2);
        float nx = x / length;
        float ny = y / length;
        return new Vector(nx, ny);
    }
    
    
    @Override
    public String toString()
    {
        return String.format("(%f,%f)", x, y);
    }
}