package de.mw.scene2d.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Ground
{
    private int width = 0;
    public int height = 0;
    public GroundTile[] tile;
    
    protected List<Edge> mNavPathEdgeList = new ArrayList<Edge>();
    protected Map<Point, List<Edge>> mNavPointEdgeListMap = new HashMap<Point, List<Edge>>();
    protected List<Point> mNavPointList = new ArrayList<Point>();
    
    

    public GroundTile getTile(float x, float y)
    {
        int xIndex = (int)Math.floor(x / 10);
        int yIndex = (int)Math.floor(y / 10);
        int index = getTileIndex(xIndex, yIndex);
        return tile[index];
    }
    
    public Vector getTileOffset(float x, float y)
    {
        float xOffset = (int)Math.floor(x / 10) * 10;
        float yOffset = (int)Math.floor(y / 10) * 10;
        return new Vector(xOffset, yOffset);
    }
    
    
    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getTileIndex(int x, int y);

    
    public void addNavPathEdge(Edge edge)
    {
        mNavPathEdgeList.add(edge);
        Point startPoint = edge.p0;
        List<Edge> edgeList = mNavPointEdgeListMap.get(startPoint);
        if(edgeList == null)
        {
            edgeList = new ArrayList<Edge>();
            mNavPointEdgeListMap.put(startPoint, edgeList);
        }
        edgeList.add(edge);
        
        Point endPoint = edge.p1;
        List<Edge> endPointEdgeList = mNavPointEdgeListMap.get(endPoint);
        if(endPointEdgeList == null)
        {
            mNavPointEdgeListMap.put(endPoint, new ArrayList<Edge>());
        }
        
    }

    public List<Edge> getNavPathEdgeList()
    {
        return mNavPathEdgeList;
    }

    public List<Edge> getNavPointEdgeList(Point p)
    {
        return mNavPointEdgeListMap.get(p);
    }

    public List<Point> getNavPointList()
    {
        return mNavPointList;
    }
    
    public abstract Point getNextTrackPoint(Point position, Point target);
}
