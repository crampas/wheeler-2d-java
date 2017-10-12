package de.mw.scene2d.model;

import java.io.Serializable;

public class Rect implements Serializable
{
    public float x;
    public float y;
    public float width;
    public float height;
    
    public Rect()
    {}
    
    public Rect(float x, float y, float width, float height)
    {
    	this.x = x;
    	this.y = y;
    	this.width = width;
    	this.height = height;
    }
    
    public boolean contains(Point p)
    {
        return p.x >= x && p.x <= x + width && p.y >= y && p.y <= y + height;
    }
    
    @Override
    public String toString()
    {
        return String.format("(%f,%f,%f,%f)", x, y, width, height);
    }
}