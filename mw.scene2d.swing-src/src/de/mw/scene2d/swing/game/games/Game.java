package de.mw.scene2d.swing.game.games;

import javax.swing.JFrame;

import de.mw.scene2d.swing.game.SceneFrame;
import de.mw.scene2d.swing.game.ScenePanel;

public abstract class Game
{
	public ScenePanel scenePanel;
	
	
    public void run()
    {
        SceneFrame mainFrame = new SceneFrame();
        scenePanel = mainFrame.getScenePanel(); 
        setup();
        
        mainFrame.setSize(1200, 800);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        
        mainFrame.startSimulation();
        
        start();
    }
    
    public abstract void setup();
    
    public abstract void start();
}
