package de.mw.scene2d.model;

public class TileSet
{
	public int id;
	public String name;
	public int tileSize = 100;
	public GroundTile[] tile;
	
	public GroundTile getTile(int index)
	{
		return tile[index];
	}
	
	public int getTileCount()
	{
		return tile.length;
	}

	public GroundTile[] getTileArray()
	{
		return tile;
	}
	
	public int getUniqueTileIndex(int index)
	{
		return (id << 16) + index;
	}
	
}
