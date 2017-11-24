package de.mw.scene2d.swing.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import de.mw.scene2d.model.SignSceneObject;

/**
 * Renderer für Objekte des Typs TempSceneObject.
 */
public class SignSceneObjectRenderer extends SceneObjectSwingRenderer<SignSceneObject>
{
    private Font mIdFont = new Font("Arial", 0, 2);
    private Color mColor = Color.decode("#3787f4"); 
    private Shape mObjectShape = new Ellipse2D.Float(-0.2f, -0.2f, 0.4f, 0.4f);
    
    public SignSceneObjectRenderer(SwingRendererContext context)
    {
    	super(context);
    }
    
    protected void drawObject(Graphics2D graphics, SignSceneObject object)
    {
        if(!object.active)
            return;

        graphics.setColor(mColor);
        graphics.fill(mObjectShape);
        
//        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.rotate(-mContext.getViewportRotation() / 180.0 * Math.PI);

        graphics.setFont(mIdFont);
        FontMetrics metrics = graphics.getFontMetrics();
        Rectangle2D textBounds = metrics.getStringBounds(object.text, graphics);
        float textWidth = (float)textBounds.getWidth();
        float textWidth2 = textWidth / 2f; 
        
        graphics.setColor(mColor);
        RoundRectangle2D.Float rect = new RoundRectangle2D.Float(-textWidth2 - 1, -2, textWidth + 2, 3, 1, 1);
        graphics.fill(rect);
        graphics.setColor(Color.WHITE);
        graphics.setStroke(new BasicStroke((float) 0.2));
        graphics.draw(rect);
        
        graphics.drawString(object.text, -textWidth / 2f, 0f);
//        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }
}
