package de.mw.scene2d.swing.game.games.camping;

import java.io.File;
import java.util.List;

import de.mw.scene2d.model.CarSceneObject;
import de.mw.scene2d.model.ParkingRectSceneObject;
import de.mw.scene2d.model.Point;
import de.mw.scene2d.model.Scene;
import de.mw.scene2d.model.SceneObject;
import de.mw.scene2d.model.SpriteSceneObject;
import de.mw.scene2d.model.TrailerSceneObject;
import de.mw.scene2d.swing.game.ScenePanel;
import de.mw.scene2d.swing.game.SwingGround;
import de.mw.scene2d.swing.game.games.Game;
import de.mw.scene2d.swing.util.IOUtilities;

public class CampingGame extends Game
{
	
	public static void main(String[] arguments) throws Exception
	{
		Game game = new CampingGame();
		game.run();
	}

	public ParkingRectSceneObject tenderParking = new ParkingRectSceneObject(55, 61, 4f, 8f);
	public ParkingRectSceneObject carParking = new ParkingRectSceneObject(35.5f, 10.25f, 6f, 2.5f);
	public CarSceneObject car;
	
	public void setup()
	{
		ScenePanel.ViewportVelocityScale = false;
		scenePanel.setViewportScale(30);

		SwingGround ground = SwingGround.createSampleGround(new File("res/city/map-48x48.bin"),
				new File("res/city/road-tileset.xml"), new File("res/city/roof-tileset.xml"),
				new File("res/city/floor-tileset.xml"), new File("res/city/hedge-tileset.xml"));

		scenePanel.mGround = ground;
		scenePanel.mScene.setGround(ground);
		Scene scene = scenePanel.mScene;

		List<SceneObject> sceneObjectList = scenePanel.mScene.getSceneObjectList();

		car = new CarSceneObject(scene, IOUtilities.readObjectConfig("CarConfig-001.xml"));
		car.setPosition(20f, 17.5f);
		scenePanel.mCar = car;
		sceneObjectList.add(car);
		car.setDamageListener(scenePanel);

		TrailerSceneObject trailer = new TrailerSceneObject(IOUtilities.readObjectConfig("TrailerConfig-001.xml"));
		trailer.setPosition(16f, 17.5f);
		sceneObjectList.add(trailer);
		car.childList.add(trailer);

		tenderParking.addVehicle(trailer);		
		carParking.addVehicle(car);
		
		{
			SpriteSceneObject sprite1 = new SpriteSceneObject("taxi-2.png");
			sprite1.position = new Point(30f, 11.5f);
			sprite1.setRotation(-90);
			sceneObjectList.add(sprite1);
		}
		{
			SpriteSceneObject sprite = new SpriteSceneObject("taxi-2.png");
			sprite.position = new Point(42f, 11.5f);
			sprite.setRotation(-90);
			sceneObjectList.add(sprite);
		}
		{
			SpriteSceneObject sprite = new SpriteSceneObject("taxi-2.png");
			sprite.position = new Point(48f, 11.5f);
			sprite.setRotation(-90);
			sceneObjectList.add(sprite);
		}
		{
			SpriteSceneObject sprite = new SpriteSceneObject("caravan.png");
			sprite.position = new Point(55f, 81f);
			sceneObjectList.add(sprite);
		}
		{
			SpriteSceneObject sprite = new SpriteSceneObject("deck-chair.png");
			sprite.position = new Point(52f, 83f);
			sprite.setRotation(160);
			sceneObjectList.add(sprite);
		}
		{
			SpriteSceneObject sprite = new SpriteSceneObject("deck-chair.png");
			sprite.position = new Point(52f, 85f);
			sprite.setRotation(190);
			sceneObjectList.add(sprite);
		}
		{
			SpriteSceneObject sprite = new SpriteSceneObject("tent.png");
			sprite.position = new Point(33f, 42f);
			sprite.setRotation(-25);
			sceneObjectList.add(sprite);
		}
		{
			SpriteSceneObject sprite = new SpriteSceneObject("bather.png");
			sprite.position = new Point(39f, 43f);
			sprite.setRotation(15);
			sceneObjectList.add(sprite);
		}
	}

    @Override
	public void start()
	{
		setGoal(new TenderParkingGoal(this));
	}
}
