package de.mw.scene2d.swing.editor.ground;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.swing.AbstractAction;

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
        SwingGround ground = mApplication.getGround();
        File file = mApplication.getTileMapFile();
        
        backupFile(file);
        GroundMapSerializer.writeTileMap(file, ground.getGroundMap());
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
