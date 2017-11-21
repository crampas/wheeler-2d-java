package de.mw.scene2d.swing.game.games.delivery;

import java.io.File;
import java.util.List;

import de.mw.scene2d.model.CarSceneObject;
import de.mw.scene2d.model.ObjectConfig;
import de.mw.scene2d.model.ParkingRectSceneObject;
import de.mw.scene2d.model.Point;
import de.mw.scene2d.model.Scene;
import de.mw.scene2d.model.SceneObject;
import de.mw.scene2d.model.SignSceneObject;
import de.mw.scene2d.model.SpriteSceneObject;
import de.mw.scene2d.model.TrailerSceneObject;
import de.mw.scene2d.swing.game.ScenePanel;
import de.mw.scene2d.swing.game.SwingGround;
import de.mw.scene2d.swing.game.games.Game;
import de.mw.scene2d.swing.util.IOUtilities;

public class DeliveryGame extends Game
{
	public static void main(String[] arguments) throws Exception
	{
		Game game = new DeliveryGame();
		game.run();
	}

	public void setup(ScenePanel scenePanel)
	{
		ScenePanel.ViewportVelocityScale = false;

		SwingGround ground = SwingGround.createSampleGround(new File("res/city/map-48x48.bin"),
				new File("res/city/road-tileset.xml"), new File("res/city/roof-tileset.xml"),
				new File("res/city/floor-tileset.xml"), new File("res/city/hedge-tileset.xml"));

		scenePanel.mGround = ground;
		scenePanel.mScene.setGround(ground);
		Scene scene = scenePanel.mScene;

		List<SceneObject> mSceneObjectList = scenePanel.mScene.getSceneObjectList();

		ObjectConfig carConfig = IOUtilities.readObjectConfig("CarConfig-001.xml");
		CarSceneObject car = new CarSceneObject(scene, carConfig);
		car.setPosition(20f, 17.5f);
		scenePanel.mCar = car;
		mSceneObjectList.add(car);
		car.setDamageListener(scenePanel);

		ObjectConfig trailerConfig = IOUtilities.readObjectConfig("TrailerConfig-001.xml");
		TrailerSceneObject trailer = new TrailerSceneObject(trailerConfig);
		trailer.setPosition(16f, 17.5f);
		mSceneObjectList.add(trailer);
		scenePanel.mCar.childList.add(trailer);

		ParkingRectSceneObject parking = new ParkingRectSceneObject(55, 61, 4f, 8f);
		parking.addVehicle(trailer);
		mSceneObjectList.add(parking);
		
		SignSceneObject sign = new SignSceneObject();
		sign.position = new Point(50, 60);
		sign.active = true;
		mSceneObjectList.add(sign);

		{
			SpriteSceneObject sprite1 = new SpriteSceneObject();
			sprite1.position = new Point(30f, 18.5f);
			sprite1.setRotation(90);
			mSceneObjectList.add(sprite1);
		}
		{
			SpriteSceneObject sprite = new SpriteSceneObject();
			sprite.position = new Point(40f, 18.5f);
			sprite.setRotation(90);
			mSceneObjectList.add(sprite);
		}
		{
			SpriteSceneObject sprite = new SpriteSceneObject();
			sprite.position = new Point(45f, 18.5f);
			sprite.setRotation(90);
			mSceneObjectList.add(sprite);
		}
	}
}
