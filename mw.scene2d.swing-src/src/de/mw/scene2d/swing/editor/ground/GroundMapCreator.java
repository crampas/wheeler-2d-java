package de.mw.scene2d.swing.editor.ground;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import de.mw.scene2d.model.GroundMap;
import de.mw.scene2d.swing.util.GroundMapSerializer;

public class GroundMapCreator
{

    public static void main(String[] args) throws Exception
    {
        Properties settings = new Properties();
        String targetFile = parseArguments(settings, args);

        int width = Integer.parseInt(settings.getProperty("width"));
        int height = Integer.parseInt(settings.getProperty("height"));
        
        GroundMap groundMap = new GroundMap(width, height);
        OutputStream stream = new FileOutputStream(targetFile);
        GroundMapSerializer.writeGroundMap(stream, groundMap);
        stream.close();
    }
    
    private static String parseArguments(Properties properties, String[] args)
    {
        String target = null;
        for (String argument : args)
        {
            int assignmentIndex = argument.indexOf('=');
            if(assignmentIndex >= 0)
            {
                String key = argument.substring(0, assignmentIndex);
                String value = argument.substring(assignmentIndex + 1);
                properties.setProperty(key, value);
            }
            else
            {
                target = argument;
            }
        }
        return target;
    }

}
