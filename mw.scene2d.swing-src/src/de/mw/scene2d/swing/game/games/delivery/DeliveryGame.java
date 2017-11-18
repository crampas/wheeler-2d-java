package de.mw.scene2d.swing.game.games.delivery;

import java.io.File;
import java.util.List;

import de.mw.scene2d.model.CarSceneObject;
import de.mw.scene2d.model.ObjectConfig;
import de.mw.scene2d.model.ParkingRectSceneObject;
import de.mw.scene2d.model.Scene;
import de.mw.scene2d.model.SceneObject;
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
				new File("res/city/road-tileset.xml"), new File("res/city/roof-tileset.xml"));
		
		scenePanel.mGround = ground;
		scenePanel.mScene.setGround(ground);
		Scene scene = scenePanel.mScene; 

		List<SceneObject> mSceneObjectList = scenePanel.mScene.getSceneObjectList();
		
		
        ObjectConfig carConfig = IOUtilities.readObjectConfig("CarConfig-001.xml");
        scenePanel.mCar = new CarSceneObject(scene, carConfig);
        scenePanel.mCar.setPosition(15f, 25f);
        scenePanel.mCar.setRotation(-90);
        mSceneObjectList.add(scenePanel.mCar);
        scenePanel.mCar.setDamageListener(scenePanel);
        scenePanel.mCarList.add(scenePanel.mCar);
        
		ObjectConfig trailerConfig = IOUtilities.readObjectConfig("TrailerConfig-001.xml");
		TrailerSceneObject trailer = new TrailerSceneObject(trailerConfig);
		trailer.setPosition(15f, 29f);
		trailer.setRotation(-90);
		mSceneObjectList.add(trailer);
		scenePanel.mCar.childList.add(trailer);
        
        
		ParkingRectSceneObject parking = new ParkingRectSceneObject(45, 50, 4.5f, 8f);
		parking.addVehicle(trailer);
		mSceneObjectList.add(parking);

        
	}
}
