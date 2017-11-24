package de.mw.scene2d.swing.game;

import de.mw.scene2d.model.CarSceneObject;
import de.mw.scene2d.model.Scene;
import de.mw.scene2d.model.SceneListener;

public class Driver implements SceneListener
{
    private Scene mScene;
    private CarSceneObject mCar;
    
    public Driver(Scene scene, CarSceneObject car)
    {
        mScene = scene;
        mCar = car;
    }
    
    @Override
    public void update(float time, float td)
    {
        mCar.velocity = 5.0f;
        mCar.steeringAngle = 20;
        
        
    }
}
