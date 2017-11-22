package de.mw.scene2d.swing.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import de.mw.scene2d.model.SceneObject;
import de.mw.scene2d.model.TempSceneObject;

/**
 * Renderer für Objekte des Typs TempSceneObject.
 */
public class TempSceneObjectRenderer extends SceneObjectSwingRenderer
{
    private Font mIdFont = new Font("Arial", 0, 2);
    private Color mColor = new Color(255, 200, 100); 
    private Shape mObjectShape = new Ellipse2D.Float(-1f, -1f, 2f, 2f);
    private Shape mObjectLook = new Line2D.Float(0f, 0f, 2f, 0f);
    
    public TempSceneObjectRenderer(SwingRendererContext context)
    {
    	super(context);
    }
    
    @Override
    public void renderObject(SwingRendererContext context, Graphics2D graphics, SceneObject object)
    {
        TempSceneObject temp = (TempSceneObject)object;

//        // Pfad der Navigationspunkte
//        graphics.setColor(Color.RED);
//        for (Edge edge : temp.edgeList)
//        {
//            Line2D navLine = new Line2D.Float(edge.p0.x, edge.p0.y, edge.p1.x, edge.p1.y);
//            graphics.draw(navLine);
//        }
        // nächster Navigationspunkt
        graphics.setColor(Color.ORANGE);
        if(temp.nextNavPoint != null)
        {
            Rectangle2D nextNaxPoint = new Rectangle2D.Float(temp.nextNavPoint.x - 0.5f, temp.nextNavPoint.y - 0.5f, 1.0f, 1.0f);
            graphics.draw(nextNaxPoint);
        }

        // Ziel Navigationspunkt
        graphics.setColor(Color.RED);
        if(temp.targetNavPoint != null)
        {
            Rectangle2D targetNaxPoint = new Rectangle2D.Float(temp.targetNavPoint.x - 0.5f, temp.targetNavPoint.y - 0.5f, 1.0f, 1.0f);
            graphics.draw(targetNaxPoint);
        }

        
        // Objekt selbst
        super.renderObject(context, graphics, object);
    }
    
    protected void drawObject(Graphics2D graphics, SceneObject object)
    {
        graphics.setColor(mColor);
        graphics.fill(mObjectShape);
        graphics.draw(mObjectLook);
        
        graphics.setColor(Color.RED);
//        graphics.setFont(mIdFont);
//        graphics.drawString(Integer.toString(((TempSceneObject)object).id), 0, 0);
    }
}
