package de.mw.scene2d.swing.editor.ground;

class TileTool implements GroundEditorTool
{
	private GroundEditorPanel mGroundPanel;
	
	public TileTool(GroundEditorPanel parent)
	{
		mGroundPanel = parent;
	}
	
	public void processAddTile(int x, int y)
	{
		processSetTile(x, y);
	}
	
	public void processSetTile(int x, int y)
	{
		int tileIndex = mGroundPanel.getSelectedTileIndex();
        if(tileIndex < 0)
            return;
		mGroundPanel.changePatch(x, y, tileIndex);
	}
}