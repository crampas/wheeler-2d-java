package de.mw.scene2d.swing.view;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import de.mw.scene2d.model.SpriteSceneObject;

public class SpriteSceneObjectSwingRenderer extends SceneObjectSwingRenderer<SpriteSceneObject>
{
    public SpriteSceneObjectSwingRenderer(SwingRendererContext context)
    {
    	super(context);
	}

	
    protected void drawObject(Graphics2D graphics, SpriteSceneObject sprite)
    {
    	BufferedImage image = mContext.getImage(sprite.imageId);
        
        // AffineTransform transform = AffineTransform.getTranslateInstance(car.back - 0.2, car.left - 0.15);
        AffineTransform transform = AffineTransform.getTranslateInstance(sprite.left, 0);
        transform.scale(1.0/10.0, 1.0/10.0);
        graphics.drawImage(image, transform, null);

    }
	
}
