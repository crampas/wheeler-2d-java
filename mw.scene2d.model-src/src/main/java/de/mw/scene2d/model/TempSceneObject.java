package de.mw.scene2d.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class TempSceneObject extends SceneObject
{
    public int id = 0;
    public float velocity = 0.0f;
    public float targetVelocity = 5.0f;
    public Point targetNavPoint;
    public Point nextNavPoint;
    
    private List<SceneObject> mSceneObjectList;
    
    private Ground mGround;
    
    public TempSceneObject(Ground ground, int firstNavPointIndex, List<SceneObject> sceneObjectList)
    {
        id = firstNavPointIndex;
        mGround = ground;
//        Point startPoint = mGround.mNavPathEdgeList.get(firstNavPointIndex).p0;
        
        Point startPoint = mGround.getNavPointList().get(firstNavPointIndex);
        
        this.x = startPoint.x;
        this.y = startPoint.y;
        
        targetNavPoint = nextTargetPoint();
        nextNavPoint = mGround.getNextTrackPoint(startPoint, targetNavPoint);
        
//        nextNavPoint = nextNavPoint(startPoint);

        //=====================================================================
        // Initiale Ausrichtung einstellen  
        //---------------------------------------------------------------------
        float navDx = nextNavPoint.x - x;
        float navDy = nextNavPoint.y - y;
        float tanRotation = navDy / navDx;
        float targetRotation = (float)(Math.atan(tanRotation) / Math.PI * 180.0);
        if(navDx < 0)
        {
            targetRotation = 180.0f + targetRotation;
        }
        targetRotation = (targetRotation + 180f) % 360 - 180f;
        setRotation(targetRotation);
        //=====================================================================
        
        System.out.println("first  " + x + ";" + y);
        
        mSceneObjectList = sceneObjectList;
    }
    
    private Point nextNavPoint(Point p)
    {
        if(p.equals(targetNavPoint))
        {
            targetNavPoint = nextTargetPoint();
            return mGround.getNextTrackPoint(p, targetNavPoint);
        }
            
        Point next = mGround.getNextTrackPoint(p, targetNavPoint);
        return next;
        
        
//        List<Edge> edgeList = mGround.getNavPointEdgeList(p);
//        if(edgeList == null)
//        {
//            return null;
//        }
//        List<Point> nextPossiblePoint = new ArrayList<SceneObject.Point>(edgeList.size());
//        for (Edge edge : edgeList)
//        {
//            boolean greaterLimit = calcIsNavigationPathGreater(edge, 2);
//            if(greaterLimit)
//            {
//                nextPossiblePoint.add(edge.p1);
//            }
//        }
//        if(nextPossiblePoint.size() == 0)
//        {
//            return null;
//        }
//        
//        int nextIndex = new Random().nextInt(nextPossiblePoint.size());
//        return nextPossiblePoint.get(nextIndex);
    }
    
    private Point nextTargetPoint()
    {
        List<Point> navPointList = mGround.getNavPointList();
        int navPointIndex = new Random().nextInt(navPointList.size());
        Point navPoint = navPointList.get(navPointIndex);
        return navPoint;
    }

    private boolean calcIsNavigationPathGreater(Edge edge, int length)
    {
        if(length == 0)
        {
            return true;
        }
        
        Point nextPoint = edge.p1;
        List<Edge> nextEdgeList = mGround.getNavPointEdgeList(nextPoint);
        if(nextEdgeList == null)
        {
            return false;
        }
        boolean greater = false;
        for (Edge nextEdge : nextEdgeList)
        {
            greater |= calcIsNavigationPathGreater(nextEdge, length - 1);
        }
        return greater;
    }
    
    @Override
    public void update(float time, float td)
    {
        if(nextNavPoint == null)
        {
            return;
        }

        //=====================================================================
        // Check ob ein Object zu nahe ist  
        //---------------------------------------------------------------------
        double rotationRad = - rotation / 180.0 * Math.PI;
        float rotationSin = (float)Math.sin(rotationRad);
        float rotationCos = (float)Math.cos(rotationRad);


        boolean near = false;
        for (SceneObject object : mSceneObjectList)
        {
            if(object == this || !(object instanceof TempSceneObject))
            {
                continue;
            }
            
            float objectDx = object.x - x;
            float objectDy = object.y - y;

            float objectX = objectDx * rotationCos - objectDy * rotationSin;
            float objectY = objectDx * rotationSin + objectDy * rotationCos;

            if(objectY > -2f && objectY < 2f && objectX > 0 && objectX < 10)
            {
                near = true;
                break;
            }
        }
        if(near)
        {
            velocity = 0;
        }
        else
        {
            velocity = targetVelocity;
        }

        //=====================================================================
        // nächsten Navigationspunkt prüfen  
        //---------------------------------------------------------------------
        float navDx = nextNavPoint.x - x;
        float navDy = nextNavPoint.y - y;
        float navD = navDx * navDx + navDy * navDy;
        if(navD < 1.0f)
        {
            // Navigationspunkt erreicht, nächsten Navigationspunkt einstellen
            nextNavPoint = nextNavPoint(nextNavPoint);
            if(nextNavPoint == null)
            {
                return;
            }
            navDx = nextNavPoint.x - x;
            navDy = nextNavPoint.y - y;
        }
        
        //=====================================================================
        // Ausrichtung auf den nächsten Navigationspunkt  
        //---------------------------------------------------------------------
        
        float tanRotation = navDy / navDx;
        float targetRotation = (float)(Math.atan(tanRotation) / Math.PI * 180.0);
        if(navDx < 0)
        {
            targetRotation = 180.0f + targetRotation;
        }
        targetRotation = (targetRotation + 180f) % 360 - 180f;

        float dRotation = targetRotation + 360f - (rotation + 360f);
        dRotation = (dRotation + 180f) % 360f - 180f;
        
        float alteredRotation = Math.min(Math.abs(dRotation), 10.0f) * Math.signum(dRotation);
        rotate(alteredRotation);

        //=====================================================================
        // Bewegung  
        //---------------------------------------------------------------------
        
        double v = velocity; // m/s
        double dx = v * td;
        addPositionRelative((float)dx, 0f);

        //=====================================================================
    }
}
