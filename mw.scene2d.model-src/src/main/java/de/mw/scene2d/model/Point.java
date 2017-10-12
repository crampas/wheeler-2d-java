package de.mw.scene2d.model;

import java.io.Serializable;

public class Point implements Serializable
{
    public float x;
    public float y;

    public Point()
    {
        
    }
    
    public Point(Point rhs)
    {
        x = rhs.x;
        y = rhs.y;
    }
    
    public Point(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
    
    public Vector sub(Point rhs)
    {
        float x = this.x - rhs.x;
        float y = this.y - rhs.y;
        return new Vector(x, y);
    }

    public void set(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void move(float dx, float dy)
    {
        x += dx;
        y += dy;
    }
    
    @Override
    public String toString()
    {
        return String.format("(%f,%f)", x, y);
    }
    
    @Override
    public boolean equals(Object rhs)
    {
        Point rhsPoint = (Point)rhs;
        return rhsPoint.x == x && rhsPoint.y == y;
    }
    
    @Override
    public int hashCode()
    {
        long bits = java.lang.Float.floatToIntBits(x);
        bits ^= java.lang.Float.floatToIntBits(y) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }
}