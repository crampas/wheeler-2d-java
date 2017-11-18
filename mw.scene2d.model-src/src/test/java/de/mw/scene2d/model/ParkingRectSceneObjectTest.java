package de.mw.scene2d.model;

import org.junit.Assert;
import org.junit.Test;




public class ParkingRectSceneObjectTest
{
	@Test
    public void inFrame()
    {
		ParkingRectSceneObject park = new ParkingRectSceneObject(0, 0, 10, 10);
		
		VehicleSceneObject vehicle = new VehicleSceneObject();
		vehicle.setPosition(5, 5);
		vehicle.left = -1;
		vehicle.right = 1;
		vehicle.front = 1;
		vehicle.back = -1;
        
        Assert.assertTrue(park.inFrame(vehicle));
    }

	@Test
    public void inFrame_MovedRegions()
    {
		ParkingRectSceneObject park = new ParkingRectSceneObject(10, 10, 10, 10);
		
		VehicleSceneObject vehicle = new VehicleSceneObject();
		vehicle.setPosition(15, 15);
		vehicle.left = -1;
		vehicle.right = 1;
		vehicle.front = 1;
		vehicle.back = -1;
        
        Assert.assertTrue(park.inFrame(vehicle));
    }

	@Test
    public void inFrame_Rotation()
    {
		ParkingRectSceneObject park = new ParkingRectSceneObject(10, 10, 10, 10);
		
		VehicleSceneObject vehicle = new VehicleSceneObject();
		vehicle.setPosition(15, 15);
		vehicle.setRotation(20);
		vehicle.left = -1;
		vehicle.right = 1;
		vehicle.front = 1;
		vehicle.back = -1;
        
        Assert.assertTrue(park.inFrame(vehicle));
    }

	@Test
    public void inFrame_NotCompleteInFrame()
    {
		ParkingRectSceneObject park = new ParkingRectSceneObject(10, 10, 10, 10);
		
		VehicleSceneObject vehicle = new VehicleSceneObject();
		vehicle.setPosition(10, 10);
		vehicle.setRotation(20);
		vehicle.left = -1;
		vehicle.right = 1;
		vehicle.front = 1;
		vehicle.back = -1;
        
        Assert.assertFalse(park.inFrame(vehicle));
    }

}
