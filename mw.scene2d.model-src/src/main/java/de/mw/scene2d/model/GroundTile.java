package de.mw.scene2d.model;

import java.util.ArrayList;
import java.util.List;

public class GroundTile
{
    public String imageId;
    public int tileIndexX;
    public int tileIndexY;
    public List<Edge> edge = new ArrayList<Edge>();
    public List<Rect> damageRect = new ArrayList<Rect>();

    
    public GroundTile()
    {}
    
    public GroundTile(String imageId, int tileIndexX, int tileIndexY)
    {
        this.imageId = imageId;
        this.tileIndexX = tileIndexX;
        this.tileIndexY = tileIndexY;
    }
    
    public void addEdge(Edge item)
    {
        edge.add(item);
    }
    
    public void addPointPath(Point... pointList)
    {
        for(int index = 0; index < pointList.length - 1; ++index)
        {
            addEdge(new Edge(pointList[index], pointList[index + 1]));
        }
    }
    
    public Point getEdgePoint(int index)
    {
        if(index >= edge.size())
        {
            return edge.get(index - 1).p1;
        }
        return edge.get(index).p1;
    }
    
    public void addDamageRect(Rect rect)
    {
    	damageRect.add(rect);
    }

    public List<Rect> getDamageRectList()
    {
        return damageRect;
    }
}
