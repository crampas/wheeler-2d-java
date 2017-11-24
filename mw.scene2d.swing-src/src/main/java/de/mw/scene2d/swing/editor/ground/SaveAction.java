package de.mw.scene2d.swing.editor.ground;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import de.mw.scene2d.swing.game.SwingGround;
import de.mw.scene2d.swing.util.GroundMapSerializer;

public class SaveAction extends AbstractAction
{
    private final GroundEditorFrame mApplication;

    public SaveAction(GroundEditorFrame application)
    {
        super("Speichern");
        mApplication = application;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
    	JFileChooser fileChooser = new JFileChooser();
    	File file = mApplication.getTileMapFile();
    	fileChooser.setCurrentDirectory(file.getParentFile());
    	fileChooser.setSelectedFile(file);
    	if (fileChooser.showSaveDialog(mApplication) != JFileChooser.APPROVE_OPTION)
    		return;

		File newFile = fileChooser.getSelectedFile();

        backupFile(newFile);
        SwingGround ground = mApplication.getGround();
        GroundMapSerializer.writeTileMap(newFile, ground.getGroundMap());
    }

	private void backupFile(File file)
	{
		if (file.exists())
        {
            String bakFilename = file.toString() + ".bak";
            File bakFile = new File(bakFilename);
            bakFile.delete();
            file.renameTo(bakFile);
        }
	}
}
