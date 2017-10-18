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
        Point[] childPositionList = new Point[childList.size()]; 
        for(int index = 0; index < childList.size(); index++)
        {
            SceneObject childObject = childList.get(index);
            Point p0 = new Point(childObject.position.x, childObject.position.y);
            childPositionList[index] = getRelative(p0);
        }


        Vector helmDirection = Angle.fromDegree(steeringAngle);
        Vector helmPull = helmDirection.multiply(velocity * td);
        Vector pull = this.getAbsolute(helmPull);
        pull(pull);
        
        
        boolean hitFL = checkDamageHit(axis, left);
        boolean hitFR = checkDamageHit(axis, right);
        boolean hitBL = checkDamageHit(0, left);
        boolean hitBR = checkDamageHit(0, right);
        boolean hit = hitFL || hitFR || hitBL || hitBR;
        if(hit)
        {
            Vector hitPull = new Vector(-1, 0).multiply(velocity * td * 1);
            System.out.println(hitPull);
            pull(getAbsolute(hitPull));

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
            
            Point paOld = new Point(childObject.position.x, childObject.position.y);
            Vector diff = paNew.sub(paOld);
//            System.out.println(pr + " : " + paNew + " - " + paOld + " = " + diff);
            childObject.pull(diff);
        }
    }

    
    @Override
    public void pull(Vector d)
    {
    	this.position.x += d.x;
    	this.position.y += d.y;
    	
    	Vector axisVector = this.direction.multiply(-axis);
    	direction = axisVector.add(d).normalize(); 
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
