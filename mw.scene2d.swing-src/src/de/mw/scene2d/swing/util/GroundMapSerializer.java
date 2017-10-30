package de.mw.scene2d.swing.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.mw.scene2d.model.GroundMap;

public class GroundMapSerializer
{
    public static GroundMap readGroundMap(InputStream stream) throws IOException
    {
        DataInputStream dataStream = new DataInputStream(stream);
        int width = dataStream.readInt();
        int height = dataStream.readInt();
        int[] mapData = new int[width * height];
        for(int index = 0; index < mapData.length; index++)
        {
        	mapData[index] = dataStream.readInt();
        }
        return new GroundMap(width, height, mapData);
    }

    public static void writeGroundMap(OutputStream stream, GroundMap map) throws IOException
    {
        DataOutputStream dataStream = new DataOutputStream(stream);
        dataStream.writeInt(map.getWidth());
        dataStream.writeInt(map.getHeight());
        int[] mapData = map.getMapData();
        for(int index = 0; index < mapData.length; index++)
        {
            dataStream.writeInt(mapData[index]);    
        }
        dataStream.flush();
    }

}
