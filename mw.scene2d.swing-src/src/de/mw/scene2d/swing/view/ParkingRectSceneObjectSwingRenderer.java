package de.mw.scene2d.swing.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import de.mw.scene2d.model.ParkingRectSceneObject;

public class ParkingRectSceneObjectSwingRenderer extends SceneObjectSwingRenderer<ParkingRectSceneObject>
{
    private Color mColor = new Color(100, 100, 255, 127);
    private Color mColorActive = new Color(255, 100, 100, 127);
    private Color mColorLines = Color.BLUE;

    
    public ParkingRectSceneObjectSwingRenderer(SwingRendererContext context)
    {
    	super(context);
    }
	
    protected void drawObject(Graphics2D graphics, ParkingRectSceneObject object)
    {
        Shape s = new Rectangle2D.Float(0, 0, object.width, object.height);
        graphics.setColor(object.active ? mColorActive : mColor);
        graphics.fill(s);
        graphics.setColor(mColorLines);
        graphics.draw(s);
    }
	
}
