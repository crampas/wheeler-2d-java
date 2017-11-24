package de.mw.scene2d.swing.editor.ground;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.mw.scene2d.model.GroundTile;
import de.mw.scene2d.model.TileSet;
import de.mw.scene2d.swing.game.SwingGround;

public class TileSelectorPanel extends JPanel implements TileSelectionSource
{
    private SwingGround mGround;
    private TileSet mTileSet;
    private JList<GroundTile> mListControl = new JList<GroundTile>();
    
    private Icon[] mTileIconList;
    
    public TileSelectorPanel(SwingGround ground, int tileSetId)
    {
        mGround = ground;
        
        setLayout(new BorderLayout());
        
        
        mTileSet = mGround.tileSet.getTileSet(tileSetId);
        
        mTileIconList = new Icon[mTileSet.getTileCount()];
        mListControl.setListData(mTileSet.getTileArray());
        mListControl.setCellRenderer(new GroundTileListCellRenderer());
        mListControl.setDragEnabled(true);
        
        
        JScrollPane scrollPane = new JScrollPane(mListControl);
        
        add(scrollPane);
    }

    @Override
    public int getSelectedTileIndex()
    {
        int index = mListControl.getSelectedIndex();
        int tileIndex = mTileSet.getUniqueTileIndex(index);
        return tileIndex;
    }
    
    private Icon getTileIcon(int index)
    {
        Icon tileIcon = mTileIconList[index];
        if(tileIcon == null)
        {
        	int tileIndex = mTileSet.getUniqueTileIndex(index);
            BufferedImage tileImage = mGround.getTileImage(tileIndex);
            tileIcon = new ImageIcon(tileImage);
            mTileIconList[index] = tileIcon;
        }
        return tileIcon;
    }
    
    class GroundTileListCellRenderer extends DefaultListCellRenderer 
    { 
        public Component getListCellRendererComponent( 
             JList list, 
             Object value, 
             int index, 
             boolean isSelected, 
             boolean cellHasFocus) 
         { 
             Component retValue = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
             setHorizontalAlignment(JLabel.CENTER);
             setText(Integer.toString(index));
             setIcon(getTileIcon(index)); 
             return retValue; 
         } 
     } 
    
    private static Dimension PREFERRED_SIZE = new Dimension(150, 150); 
    
    @Override
    public Dimension getPreferredSize()
    {
        return PREFERRED_SIZE;
    }
}
