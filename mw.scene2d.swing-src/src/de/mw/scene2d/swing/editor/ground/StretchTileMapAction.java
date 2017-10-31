package de.mw.scene2d.swing.editor.ground;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import de.mw.scene2d.model.TileMap;
import de.mw.scene2d.swing.game.SampleGround;

public class StretchTileMapAction extends AbstractAction
{
    private final GroundEditorFrame mApplication;
    private int mFactor;
    
    public StretchTileMapAction(GroundEditorFrame application, int factor)
    {
        super(String.format("Stretch x %d", factor));
        mApplication = application;
        mFactor = factor;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
    	SampleGround ground = mApplication.getGround();
    	TileMap newMap = stretchTileMap(ground.getGroundMap(), mFactor);
    	ground.setGroundMap(newMap);
    	ground.init();
    	mApplication.repaint();
    }

	private TileMap stretchTileMap(TileMap map, int factor)
	{
    	int width = map.getWidth();
    	int height = map.getHeight();
    	
    	int newWidth = (width - 1) * factor + 1;
    	int newHeight = (height - 1) * factor+ 1;
    	TileMap newMap = new TileMap(newWidth, newHeight);
    	
    	for(int y = 0; y < newHeight; y++)
    	{
    		for(int x = 0; x < newWidth; x++)
    		{
				int oldTileIndex = map.getTileIndex(x / factor, y / factor);
    			if ((x % factor) == 0 && (y % factor) == 0)
    			{
    				newMap.setTileIndex(x, y, oldTileIndex);
    			}
    			else if ((x % factor) == 0)
    			{
    				boolean open = (oldTileIndex & 4) != 0;
    				newMap.setTileIndex(x, y, open ? 12 : 0);
    			}
    			else if ((y % factor) == 0)
    			{
    				boolean open = (oldTileIndex & 1) != 0;
    				newMap.setTileIndex(x, y, open ? 3 : 0);
    			}
    			else
    			{
    				newMap.setTileIndex(x, y, 0);
    			}
    		}
    	}
    	
		return newMap;
	}

}
