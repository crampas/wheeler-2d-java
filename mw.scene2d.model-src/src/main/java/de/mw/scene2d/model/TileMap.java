package de.mw.scene2d.model;

public class TileMap
{
    private int mWidth;
    private int mHeight;
    private int[] mMapData;
    
    
    public int[] getMapData()
	{
		return mMapData;
	}

	public TileMap()
    {}
    
    public TileMap(int width, int height)
    {
        mWidth = width;
        mHeight = height;
        mMapData = new int[width * height];
    }
    
    public TileMap(int width, int height, int... tiles)
    {
        mWidth = width;
        mHeight = height;
        mMapData = tiles;
    }
    
    public boolean match(TileMap rhs, int x, int y)
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

    public void replace(TileMap rhs, int x, int y)
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
            return -1;
        }
        
        int index = y * mWidth + x;
        int tileIndex = mMapData[index];
        return tileIndex;
    }

    public void setTileIndex(int x, int y, int tileIndex)
    {
    	if(x < 0 || x >= mWidth || y < 0 || y >= mHeight)
    		return;
    	
        int index = y * mWidth + x;
        mMapData[index] = tileIndex;
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
