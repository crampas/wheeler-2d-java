package de.mw.scene2d.swing.game.games.camping;

import javax.swing.JOptionPane;

import de.mw.scene2d.model.ParkingRectSceneObject;
import de.mw.scene2d.swing.game.games.GameGoal;

public class PickUpGoal extends GameGoal<CampingGame>
{
	public ParkingRectSceneObject pickUpParking = new ParkingRectSceneObject(140f, 190.5f, 10f, 3.0f);
	
	public PickUpGoal(CampingGame game)
	{
		super(game, "Tender Parking");
	}

    public void onStart()
    {
    	game.scenePanel.mScene.getSceneObjectList().add(pickUpParking);
    	pickUpParking.addVehicle(game.car);
    	
    	JOptionPane.showMessageDialog(game.scenePanel, "Hol doch mal die Semmeln für die Jungs. Fahr dafür zum Bäcker. Nimm den südlichen Ausgang und dann die zweite links.");
    }
	
    public boolean checkCondition()
    {
    	return pickUpParking.active && game.car.velocity == 0;
    }
    
    public GameGoal<CampingGame> onFinished()
    {
    	game.scenePanel.mScene.getSceneObjectList().remove(pickUpParking);
    	JOptionPane.showMessageDialog(game.scenePanel, "Erledigt - gut!");
    	return null;
    }
}
