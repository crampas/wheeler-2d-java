package de.mw.scene2d.swing.editor.ground;

import de.mw.scene2d.model.GroundMap;

public class ReplaceRule
{
    GroundMap mLhs;
    GroundMap mRhs;
    
    public ReplaceRule(GroundMap lhs, GroundMap rhs)
    {
        mLhs = lhs;
        mRhs = rhs;
    }
}
