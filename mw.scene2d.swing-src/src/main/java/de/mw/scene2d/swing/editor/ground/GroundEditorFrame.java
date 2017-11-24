package de.mw.scene2d.swing.editor.ground;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

import de.mw.scene2d.swing.game.SwingGround;

public class GroundEditorFrame extends JFrame
{
    private SwingGround mGround;
    private File mTileMapFile;
    
    public static void main(String[] arguments) throws Exception
    {
        GroundEditorFrame mainFrame = new GroundEditorFrame();

        mainFrame.setSize(1200, 800);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    
    public GroundEditorFrame()
    {
//        mGround = SampleGround.createSampleGround(new File("res/street/SampleGround.xml"),
//                new File("res/street/Ground-100x100.bin"));
//        mGround = SampleGround.createSampleGround(new File("res/street/SampleGround.xml"),
//                new File("res/street/Ground-10x10.bin"));
    	
    	mTileMapFile = new File("res/city/map-16x16.bin");
    	mTileMapFile = new File("res/city/map-48x48.bin");
	    mGround = SwingGround.createSampleGround(mTileMapFile, 
	    		new File("res/city/road-tileset.xml"), new File("res/city/roof-tileset.xml"), new File("res/city/floor-tileset.xml"),
	    		new File("res/city/hedge-tileset.xml"));
        
        this.setTitle(mGround.getBasleLocation().toString() + " / " + mTileMapFile.toString());
        
        initContent();
    }

    private void initContent()
    {
        Container contentPane = getContentPane();
        
        contentPane.setLayout(new BorderLayout());

        GroundEditorPanel groundView = new GroundEditorPanel(mGround);
        contentPane.add(groundView, BorderLayout.CENTER);
        
        TileSelectorGroup selectorGroup = new TileSelectorGroup();
        contentPane.add(selectorGroup, BorderLayout.EAST);
        
        TileSelectorPanel tileSelector1 = new TileSelectorPanel(mGround, 0);
        selectorGroup.addTileSelector("Straﬂen", tileSelector1);
        
        TileSelectorPanel tileSelector2 = new TileSelectorPanel(mGround, 1000);
        selectorGroup.addTileSelector("Geb‰ude", tileSelector2);

        TileSelectorPanel tileSelector3 = new TileSelectorPanel(mGround, 2000);
        selectorGroup.addTileSelector("Boden", tileSelector3);

        TileSelectorPanel tileSelector4 = new TileSelectorPanel(mGround, 3000);
        selectorGroup.addTileSelector("Hecke", tileSelector4);
        
        groundView.setTileSelectionSource(selectorGroup);
        
        
        JMenuBar mainMenu = new JMenuBar();
        setJMenuBar(mainMenu);
        
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem(new SaveAction(this)));
        mainMenu.add(fileMenu);
        
        JMenu operationsMenu = new JMenu("Operations");
        operationsMenu.add(new JMenuItem(new CreateRandomTileMapAction(this)));
        JMenu stretchSubmenu = new JMenu("Stretch");
        stretchSubmenu.add(new JMenuItem(new StretchTileMapAction(this, 2)));
        stretchSubmenu.add(new JMenuItem(new StretchTileMapAction(this, 3)));
        operationsMenu.add(stretchSubmenu);
        operationsMenu.add(new JMenuItem(new AddFrameTileMapAction(this)));
        mainMenu.add(operationsMenu);
        
        JToolBar toolBar = new JToolBar("Tools", JToolBar.HORIZONTAL);
        toolBar.setFloatable(false);
        JButton button = new JButton("Hallo");
        toolBar.add(button);
        contentPane.add(toolBar, BorderLayout.PAGE_START);
    }

    public SwingGround getGround()
    {
        return mGround;
    }


	public File getTileMapFile()
	{
		return mTileMapFile;
	}


	public void setTileMapFile(File tileMapFile)
	{
		mTileMapFile = tileMapFile;
	}
}
