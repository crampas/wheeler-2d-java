package de.mw.scene2d.swing.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import de.mw.scene2d.model.TrailerSceneObject;

public class TrailerSceneObjectSwingRenderer extends SceneObjectSwingRenderer<TrailerSceneObject>
{
	public TrailerSceneObjectSwingRenderer(SwingRendererContext context)
    {
    	super(context);
    }
	
	
    protected void drawObject(Graphics2D graphics, TrailerSceneObject vehicle)
    {
        Shape rect = new Rectangle2D.Float(vehicle.back, vehicle.left, vehicle.front - vehicle.back, vehicle.right - vehicle.left);
        graphics.setColor(Color.WHITE);
        graphics.fill(rect);
        graphics.setColor(Color.DARK_GRAY);
        graphics.draw(rect);

        Line2D axis = new Line2D.Float(vehicle.axis, vehicle.left, vehicle.axis, vehicle.right);
        graphics.draw(axis);
        
        Line2D a1 = new Line2D.Float(vehicle.axis, vehicle.left, 0, 0);
        graphics.draw(a1);
        Line2D a2 = new Line2D.Float(vehicle.axis, vehicle.right, 0, 0);
        graphics.draw(a2);
        
        
//        canvas.drawRect(car.back, car.left, car.front, car.right, DEFAULT_PAINT);
//        canvas.drawLine(0f, car.left, 0f, car.right, DEFAULT_PAINT);
//        canvas.drawLine(car.axisFront, car.left, car.axisFront, car.right, DEFAULT_PAINT);
//        canvas.drawLine(car.front, 0f, car.front + 1f, 0f, DEFAULT_PAINT);

    }
}