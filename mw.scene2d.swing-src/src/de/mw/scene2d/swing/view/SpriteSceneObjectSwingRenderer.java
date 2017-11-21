package de.mw.scene2d.swing.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import de.mw.scene2d.model.SpriteSceneObject;

public class SpriteSceneObjectSwingRenderer extends SceneObjectSwingRenderer<SpriteSceneObject>
{
    private Color mColor = new Color(100, 100, 255, 127);
    private Color mColorLines = Color.BLUE;
    
    private BufferedImage mImage;

    
    public SpriteSceneObjectSwingRenderer()
	{
        try
        {
            URL groundUrl = new File("res/street/sprites/taxi-2.png").toURI().toURL();
            mImage = ImageIO.read(groundUrl);
        }
        catch(IOException ex)
        {
            throw new RuntimeException(ex);
        }
	}

	
    protected void drawObject(Graphics2D graphics, SpriteSceneObject sprite)
    {
//        Shape s = new Rectangle2D.Float(sprite.left, sprite.top, sprite.right - sprite.left, sprite.bottom - sprite.top);
//        graphics.setColor(mColor);
//        graphics.fill(s);
//        graphics.setColor(mColorLines);
//        graphics.draw(s);
        
        // AffineTransform transform = AffineTransform.getTranslateInstance(car.back - 0.2, car.left - 0.15);
        AffineTransform transform = AffineTransform.getTranslateInstance(sprite.left, 0);
        transform.scale(1.0/10.0, 1.0/10.0);
        graphics.drawImage(mImage, transform, null);

    }
	
}
