package de.mw.scene2d.swing.game.games.camping;

import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;

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

public class CampingGame extends Game
{
	public GameGoal tenderParkingGoal = new GameGoal("Tender parked", () -> checkTenderGoalFinished(), () -> onTenderGoalFinished());
	public GameGoal carParkingGoal = new GameGoal("Car parked", () -> checkCarGoalFinished(), () -> onCarGoalFinished());
	
	
	public static void main(String[] arguments) throws Exception
	{
		Game game = new CampingGame();
		game.run();
	}

	private ParkingRectSceneObject tenderParking = new ParkingRectSceneObject(55, 61, 4f, 8f);
	private ParkingRectSceneObject carParking = new ParkingRectSceneObject(62, 17, 6f, 2.5f);
	private CarSceneObject car;
	
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

		ObjectConfig carConfig = IOUtilities.readObjectConfig("CarConfig-001.xml");
		car = new CarSceneObject(scene, carConfig);
		car.setPosition(20f, 17.5f);
		scenePanel.mCar = car;
		sceneObjectList.add(car);
		car.setDamageListener(scenePanel);

		ObjectConfig trailerConfig = IOUtilities.readObjectConfig("TrailerConfig-001.xml");
		TrailerSceneObject trailer = new TrailerSceneObject(trailerConfig);
		trailer.setPosition(16f, 17.5f);
		sceneObjectList.add(trailer);
		car.childList.add(trailer);

		tenderParking.addVehicle(trailer);
		sceneObjectList.add(tenderParking);
		
		carParking.addVehicle(car);
		
		SignSceneObject sign = new SignSceneObject("Hier her ...");
		sign.position = new Point(50, 60);
		sign.active = true;
		sign.childList.add(car);
		sceneObjectList.add(sign);

		{
			SpriteSceneObject sprite1 = new SpriteSceneObject("taxi-2.png");
			sprite1.position = new Point(30f, 18.5f);
			sprite1.setRotation(90);
			sceneObjectList.add(sprite1);
		}
		{
			SpriteSceneObject sprite = new SpriteSceneObject("taxi-2.png");
			sprite.position = new Point(40f, 18.5f);
			sprite.setRotation(90);
			sceneObjectList.add(sprite);
		}
		{
			SpriteSceneObject sprite = new SpriteSceneObject("taxi-2.png");
			sprite.position = new Point(45f, 18.5f);
			sprite.setRotation(90);
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
	}
	
    public void update(float time, float td)
    {
    	this.tenderParkingGoal.isAccomplished();
    	this.carParkingGoal.isAccomplished();
    }

    @Override
	public void start()
	{
		JOptionPane.showMessageDialog(scenePanel, "Stell deinen Wohnwagen auf den Platz dort drüben.");
	}

    public boolean checkTenderGoalFinished()
    {
    	return tenderParking.active && car.childList.size() == 0;
    }
    
    public void onTenderGoalFinished()
    {
    	scenePanel.mScene.getSceneObjectList().remove(tenderParking);
    	scenePanel.mScene.getSceneObjectList().add(carParking);
    }

    public boolean checkCarGoalFinished()
    {
    	return carParking.active && car.velocity == 0;
    }

    public void onCarGoalFinished()
    {
		JOptionPane.showMessageDialog(scenePanel, "Erledigt!");
    }
    
	
}
