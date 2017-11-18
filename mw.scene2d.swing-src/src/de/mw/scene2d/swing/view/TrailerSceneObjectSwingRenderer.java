package de.mw.scene2d.swing.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import de.mw.scene2d.model.SceneObject;
import de.mw.scene2d.model.TrailerSceneObject;

public class TrailerSceneObjectSwingRenderer extends SceneObjectSwingRenderer
{

    protected void drawObject(Graphics2D graphics, SceneObject object)
    {
        TrailerSceneObject car = (TrailerSceneObject)object;
        
        Shape rect = new Rectangle2D.Float(car.back, car.left, car.front - car.back, car.right - car.left);
        graphics.setColor(Color.WHITE);
        graphics.fill(rect);
        graphics.setColor(Color.DARK_GRAY);
        graphics.draw(rect);

        Line2D axis = new Line2D.Float(car.axis, car.left, car.axis, car.right);
        graphics.draw(axis);
        
        Line2D a1 = new Line2D.Float(car.axis, car.left, 0, 0);
        graphics.draw(a1);
        Line2D a2 = new Line2D.Float(car.axis, car.right, 0, 0);
        graphics.draw(a2);
        
        
//        canvas.drawRect(car.back, car.left, car.front, car.right, DEFAULT_PAINT);
//        canvas.drawLine(0f, car.left, 0f, car.right, DEFAULT_PAINT);
//        canvas.drawLine(car.axisFront, car.left, car.axisFront, car.right, DEFAULT_PAINT);
//        canvas.drawLine(car.front, 0f, car.front + 1f, 0f, DEFAULT_PAINT);

    }

    
}
