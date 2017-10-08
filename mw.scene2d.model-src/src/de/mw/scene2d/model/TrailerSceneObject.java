package de.mw.scene2d.model;


public class TrailerSceneObject extends SceneObject
{
    public float left = -1.1f;
    public float right = 1.1f;
    public float front = -0.75f; 
    public float back = -7.0f;
    public float ancor = 0f;
    public float axis = -3.5f;
    
//    private PointF mAncor = new PointF(mFront, 0f);
    
    
    public TrailerSceneObject(ObjectConfig config)
    {
        left = config.left;
        right = config.right;
        front = config.front;
        back = config.back;
        axis = config.axis;
    }
    
//    @Override
//    protected void drawObject(Canvas canvas)
//    {
//        canvas.drawLine(0f, mLeft, 0f, mRight, DEFAULT_PAINT);
//        canvas.drawLine(0f, mLeft, mFront, 0f, DEFAULT_PAINT);
//        canvas.drawLine(0f, mRight, mFront, 0f, DEFAULT_PAINT);
//    }
    
    @Override
    public void update(float time, float td)
    {
//        addPositionRelative(0.3f, 0f);
//        rotate(-4f);
    }
    
    @Override
    public void pull(Vector d)
    {
        this.x += d.x;
        this.y += d.y;
        
        Vector rotorI = new Vector(axis, 0);
        Vector rotor = getAbsolute(rotorI);
        Vector dir = d.sub(rotor);
        Vector dirN = dir.normalize();
        
        double angle = Math.asin(dirN.y) / Math.PI * 180;
        if(dirN.x < 0)
        {
            angle = 180 - angle;
        }
        rotation = (float)angle;
    }
    
}
