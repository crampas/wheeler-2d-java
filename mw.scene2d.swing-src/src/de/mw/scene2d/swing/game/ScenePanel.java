package de.mw.scene2d.swing.game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import de.mw.scene2d.model.CarDamageListener;
import de.mw.scene2d.model.CarSceneObject;
import de.mw.scene2d.model.ObjectConfig;
import de.mw.scene2d.model.ObstacleSceneObject;
import de.mw.scene2d.model.Scene;
import de.mw.scene2d.model.SceneListener;
import de.mw.scene2d.model.SceneObject;
import de.mw.scene2d.model.SignSceneObject;
import de.mw.scene2d.model.TempSceneObject;
import de.mw.scene2d.model.TrailerSceneObject;
import de.mw.scene2d.swing.view.SwingSceneView;

public class ScenePanel extends SwingSceneView implements CarDamageListener
{
    public static boolean ViewportRotateToHeading = true;
    public static double ViewportScale = 20; // 20; // 5
    public static boolean ViewportVelocityScale = true;
    
    private CarSceneObject mCar;
    private List<CarSceneObject> mCarList = new ArrayList<CarSceneObject>();
    
    private TrailerSceneObject mTrailer;

    private SampleGround mGround;

    
    public ScenePanel()
    {
        super(new Scene());
        
//        mGround = SampleGround.createSampleGround(new File("res/street/SampleGround.xml"),
//                new File("res/street/Ground-100x100.bin"));
        mGround = SampleGround.createSampleGround(new File("res/street/SampleGround.xml"),
                new File("res/street/Ground-10x10.bin"));
        
        mScene.setGround(mGround);

        List<SceneObject> mSceneObjectList = mScene.getSceneObjectList();
        {
            ObstacleSceneObject obstacle = new ObstacleSceneObject(mSceneObjectList);
            obstacle.setPosition(30, 10f);
            mSceneObjectList.add(obstacle);
        }

        {
            SignSceneObject sign1 = new SignSceneObject();
            sign1.setPosition(34f, 20f);
            mSceneObjectList.add(sign1);
        }

        mGround.calcShortPaths();
        mSceneObjectList.add(new TempSceneObject(mGround, 0, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(mGround, 55, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(mGround, 102, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(mGround, 22, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(mGround, 48, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(mGround, 97, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(mGround, 16, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(mGround, 29, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(mGround, 47, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(mGround, 35, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(mGround, 8, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(mGround, 24, mSceneObjectList));

        
        try
        {
            JAXBContext jaxbObjectConfigContext = JAXBContext.newInstance(ObjectConfig.class);
            Unmarshaller jaxbObjectConfigUnmarshaller = jaxbObjectConfigContext.createUnmarshaller();
            
            // Truck 
            if(false)
            {
                ObjectConfig carConfig = (ObjectConfig)jaxbObjectConfigUnmarshaller.unmarshal(new File("CarConfig-002.xml"));
    
                mCar = new CarSceneObject(mScene, carConfig);
                mCar.setPosition(20f, 7.5f);
                mCar.setPosition(420f, 500f);
                mCar.rotate(180);
                mSceneObjectList.add(mCar);
                mCar.setDamageListener(this);
                
                mCarList.add(mCar);
//                mSignRenderer.setCar(mCar);
            }
            
            // Truck trailer
            if(false)
            {
                ObjectConfig trailerConfig = (ObjectConfig)jaxbObjectConfigUnmarshaller.unmarshal(new File("TrailerConfig-002.xml"));
                mTrailer = new TrailerSceneObject(trailerConfig);
                mTrailer.setPosition(19f, 7.5f);
                mTrailer.setPosition(421f, 500f);
                mTrailer.rotate(180);
                mSceneObjectList.add(mTrailer);
                mCar.childList.add(mTrailer);
            }
            
            // car 1
            if(false)
            {
                ObjectConfig carConfig = (ObjectConfig)jaxbObjectConfigUnmarshaller.unmarshal(new File("CarConfig-001.xml"));
    
                CarSceneObject car = new CarSceneObject(mScene, carConfig);
                car.setPosition(420f, 505f);
                car.rotate(180);
                mSceneObjectList.add(car);
                car.setDamageListener(this);
                
                mCarList.add(car);
            }

            // self-driving car
            if(false)
            {
                ObjectConfig carConfig = (ObjectConfig)jaxbObjectConfigUnmarshaller.unmarshal(new File("CarConfig-001.xml"));
    
                CarSceneObject car = new CarSceneObject(mScene, carConfig);
                car.setPosition(420f, 510f);
                car.rotate(180);
                mSceneObjectList.add(car);
                car.setDamageListener(this);
                
                Driver driver = new Driver(mScene, car);
                mScene.getSceneListenerList().add(driver);
            }

            
            // small map - self-driving car
            if(true)
            {
                ObjectConfig carConfig = (ObjectConfig)jaxbObjectConfigUnmarshaller.unmarshal(new File("CarConfig-001.xml"));
    
                CarSceneObject car = new CarSceneObject(mScene, carConfig);
                mCar = car;
                car.setPosition(0f, 0f);
                car.rotate(180);
                mSceneObjectList.add(car);
//                car.setDamageListener(this);
                mCarList.add(car);
                
//                Driver driver = new Driver(mScene, car);
//                mScene.getSceneListenerList().add(driver);
            }

        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }
    
    public CarSceneObject getCar()
    {
        return mCar;
    }

    long mLastUpdateTimeMillis = 0;

    public void update()
    {
        long currentTimeMillis = System.currentTimeMillis();
        float dt = ((float)(currentTimeMillis - mLastUpdateTimeMillis)) / 1000f;
        dt = Math.min(dt, 0.1f);
        
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
            float viewportRotation = -(mCar.rotation + mCar.steeringAngle + 90); 
            setViewportRotation(viewportRotation);
        }

        // auf Wagen zentrieren
        getViewportPosition().set(mCar.x, mCar.y);

        
        mLastUpdateTimeMillis = currentTimeMillis;
        repaint();
    }

    public void switchCar()
    {
        int currentIndex = mCarList.indexOf(mCar);
        int nextIndex = (currentIndex + 1) % mCarList.size();
        mCar = mCarList.get(nextIndex);
    }

    private CarDamageListener mCarDamageListener;
    
    @Override
    public void onCarDamaged(CarSceneObject car, float damage)
    {
        if(mCarDamageListener != null)
        {
            mCarDamageListener.onCarDamaged(car, damage);
        }
    }

    public CarDamageListener getCarDamageListener()
    {
        return mCarDamageListener;
    }

    public void setCarDamageListener(CarDamageListener carDamageListener)
    {
        mCarDamageListener = carDamageListener;
    }
}
