package de.mw.scene2d.swing.game.games;

import javax.swing.JFrame;

import de.mw.scene2d.model.SceneListener;
import de.mw.scene2d.swing.game.SceneFrame;
import de.mw.scene2d.swing.game.ScenePanel;
import de.mw.scene2d.swing.game.games.camping.GameGoal;

public abstract class Game implements SceneListener
{
	public ScenePanel scenePanel;
	private GameGoal<?> currentGoal; 
	
    public void run()
    {
        SceneFrame mainFrame = new SceneFrame();
        scenePanel = mainFrame.getScenePanel(); 
        setup();
        
        mainFrame.setSize(1200, 800);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        
        mainFrame.startSimulation(this);
        
        start();
    }
    
    public abstract void setup();
    
    public abstract void start();
    
    public void setGoal(GameGoal<?> goal)
    {
    	currentGoal = goal;
    }
    
    @Override
    public void update(float time, float td)
    {
    	if (currentGoal == null)
    		return;
    	
    	if (!currentGoal.started)
    		currentGoal.onStart();
    	currentGoal.started = true;
    	currentGoal = currentGoal.update();
    }
}
