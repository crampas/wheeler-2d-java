package de.mw.scene2d.model;

import java.io.Serializable;

public class Edge implements Serializable
{
    public Point p0;
    public Point p1;
    
    public Edge()
    {
    }
    
    /**
     * Erzeugt ein neues Edge Objeke als komplette Kopie.
     * Auch die Unterstrukturen werden kopiert.
     * @param rhs zu kopierendes Objekt
     */
    public Edge(Edge rhs)
    {
        p0 = new Point(rhs.p0);
        p1 = new Point(rhs.p1);
    }
    
    public Edge(Point p0, Point p1)
    {
        this.p0 = p0;
        this.p1 = p1;
    }
    
    public Edge(float x0, float y0, float x1, float y1)
    {
        this.p0 = new Point(x0, y0);
        this.p1 = new Point(x1, y1);
    }

    public void move(float dx, float dy)
    {
        p0.move(dx, dy);
        p1.move(dx, dy);
    }
}