package de.mw.scene2d.model;

import java.util.List;


public class CarSceneObject extends SceneObject
{
    private Scene mScene;
    private CarDamageListener mDamageListener;
    
    public float steeringAngle = 0.0f;
    public float velocity = 0.0f;
    
    public float left = -1.0f;
    public float right = 1.0f;
    public float front = 3.5f; 
    public float axis = 3.0f;
    public float back = -1f;
    public float damaged = 0f;
    
    public CarEngine engine = new CarEngine();
    
    public CarSceneObject(Scene scene, ObjectConfig config)
    {
        mScene = scene;
        
        left = config.left;
        right = config.right;
        front = config.front;
        back = config.back;
        axis = config.axis;
    }
    
    private boolean checkDamageHit(float checkX, float checkY)
    {
        Point absolute = getAbsolute(new Point(checkX, checkY));

        Ground ground = mScene.getGround();
        GroundTile tile = ground.getTile(absolute.x, absolute.y);
        
        if(tile != null)
        {
            Vector tileOffset = ground.getTileOffset(absolute.x, absolute.y);
            List<Rect> damageRectList = tile.getDamageRectList();
    
            boolean damage = false;
            Point checkPoint = new Point(absolute.x - tileOffset.x, absolute.y - tileOffset.y);
            for (Rect damageRect : damageRectList)
            {
                boolean contains = damageRect.contains(checkPoint);
                damage |= contains;
            }
            return damage;
        }
        return false;
    }
    
    @Override
    public void update(float time, float td)
    {
        double v = velocity; // m/s
        double s = v * td;
        
        double helmSin = Math.sin(steeringAngle / 180.0 * Math.PI);
        double rotationSin = s / front * helmSin;
        double rotation = Math.asin(rotationSin) / Math.PI * 180.0;
        
        Point[] childPositionList = new Point[childList.size()]; 
        for(int index = 0; index < childList.size(); index++)
        {
            SceneObject childObject = childList.get(index);
            Point p0 = new Point(childObject.x, childObject.y);
            childPositionList[index] = getRelative(p0);
        }
        
        addPositionRelative((float)s, 0f);
        rotate((float)rotation);
        
        
        boolean hitFL = checkDamageHit(axis, left);
        boolean hitFR = checkDamageHit(axis, right);
        boolean hitBL = checkDamageHit(0, left);
        boolean hitBR = checkDamageHit(0, right);
        boolean hit = hitFL || hitFR || hitBL || hitBR;
        if(hit)
        {
            float newDamage = td * velocity; 
            damaged += newDamage;
            if(mDamageListener != null)
            {
                mDamageListener.onCarDamaged(this, newDamage);
            }
        }
        
        for(int index = 0; index < childList.size(); index++)
        {
            SceneObject childObject = childList.get(index);
            Point pr = childPositionList[index];
            Point paNew = getAbsolute(pr);
            
            Point paOld = new Point(childObject.x, childObject.y);
            Vector diff = paNew.sub(paOld);
//            System.out.println(pr + " : " + paNew + " - " + paOld + " = " + diff);
            childObject.pull(diff);
        }
    }

    public CarDamageListener getDamageListener()
    {
        return mDamageListener;
    }

    public void setDamageListener(CarDamageListener damageListener)
    {
        mDamageListener = damageListener;
    }
}
