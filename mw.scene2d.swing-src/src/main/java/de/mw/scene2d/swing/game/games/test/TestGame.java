package de.mw.scene2d.swing.game.games.test;

import java.io.File;
import java.util.List;

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
import de.mw.scene2d.swing.game.games.Game;
import de.mw.scene2d.swing.util.IOUtilities;

public class TestGame extends Game
{
    public static void main(String[] arguments) throws Exception
    {
    	Game game = new TestGame();
    	game.run();
    }
	
	public void setup()
	{
		SwingGround ground = SwingGround.createSampleGround(new File("res/city/map-48x48.bin"),
				new File("res/city/road-tileset.xml"), new File("res/city/roof-tileset.xml"), new File("res/city/floor-tileset.xml"), 
				new File("res/city/hedge-tileset.xml"), new File("res/city/tree-tileset.xml"));

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
			SignSceneObject sign1 = new SignSceneObject("Sign1");
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

		// Truck
		if (true)
		{
			ObjectConfig carConfig = IOUtilities.readObjectConfig("CarConfig-003.xml");

			scenePanel.mCar = new CarSceneObject(scene, carConfig);
			scenePanel.mCar.setPosition(30f, 17.5f);
			mSceneObjectList.add(scenePanel.mCar);
			scenePanel.mCar.setDamageListener(scenePanel);

			// mSignRenderer.setCar(mCar);
		}

		// Truck trailer
		if (true)
		{
			ObjectConfig trailerConfig = IOUtilities.readObjectConfig("TrailerConfig-003.xml");
			TrailerSceneObject trailer = new TrailerSceneObject(trailerConfig);
			trailer.setPosition(27.9f, 17.5f);
			mSceneObjectList.add(trailer);
			scenePanel.mCar.childList.add(trailer);
		}

		// car 1
		if (false)
		{
			ObjectConfig carConfig = IOUtilities.readObjectConfig("CarConfig-001.xml");

			CarSceneObject car = new CarSceneObject(scene, carConfig);
			car.setPosition(420f, 505f);
			car.rotate(180);
			mSceneObjectList.add(car);
			car.setDamageListener(scenePanel);

			// scenePanel.mCarList.add(car);
		}

		// self-driving car
		if (false)
		{
			ObjectConfig carConfig = IOUtilities.readObjectConfig("CarConfig-001.xml");

			CarSceneObject car = new CarSceneObject(scene, carConfig);
			car.setPosition(420f, 510f);
			car.rotate(180);
			mSceneObjectList.add(car);
			car.setDamageListener(scenePanel);

			Driver driver = new Driver(scene, car);
			scene.getSceneListenerList().add(driver);
		}

		// small map - self-driving car
		if (false)
		{
			ObjectConfig carConfig = IOUtilities.readObjectConfig("CarConfig-001.xml");

			CarSceneObject car = new CarSceneObject(scene, carConfig);
			scenePanel.mCar = car;
			car.setPosition(20f, 7.5f);
			// car.rotate(180);
			mSceneObjectList.add(car);
			car.setDamageListener(scenePanel);
			// scenePanel.mCarList.add(car);

			// Driver driver = new Driver(mScene, car);
			// mScene.getSceneListenerList().add(driver);
		}

	}
	
	@Override
	public void start()
	{
		// TODO Auto-generated method stub
		
	}

}
