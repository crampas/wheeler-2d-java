package de.mw.scene2d.swing.game.games.test;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import de.mw.scene2d.model.CarSceneObject;
import de.mw.scene2d.model.ObjectConfig;
import de.mw.scene2d.model.ObstacleSceneObject;
import de.mw.scene2d.model.Scene;
import de.mw.scene2d.model.SceneObject;
import de.mw.scene2d.model.SignSceneObject;
import de.mw.scene2d.model.TempSceneObject;
import de.mw.scene2d.model.TrailerSceneObject;
import de.mw.scene2d.swing.game.Driver;
import de.mw.scene2d.swing.game.SwingGround;
import de.mw.scene2d.swing.game.ScenePanel;

public class TestGame
{
	public void setup(ScenePanel scenePanel)
	{
		SwingGround ground = SwingGround.createSampleGround(new File("res/city/map-16x16.bin"),
                new File("res/city/road-tileset.xml"));
		
		scenePanel.mGround = ground;
		scenePanel.mScene.setGround(ground);
		Scene scene = scenePanel.mScene; 

        List<SceneObject> mSceneObjectList = scenePanel.mScene.getSceneObjectList();
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

        ground.calcShortPaths();
        mSceneObjectList.add(new TempSceneObject(ground, 0, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(ground, 55, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(ground, 102, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(ground, 22, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(ground, 48, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(ground, 97, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(ground, 16, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(ground, 29, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(ground, 47, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(ground, 35, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(ground, 8, mSceneObjectList));
        mSceneObjectList.add(new TempSceneObject(ground, 24, mSceneObjectList));

        
        try
        {
            JAXBContext jaxbObjectConfigContext = JAXBContext.newInstance(ObjectConfig.class);
            Unmarshaller jaxbObjectConfigUnmarshaller = jaxbObjectConfigContext.createUnmarshaller();
            
            // Truck 
            if(true)
            {
                ObjectConfig carConfig = (ObjectConfig)jaxbObjectConfigUnmarshaller.unmarshal(new File("CarConfig-003.xml"));
    
                scenePanel.mCar = new CarSceneObject(scene, carConfig);
                scenePanel.mCar.setPosition(20f, 7.5f);
                mSceneObjectList.add(scenePanel.mCar);
                scenePanel.mCar.setDamageListener(scenePanel);
                
                scenePanel.mCarList.add(scenePanel.mCar);
//                mSignRenderer.setCar(mCar);
            }
            
            // Truck trailer
            if(true)
            {
                ObjectConfig trailerConfig = (ObjectConfig)jaxbObjectConfigUnmarshaller.unmarshal(new File("TrailerConfig-003.xml"));
                scenePanel.mTrailer = new TrailerSceneObject(trailerConfig);
                scenePanel.mTrailer.setPosition(17.9f, 7.5f);
                mSceneObjectList.add(scenePanel.mTrailer);
                scenePanel.mCar.childList.add(scenePanel.mTrailer);
            }
            
            // car 1
            if(false)
            {
                ObjectConfig carConfig = (ObjectConfig)jaxbObjectConfigUnmarshaller.unmarshal(new File("CarConfig-001.xml"));
    
                CarSceneObject car = new CarSceneObject(scene, carConfig);
                car.setPosition(420f, 505f);
                car.rotate(180);
                mSceneObjectList.add(car);
                car.setDamageListener(scenePanel);
                
                scenePanel.mCarList.add(car);
            }

            // self-driving car
            if(false)
            {
                ObjectConfig carConfig = (ObjectConfig)jaxbObjectConfigUnmarshaller.unmarshal(new File("CarConfig-001.xml"));
    
                CarSceneObject car = new CarSceneObject(scene, carConfig);
                car.setPosition(420f, 510f);
                car.rotate(180);
                mSceneObjectList.add(car);
                car.setDamageListener(scenePanel);
                
                Driver driver = new Driver(scene, car);
                scene.getSceneListenerList().add(driver);
            }

            
            // small map - self-driving car
            if(false)
            {
                ObjectConfig carConfig = (ObjectConfig)jaxbObjectConfigUnmarshaller.unmarshal(new File("CarConfig-001.xml"));
    
                CarSceneObject car = new CarSceneObject(scene, carConfig);
                scenePanel.mCar = car;
                car.setPosition(20f, 7.5f);
                // car.rotate(180);
                mSceneObjectList.add(car);
                car.setDamageListener(scenePanel);
                scenePanel.mCarList.add(car);
                
//                Driver driver = new Driver(mScene, car);
//                mScene.getSceneListenerList().add(driver);
            }

        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
	}

}
