package de.mw.scene2d.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class SceneObject implements SceneListener, Serializable
{
	public Point position = new Point(0f, 0f);
	public Vector direction = new Vector(1f, 0f);
	
	
//    public float x = 0f;
//    public float y = 0f;
    
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
        this.position.x = x;
        this.position.y = y;
    }

    public void addPosition(float x, float y)
    {
        this.position.x += x;
        this.position.y += y;
    }

    /**
     * Bewegt das Objekt in Objektkoordinaten.
     */
    public void addPositionRelative(float dx, float dy)
    {
        double ax = direction.x * dx - direction.y * dy;
        double ay = direction.y * dx + direction.x * dy;

        position.x += ax;
        position.y += ay;
    }

    public Vector getAbsolute(Vector p)
    {
        double ax = direction.x * p.x - direction.y * p.y;
        double ay = direction.y * p.x + direction.x * p.y;
        
        Vector result = new Vector((float)ax, (float)ay);
        return result;
    }

    public Point getAbsolute(Point p)
    {
        double ax = direction.x * p.x - direction.y * p.y + position.x;
        double ay = direction.y * p.x + direction.x * p.y + position.y;
        
        Point result = new Point((float)ax, (float)ay);
        return result;
    }

    public Point getRelative(Point p)
    {
        double ax =  direction.x * p.x + direction.y * p.y - (direction.x * position.x + direction.y * position.y);
        double ay = -direction.y * p.x + direction.x * p.y + (direction.y * position.x - direction.x * position.y);
        
        Point result = new Point((float)ax, (float)ay);
        return result;
    }
    
    
    public void pull(Vector d)
    {}
    
    public float getRotation()
    {
        return Angle.getDegree(direction);
    }

    public void setRotation(float angle)
    {
    	this.direction = Angle.fromDegree(angle);
    }

    public void rotate(float rotation)
    {
    	float newRotation = Angle.getDegree(direction) + rotation; 
    	this.setRotation(newRotation);
    }
}
