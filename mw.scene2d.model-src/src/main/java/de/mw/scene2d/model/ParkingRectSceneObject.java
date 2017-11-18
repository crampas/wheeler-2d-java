package de.mw.scene2d.model;

import java.util.ArrayList;
import java.util.List;

public class ParkingRectSceneObject extends SceneObject
{
	private List<VehicleSceneObject> mVehicleList = new ArrayList<>();
	
    public float width;
    public float height;

	
	public ParkingRectSceneObject(float x, float y, float width, float height)
	{
		this.position.x = x;
		this.position.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void addVehicle(VehicleSceneObject vehicle)
	{
		mVehicleList.add(vehicle);
	}
	
    @Override
    public void update(float time, float td)
    {
        for (VehicleSceneObject object : mVehicleList)
        {
            active = inFrame(object);
            if(active)
                break;
        }
    }
    
    public boolean inFrame(VehicleSceneObject object)
    {
    	Rect rect = new Rect(0, 0, width, height);

    	boolean inFrame = true;
    	
        Point leftFront = getRelative(object.getAbsolute(new Point(object.front, object.left)));
        inFrame &= rect.contains(leftFront);

        Point leftBack = getRelative(object.getAbsolute(new Point(object.back, object.left)));
        inFrame &= rect.contains(leftBack);

        Point rightFront = getRelative(object.getAbsolute(new Point(object.front, object.right)));
        inFrame &= rect.contains(rightFront);

        Point rightBack = getRelative(object.getAbsolute(new Point(object.back, object.left)));
        inFrame &= rect.contains(rightBack);

        return inFrame;
    }
    
}
