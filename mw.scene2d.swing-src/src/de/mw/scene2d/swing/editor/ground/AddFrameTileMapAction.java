package de.mw.scene2d.swing.editor.ground;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import de.mw.scene2d.model.TileMap;
import de.mw.scene2d.swing.game.SampleGround;

public class AddFrameTileMapAction extends AbstractAction
{
    private final GroundEditorFrame mApplication;
    
    public AddFrameTileMapAction(GroundEditorFrame application)
    {
        super("Add Frame");
        mApplication = application;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
    	SampleGround ground = mApplication.getGround();
    	TileMap newMap = addFrame(ground.getGroundMap());
    	ground.setGroundMap(newMap);
    	ground.init();
    	mApplication.repaint();
    }

	private TileMap addFrame(TileMap map)
	{
    	int width = map.getWidth();
    	int height = map.getHeight();
    	
    	int newWidth = width + 2;
    	int newHeight = height + 2;
    	TileMap newMap = new TileMap(newWidth, newHeight);
    	newMap.replace(map, 1, 1);
    	
		return newMap;
	}

}
