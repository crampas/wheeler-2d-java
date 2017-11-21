package de.mw.scene2d.swing.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import de.mw.scene2d.model.SceneObject;
import de.mw.scene2d.model.SceneObjectRenderer;

public class SceneObjectSwingRenderer<T extends SceneObject> extends SceneObjectRenderer
{
    private static Stroke LINE_STROKE = new BasicStroke(0.01f);
    private static Color LINE_COLOR = Color.BLACK;
    private static Color ACTIVE_FILL_COLOR = Color.RED;
    
    public SceneObjectSwingRenderer()
    {
    }
    
    @Override
    public void renderObject(SceneObject object)
    {
        // TODO Auto-generated method stub
        
    }
    
    public void renderObject(SwingRendererContext context, Graphics2D graphics, T object)
    {
        graphics.setColor(LINE_COLOR);
        graphics.setStroke(LINE_STROKE);

        AffineTransform oldTransform = graphics.getTransform();
        
        AffineTransform m = graphics.getTransform();
        m.translate(object.position.x , object.position.y);
        m.rotate(object.getRotation() / 180.0 * Math.PI);
        
        graphics.setTransform(m);
        drawObject(graphics, object);
        graphics.setTransform(oldTransform);
        
//        for (SceneObject childObject : object.childList)
//        {
//            context.renderObject(graphics, childObject);
//        }
        
    }
    
    protected void drawObject(Graphics2D graphics, T object)
    {
        Shape s = new Rectangle2D.Float(-0.1f, -0.1f, 0.2f, 0.2f);
        graphics.draw(s);
        if(object.active)
        {
            graphics.setColor(ACTIVE_FILL_COLOR);
            graphics.fillRect(-1, -1, 2, 2);
        }
        else
        {
            graphics.drawRect(-1, -1, 2, 2);
        }
    }

}
