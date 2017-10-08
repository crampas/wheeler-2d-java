package de.mw.scene2d.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class SceneObject implements SceneListener, Serializable
{
    public static class Point implements Serializable
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
    
    public static class Edge implements Serializable
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
    
    public static class Rect implements Serializable
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
    
    public static class Vector implements Serializable
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
    
    public float rotation = 0f;
    public float x = 0f;
    public float y = 0f;
    
    public boolean active = false;
    
    public List<SceneObject> childList = new ArrayList<SceneObject>();
    
//    public final static  Paint DEFAULT_PAINT;
//    
//    static
//    {
//        DEFAULT_PAINT = new Paint();
//        DEFAULT_PAINT.setStyle(Style.STROKE);
//        DEFAULT_PAINT.setColor(0xffffffff);
//    }
    
    
    
    public SceneObject()
    {
        
    }
    
    @Override
    public void update(float time, float td)
    {
        
    }
    
//    public void render(Canvas canvas)
//    {
//        canvas.save();
//        canvas.translate(x, y);
//        canvas.rotate(mRotation);
//        drawObject(canvas);
//        canvas.restore();
//    }
    
//    protected void drawObject(Canvas canvas)
//    {
//        // Einheitskreis
//        canvas.drawCircle(0f, 0f, 1f, DEFAULT_PAINT);
//        // Achsen andeuten
//        canvas.drawLine(0f, 0f, 2f, 0f, DEFAULT_PAINT);
//        canvas.drawLine(0f, 0f, 0f, 1f, DEFAULT_PAINT);
//    }
    
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
