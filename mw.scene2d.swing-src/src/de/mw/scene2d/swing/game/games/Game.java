package de.mw.scene2d.swing.game.games;

import javax.swing.JFrame;

import de.mw.scene2d.swing.game.SceneFrame;
import de.mw.scene2d.swing.game.ScenePanel;

public abstract class Game
{

    public void run()
    {
        SceneFrame mainFrame = new SceneFrame();
        this.setup(mainFrame.getScenePanel());
        
        mainFrame.setSize(1200, 800);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        
        mainFrame.startSimulation();
    }
    
    public abstract void setup(ScenePanel scenePanel);
}
