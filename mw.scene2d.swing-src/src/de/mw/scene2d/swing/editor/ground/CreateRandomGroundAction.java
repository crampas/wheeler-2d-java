package de.mw.scene2d.swing.editor.ground;

import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.AbstractAction;

import de.mw.scene2d.model.GroundMap;
import de.mw.scene2d.swing.game.SampleGround;

public class CreateRandomGroundAction extends AbstractAction
{
    private final GroundEditorFrame mApplication;

    public CreateRandomGroundAction(GroundEditorFrame application)
    {
        super("Create Rendom Ground");
        mApplication = application;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
    	SampleGround ground = mApplication.getGround();
    	GroundMap map = ground.getGroundMap();
    	int width = map.getWidth();
    	int height = map.getHeight();
    	

    	// random tiles (every second tile)
    	Random random = new Random();
    	int[] tileSelection = new int[]{3, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15};
    	for(int y = 0; y < height; y++)
    	{
    		for(int x = 0; x < width; x++)
    		{
    			if ((x+y) % 2 == 1)
    				continue;
    			
    			int tileSelectionIndex = random.nextInt(tileSelection.length);
    			int tileIndex = tileSelection[tileSelectionIndex];
    			
    			tileIndex &= (x == 0) ? ~2 : ~0; 
    			tileIndex &= (x == width - 1) ? ~1 : ~0;
    			tileIndex &= (y == 0) ? ~8 : ~0;
    			tileIndex &= (y == height - 1) ? ~4 : ~0;
    			
    			map.setTileIndex(x, y, tileIndex);
    		}
    	}
    	
    	// matching parts
    	for(int y = 0; y < height; y++)
    	{
    		for(int x = 0; x < width; x++)
    		{
    			if ((x+y) % 2 == 0)
    				continue;
    			
    			int left = map.getTileIndex(x - 1, y);
    			int right = map.getTileIndex(x + 1, y);
    			int top = map.getTileIndex(x, y - 1);
    			int bottom = map.getTileIndex(x, y + 1);
    			
    			int leftBorder = (left & 1) != 0 ? 2 : 0;
    			int rightBorder = (right & 2) != 0 ? 1 : 0;
    			int bottomBorder = (bottom & 8) != 0 ? 4 : 0;
    			int topBorder = (top & 4) != 0 ? 8 : 0;
    			
    			int tileIndex = leftBorder | rightBorder | bottomBorder | topBorder;
    			
    			tileIndex &= (x == 0) ? ~2 : ~0; 
    			tileIndex &= (x == width - 1) ? ~1 : ~0;
    			tileIndex &= (y == 0) ? ~8 : ~0;
    			tileIndex &= (y == height - 1) ? ~4 : ~0;
    			
    			map.setTileIndex(x, y, tileIndex);
    		}
    	}
    	
    	ground.calcShortPaths();
    	
    	mApplication.repaint();
    	
    }

}
