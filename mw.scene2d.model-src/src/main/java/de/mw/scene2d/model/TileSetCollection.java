package de.mw.scene2d.model;

import java.util.HashMap;
import java.util.Map;

public class TileSetCollection
{
	private Map<Integer, TileSet> mTileSets = new HashMap<Integer, TileSet>();
	
	public void addTileSet(TileSet tileSet)
	{
		mTileSets.put(tileSet.id, tileSet);
	}
	
	public TileSet getTileSet(int tileSetId)
	{
		return mTileSets.get(tileSetId);
	}
	
	public GroundTile getTile(int index)
	{
		int tileSetId = (index & 0xbfff0000) >> 16;
		int tileIndex = index & 0xffff;
		TileSet tileSet = mTileSets.get(tileSetId);
		if (tileSet == null)
			throw new IllegalArgumentException(String.format("Unknown Tileset %d", tileSetId));
		return tileSet.getTile(tileIndex);
	}
	
	public TileSet getTileTileSet(int tileIndex)
	{
		int tileSetId = (tileIndex & 0xbfff0000) >> 16;
		return mTileSets.get(tileSetId);
	}
}
