package de.mw.scene2d.swing.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GroundMap
{
    private int mWidth;
    private int mHeight;
    private int[] mMapData;
    
    
    public GroundMap()
    {}
    
    public GroundMap(int width, int height)
    {
        mWidth = width;
        mHeight = height;
        mMapData = new int[width * height];
    }
    
    public GroundMap(int width, int height, int... tiles)
    {
        mWidth = width;
        mHeight = height;
        mMapData = tiles;
    }
    
    public boolean match(GroundMap rhs, int x, int y)
    {
        for(int mapY = 0; mapY < rhs.mHeight; mapY++)
        {
            for(int mapX = 0; mapX < rhs.mWidth; mapX++)
            {
                int lhsTile = getTileIndex(x + mapX, y + mapY);
                int rhsTile = rhs.getTileIndex(mapX, mapY);
                if(lhsTile != rhsTile)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public void replace(GroundMap rhs, int x, int y)
    {
        for(int mapY = 0; mapY < rhs.mHeight; mapY++)
        {
            for(int mapX = 0; mapX < rhs.mWidth; mapX++)
            {
                int rhsTile = rhs.getTileIndex(mapX, mapY);
                this.setTileIndex(x + mapX, y + mapY, rhsTile);
            }
        }
    }
    
    public int getTileIndex(int x, int y)
    {
        if(x < 0 || x >= mWidth || y < 0 || y >= mHeight)
        {
            return 15;
        }
        
        int index = y * mWidth + x;
        int tileIndex = mMapData[index];
        return tileIndex;
    }

    public void setTileIndex(int x, int y, int tileIndex)
    {
        int index = y * mWidth + x;
        mMapData[index] = tileIndex;
    }
    
    public void write(OutputStream stream) throws IOException
    {
        DataOutputStream dataStream = new DataOutputStream(stream);
        dataStream.writeInt(mWidth);
        dataStream.writeInt(mHeight);
        for(int index = 0; index < mMapData.length; index++)
        {
            dataStream.writeInt(mMapData[index]);    
        }
        dataStream.flush();
    }

    public void read(InputStream stream) throws IOException
    {
        DataInputStream dataStream = new DataInputStream(stream);
        mWidth = dataStream.readInt();
        mHeight = dataStream.readInt();
        mMapData = new int[mWidth * mHeight];
        for(int index = 0; index < mMapData.length; index++)
        {
            mMapData[index] = dataStream.readInt();
        }
    }

    public int getWidth()
    {
        return mWidth;
    }

    public int getHeight()
    {
        return mHeight;
    }
}
