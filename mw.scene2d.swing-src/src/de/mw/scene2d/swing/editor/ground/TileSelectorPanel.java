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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.mw.scene2d.swing.game.SampleGround;

public class TileSelectorPanel extends JPanel implements TileSelectionSource
{
    private SampleGround mGround;
    private JList mListControl = new JList();
    
    private Icon[] mTileIconList;
    
    public TileSelectorPanel(SampleGround ground)
    {
        mGround = ground;
        
        setLayout(new BorderLayout());
        
        
        
        mTileIconList = new Icon[mGround.tile.length];
        mListControl.setListData(mGround.tile);
        mListControl.setCellRenderer(new GroundTileListCellRenderer());
        mListControl.setDragEnabled(true);
        
        
        JScrollPane scrollPane = new JScrollPane(mListControl);
        
        add(scrollPane);
    }

    @Override
    public int getSelectedTileIndex()
    {
        return mListControl.getSelectedIndex();
    }
    
    private Icon getTileIcon(int index)
    {
        Icon tileIcon = mTileIconList[index];
        if(tileIcon == null)
        {
            BufferedImage tileImage = mGround.getTileImage(index);
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
    
    protected void paintComponent(java.awt.Graphics g) 
    {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        
//        for(int tileIndex = 0; tileIndex < 16; tileIndex++)
//        {
//            int y = tileIndex * 110 + 10;
//            mGround.drawGroundPatch(g2, 10, y, 110, y + 100, tileIndex);
//        }
    }
    
    private static Dimension PREFERRED_SIZE = new Dimension(150, 150); 
    
    @Override
    public Dimension getPreferredSize()
    {
        return PREFERRED_SIZE;
    }
}
