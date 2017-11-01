package de.mw.scene2d.swing.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.mw.scene2d.model.TileMap;

public class GroundMapSerializer
{
	public static TileMap readGroundMap(File file)
	{
		try
		{
			InputStream stream = new FileInputStream(file);
			TileMap tileMap = readGroundMap(stream);
			stream.close();
			return tileMap;
		} 
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public static TileMap readGroundMap(InputStream stream) throws IOException
	{
		DataInputStream dataStream = new DataInputStream(stream);
		int width = dataStream.readInt();
		int height = dataStream.readInt();
		int[] mapData = new int[width * height];
		for (int index = 0; index < mapData.length; index++)
		{
			mapData[index] = dataStream.readInt();
		}
		return new TileMap(width, height, mapData);
	}

	public static void writeTileMap(File file, TileMap map)
	{
        try
        {
	        OutputStream output = new FileOutputStream(file);
	        GroundMapSerializer.writeGroundMap(output, map);
	        output.close();
        }
        catch(IOException ex)
        {
        	throw new RuntimeException(ex);
        }
	}
	
	public static void writeGroundMap(OutputStream stream, TileMap map) throws IOException
	{
		DataOutputStream dataStream = new DataOutputStream(stream);
		dataStream.writeInt(map.getWidth());
		dataStream.writeInt(map.getHeight());
		int[] mapData = map.getMapData();
		for (int index = 0; index < mapData.length; index++)
		{
			dataStream.writeInt(mapData[index]);
		}
		dataStream.flush();
	}

}
