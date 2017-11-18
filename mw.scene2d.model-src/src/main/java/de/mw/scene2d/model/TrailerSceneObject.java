package de.mw.scene2d.model;


public class TrailerSceneObject extends VehicleSceneObject
{
    
    public TrailerSceneObject(ObjectConfig config)
    {
        left = config.left;
        right = config.right;
        front = config.front;
        back = config.back;
        axis = config.axis;
    }
    
    @Override
    public void update(float time, float td)
    {
//        addPositionRelative(0.3f, 0f);
//        rotate(-4f);
    }
    
    @Override
    public void pull(Vector d)
    {
        this.position.x += d.x;
        this.position.y += d.y;
        
        Vector rotorI = new Vector(axis, 0);
        Vector rotor = getAbsolute(rotorI);
        Vector dir = d.sub(rotor);
        Vector dirN = dir.normalize();
        
        double angle = Math.asin(dirN.y) / Math.PI * 180;
        if(dirN.x < 0)
        {
            angle = 180 - angle;
        }
        setRotation((float)angle);
    }
}
