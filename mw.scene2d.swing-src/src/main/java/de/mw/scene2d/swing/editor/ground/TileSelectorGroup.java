package de.mw.scene2d.swing.editor.ground;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class TileSelectorGroup extends JPanel implements TileSelectionSource
{
    public TileSelectorContainer mActiveContainer; 
    
    public TileSelectorGroup()
    {
        setLayout(new Layout());
    }

    @Override
    public int getSelectedTileIndex()
    {
        return mActiveContainer.getSelectedTileIndex();
    }
    
    public void addTileSelector(String name, TileSelectorPanel selector)
    {
        TileSelectorContainer container = new TileSelectorContainer(name, selector);
        add(container);
        if(mActiveContainer == null)
        {
            activate(container);
        }
    }
    
    private void activate(TileSelectorContainer container)
    {
        if(mActiveContainer != null)
        {
            mActiveContainer.setActive(false); 
        }
        mActiveContainer = container;
        mActiveContainer.setActive(true);
    }
    
    class TileSelectorContainer extends JPanel implements TileSelectionSource
    {
        private JButton mHeaderButton;
        private TileSelectorPanel mTileSelectorPanel;
        
        public TileSelectorContainer(String name, TileSelectorPanel selector)
        {
            setLayout(new BorderLayout());
            mHeaderButton = new JButton(name);
            mHeaderButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    onActivateContainer();
                }
            });
            add(mHeaderButton, BorderLayout.NORTH);
            mTileSelectorPanel = selector;
            add(mTileSelectorPanel, BorderLayout.CENTER);
            mTileSelectorPanel.setVisible(false);
        }
        
        private void onActivateContainer()
        {
            activate(this);
        }
        
        void setActive(boolean activate)
        {
            mTileSelectorPanel.setVisible(activate);
        }
        
        boolean isActive()
        {
            return mTileSelectorPanel.isVisible();
        }

        @Override
        public int getSelectedTileIndex()
        {
            return mTileSelectorPanel.getSelectedTileIndex();
        }
    }
    
    static class Layout implements LayoutManager
    {
        private static Dimension SIZE = new Dimension(150, 150);
        
        @Override
        public void addLayoutComponent(String name, Component comp)
        {
        }

        @Override
        public void removeLayoutComponent(Component comp)
        {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent)
        {
            return SIZE;
        }

        @Override
        public Dimension minimumLayoutSize(Container parent)
        {
            return SIZE;
        }

        @Override
        public void layoutContainer(Container parent)
        {
            Dimension size = parent.getSize();
            
            Component[] componentList = parent.getComponents();

            int staticHeight = 0;
            for (Component component : componentList)
            {
                TileSelectorContainer item = (TileSelectorContainer)component; 
                if(!item.isActive())
                {
                    Dimension itemSize = item.getPreferredSize();
                    staticHeight += itemSize.height;
                }
            }
            
            int layoutY = 0;
            for (Component component : componentList)
            {
                TileSelectorContainer item = (TileSelectorContainer)component; 
                if(!item.isActive())
                {
                    Dimension itemSize = item.getPreferredSize();
                    item.setBounds(0, layoutY, size.width, itemSize.height);
                    layoutY += itemSize.height;
                }
                else
                {
                    int itemHeight = size.height - staticHeight;
                    item.setBounds(0, layoutY, size.width, itemHeight);
                    layoutY += itemHeight;
                }
            }
        }
    }
}
