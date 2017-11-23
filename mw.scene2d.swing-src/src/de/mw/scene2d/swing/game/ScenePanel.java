package de.mw.scene2d.swing.game;

import java.util.ArrayList;
import java.util.List;

import de.mw.scene2d.model.CarDamageListener;
import de.mw.scene2d.model.CarSceneObject;
import de.mw.scene2d.model.Scene;
import de.mw.scene2d.model.SceneListener;
import de.mw.scene2d.model.SceneObject;
import de.mw.scene2d.swing.view.SwingSceneView;

public class ScenePanel extends SwingSceneView implements CarDamageListener
{
    public static boolean ViewportRotateToHeading = true;
    public static boolean ViewportRotateToHelmHeading = false;
    public static double ViewportScale = 20; // 20; // 5
    public static boolean ViewportVelocityScale = true;
    
    public CarSceneObject mCar;
    public List<CarSceneObject> mCarList = new ArrayList<CarSceneObject>();
    
    public SwingGround mGround;

    
    public ScenePanel()
    {
        super(new Scene());
    }
    
    public CarSceneObject getCar()
    {
        return mCar;
    }


    public void update(float dt)
    {
        for (SceneObject object : mScene.getSceneObjectList())
        {
            object.update(0, dt);
        }

        for (SceneListener listener : mScene.getSceneListenerList())
        {
            listener.update(0, dt);
        }
        
        // Vergrößerung bei niedrigen Geschwindigkeiten
        if(ViewportVelocityScale)
        {
            double viewportScale = Math.min(Math.max(ViewportScale - mCar.velocity / 13.0 * 10.0, 8.0), ViewportScale);
            setViewportScale(viewportScale);
        }
        
        if(ViewportRotateToHeading)
        {
            // auf Blickrichtung des Steuerräder rotieren
            float viewportRotation = -(mCar.getRotation() + 90); 
            setViewportRotation(viewportRotation);
        }

        if(ViewportRotateToHelmHeading)
        {
            // auf Blickrichtung des Steuerräder rotieren
            float viewportRotation = -(mCar.getRotation() + mCar.steeringAngle + 90); 
            setViewportRotation(viewportRotation);
        }
        
        // auf Wagen zentrieren
        getViewportPosition().set(mCar.position.x, mCar.position.y);

        
        repaint();
    }

    public void switchCar()
    {
        int currentIndex = mCarList.indexOf(mCar);
        int nextIndex = (currentIndex + 1) % mCarList.size();
        mCar = mCarList.get(nextIndex);
    }
    
    public void uncoupleTrailer()
    {
    	mCar.childList.clear();
    }

    private List<CarDamageListener> mCarDamageListener = new ArrayList<CarDamageListener>();
    
    @Override
    public void onCarDamaged(CarSceneObject car, float damage)
    {
        if(mCarDamageListener != null)
        {
        	for (CarDamageListener carDamageListener : mCarDamageListener)
        	{
        		carDamageListener.onCarDamaged(car, damage);
			}
        }
    }

    public void addCarDamageListener(CarDamageListener carDamageListener)
    {
        mCarDamageListener.add(carDamageListener);
    }
}
