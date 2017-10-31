package de.mw.scene2d.swing.editor.ground;

import de.mw.scene2d.model.TileMap;

public class ReplaceRule
{
    TileMap mLhs;
    TileMap mRhs;
    
    public ReplaceRule(TileMap lhs, TileMap rhs)
    {
        mLhs = lhs;
        mRhs = rhs;
    }
}
