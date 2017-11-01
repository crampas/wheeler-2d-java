package de.mw.scene2d.swing.game.games.delivery;

import java.io.File;
import java.util.List;

import de.mw.scene2d.model.CarSceneObject;
import de.mw.scene2d.model.ObjectConfig;
import de.mw.scene2d.model.Scene;
import de.mw.scene2d.model.SceneObject;
import de.mw.scene2d.swing.game.SwingGround;
import de.mw.scene2d.swing.game.ScenePanel;
import de.mw.scene2d.swing.util.IOUtilities;

public class DeliveryGame
{
	public void setup(ScenePanel scenePanel)
	{
		ScenePanel.ViewportVelocityScale = false;
		
		SwingGround ground = SwingGround.createSampleGround(new File("res/games/delivery/ground-40x40.bin"),
                new File("res/street/SampleGround.xml"));
		
		scenePanel.mGround = ground;
		scenePanel.mScene.setGround(ground);
		Scene scene = scenePanel.mScene; 

		List<SceneObject> mSceneObjectList = scenePanel.mScene.getSceneObjectList();
		
		
        ObjectConfig carConfig = IOUtilities.readObjectConfig("CarConfig-001.xml");
        scenePanel.mCar = new CarSceneObject(scene, carConfig);
        scenePanel.mCar.setPosition(20f, 25f);
        scenePanel.mCar.setRotation(-90);
        mSceneObjectList.add(scenePanel.mCar);
        scenePanel.mCar.setDamageListener(scenePanel);
        
        scenePanel.mCarList.add(scenePanel.mCar);
	}
}
