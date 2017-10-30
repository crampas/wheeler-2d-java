package de.mw.scene2d.swing.editor.ground;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import de.mw.scene2d.swing.game.SampleGround;

public class GroundEditorFrame extends JFrame
{
    private SampleGround mGround;
    
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
	    mGround = SampleGround.createSampleGround(new File("res/city/road-tileset.xml"),
	    		new File("res/city/map-16x16.bin"));
        
        this.setTitle(mGround.getBasleLocation().toString() + " / " + mGround.getGroundMapFile().toString());
        
        Container contentPane = getContentPane();
        
        contentPane.setLayout(new BorderLayout());

        GroundPanel groundView = new GroundPanel(mGround);
        contentPane.add(groundView, BorderLayout.CENTER);
        
        
        TileSelectorGroup selectorGroup = new TileSelectorGroup();
        contentPane.add(selectorGroup, BorderLayout.EAST);
        
        
        TileSelectorPanel tileSelector1 = new TileSelectorPanel(mGround);
        groundView.setTileSelectionSource(tileSelector1);
        selectorGroup.addTileSelector("Straﬂen", tileSelector1);
        
        TileSelectorPanel tileSelector2 = new TileSelectorPanel(mGround);
        selectorGroup.addTileSelector("Geb‰ude", tileSelector2);

        TileSelectorPanel tileSelector3 = new TileSelectorPanel(mGround);
        selectorGroup.addTileSelector("Landschaft", tileSelector3);

        
        JMenuBar mainMenu = new JMenuBar();
        setJMenuBar(mainMenu);
        
        JMenu fileMenu = new JMenu("Datei");
        fileMenu.add(new JMenuItem(new SaveAction(this)));
        mainMenu.add(fileMenu);
        
        JMenu operationsMenu = new JMenu("Operations");
        operationsMenu.add(new JMenuItem(new CreateRandomGroundAction(this)));
        mainMenu.add(operationsMenu);
    }


    public SampleGround getGround()
    {
        return mGround;
    }
    
}
