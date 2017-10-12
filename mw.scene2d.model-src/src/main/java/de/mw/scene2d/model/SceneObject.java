package de.mw.scene2d.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class SceneObject implements SceneListener, Serializable
{
    public float rotation = 0f;
    public float x = 0f;
    public float y = 0f;
    
    public boolean active = false;
    
    public List<SceneObject> childList = new ArrayList<SceneObject>();
    
    
    public SceneObject()
    {
        
    }
    
    @Override
    public void update(float time, float td)
    {
        
    }
    
    public void setPosition(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void addPosition(float x, float y)
    {
        this.x += x;
        this.y += y;
    }

    /**
     * Bewegt das Objekt in Objektkoordinaten.
     */
    public void addPositionRelative(float dx, float dy)
    {
        double rotationRad = rotation / 180.0 * Math.PI;
        double s = Math.sin(rotationRad);
        double c = Math.cos(rotationRad);
        
        double ax = c * dx - s * dy;
        double ay = s * dx + c * dy;

        x += ax;
        y += ay;
    }

    public Vector getAbsolute(Vector p)
    {
        double rotationRad = rotation / 180.0 * Math.PI;
        double s = Math.sin(rotationRad);
        double c = Math.cos(rotationRad);

        double ax = c * p.x - s * p.y;
        double ay = s * p.x + c * p.y;
        
        Vector result = new Vector((float)ax, (float)ay);
        return result;
    }

    public Point getAbsolute(Point p)
    {
        double rotationRad = rotation / 180.0 * Math.PI;
        double s = Math.sin(rotationRad);
        double c = Math.cos(rotationRad);

        double ax = c * p.x - s * p.y + x;
        double ay = s * p.x + c * p.y + y;
        
        Point result = new Point((float)ax, (float)ay);
        return result;
    }

    public static Point getAbsolute(Point p, float rotation, float x, float y)
    {
        double rotationRad = rotation / 180.0 * Math.PI;
        double s = Math.sin(rotationRad);
        double c = Math.cos(rotationRad);

        double ax = c * p.x - s * p.y + x;
        double ay = s * p.x + c * p.y + y;
        
        Point result = new Point((float)ax, (float)ay);
        return result;
    }
    
    public Point getRelative(Point p)
    {
        double rotationRad = rotation / 180.0 * Math.PI;
        double s = Math.sin(rotationRad);
        double c = Math.cos(rotationRad);

        double ax =  c * p.x + s * p.y - (c * x + s * y);
        double ay = -s * p.x + c * p.y + (s * x - c * y);
        
        Point result = new Point((float)ax, (float)ay);
        return result;
    }
    
    
    public void pull(Vector d)
    {}
    
    public float getRotation()
    {
        return rotation;
    }

    public void setRotation(float rotation)
    {
        this.rotation = rotation;
    }

    public void rotate(float rotation)
    {
        this.rotation += rotation;
        this.rotation = (this.rotation + 180f) % 360 - 180f;
    }
}
