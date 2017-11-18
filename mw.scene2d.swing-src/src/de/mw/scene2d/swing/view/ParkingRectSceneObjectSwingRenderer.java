package de.mw.scene2d.swing.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import de.mw.scene2d.model.ParkingRectSceneObject;
import de.mw.scene2d.model.SceneObject;

public class ParkingRectSceneObjectSwingRenderer extends SceneObjectSwingRenderer
{
    private Color mColor = new Color(100, 100, 255, 127);
    private Color mColorActive = new Color(255, 100, 100, 127);
    private Color mColorLines = Color.BLUE;

	
    protected void drawObject(Graphics2D graphics, SceneObject object)
    {
        ParkingRectSceneObject car = (ParkingRectSceneObject)object;
        
        Shape s = new Rectangle2D.Float(0, 0, car.width, car.height);
        graphics.setColor(car.active ? mColorActive : mColor);
        graphics.fill(s);
        graphics.setColor(mColorLines);
        graphics.draw(s);
    }
	
}
