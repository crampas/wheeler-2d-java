package de.mw.scene2d.swing.game.games.camping;

import javax.swing.JOptionPane;

public class CarParkingGoal extends GameGoal<CampingGame>
{
	public CarParkingGoal(CampingGame game)
	{
		super(game, "Tender Parking");
	}

    public void onStart()
    {
    	game.scenePanel.mScene.getSceneObjectList().add(game.carParking);
    	JOptionPane.showMessageDialog(game.scenePanel, "Deinen Wagen kannst du bei der Rezeption parken.");
    }
	
    public boolean checkCondition()
    {
    	return game.carParking.active && game.car.velocity == 0;
    }
    
    public GameGoal<CampingGame> onFinished()
    {
    	game.scenePanel.mScene.getSceneObjectList().remove(game.carParking);
    	JOptionPane.showMessageDialog(game.scenePanel, "Erledigt - gut!");
    	return null;
    }
}
