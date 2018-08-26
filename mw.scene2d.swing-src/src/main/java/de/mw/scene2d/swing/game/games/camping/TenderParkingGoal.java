package de.mw.scene2d.swing.game.games.camping;

import javax.swing.JOptionPane;

import de.mw.scene2d.model.SignSceneObject;
import de.mw.scene2d.swing.game.games.GameGoal;

public class TenderParkingGoal extends GameGoal<CampingGame>
{
	private SignSceneObject sign;
	
	public TenderParkingGoal(CampingGame game)
	{
		super(game, "Tender Parking");
	}

    public void onStart()
    {
    	game.scenePanel.mScene.getSceneObjectList().add(game.tenderParking);
		sign = new SignSceneObject("Hier her ...");
		sign.position = game.tenderParking.position;
		sign.childList.add(game.car);
		game.scenePanel.mScene.getSceneObjectList().add(sign);
    	
    	JOptionPane.showMessageDialog(game.scenePanel, "Stell deinen Wohnwagen auf den Platz dort drüben.");
    }
	
    public boolean checkCondition()
    {
    	return game.tenderParking.active && game.car.childList.size() == 0;
    }
    
    public GameGoal<CampingGame> onFinished()
    {
    	game.scenePanel.mScene.getSceneObjectList().remove(game.tenderParking);
    	game.scenePanel.mScene.getSceneObjectList().remove(sign);
    	return new CarParkingGoal(game);
    }
}
