package de.mw.scene2d.swing.game.games;

import javax.swing.JFrame;

import de.mw.scene2d.model.SceneListener;
import de.mw.scene2d.swing.game.SceneFrame;
import de.mw.scene2d.swing.game.ScenePanel;

public abstract class Game implements SceneListener
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
        
        mainFrame.startSimulation(this);
        
        start();
    }
    
    public abstract void setup();
    
    public abstract void start();
    
    @Override
    public void update(float time, float td)
    {}
}
