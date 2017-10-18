package de.mw.scene2d.swing.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import de.mw.scene2d.model.CarSceneObject;
import de.mw.scene2d.model.SceneObject;

public class CarSceneObjectSwingRenderer extends SceneObjectSwingRenderer
{
    private Color mColorCar = Color.decode("#f4e037");
    private Color mColorLines = Color.GRAY;
    private BufferedImage mCarImage;
    
    public CarSceneObjectSwingRenderer()
    {
        try
        {
            URL groundUrl = new File("res/bilder/audi-frei-100.png").toURI().toURL();
            mCarImage = ImageIO.read(groundUrl);
        }
        catch(IOException ex)
        {
            throw new RuntimeException(ex);
        }

    }
    
    @Override
    public void renderObject(SwingRendererContext context, Graphics2D graphics, SceneObject object)
    {
        super.renderObject(context, graphics, object);
        
        // Hilfslinie vom Ursprung (0,0) zur aktuellen Position
//        CarSceneObject car = (CarSceneObject)object;
//        graphics.setColor(Color.RED);
//        Point carPoint = car.getAbsolute(new Point(car.front, 0));
//        Line2D originLine = new Line2D.Float(0f, 0f, carPoint.x, carPoint.y);
//        graphics.draw(originLine);
        
    }
    
    protected void drawObject(Graphics2D graphics, SceneObject object)
    {
        CarSceneObject car = (CarSceneObject)object;
        
        Shape s = new Rectangle2D.Float(car.back, car.left, car.front - car.back, car.right - car.left);
        graphics.setColor(mColorCar);
        graphics.fill(s);
        graphics.setColor(mColorLines);
        graphics.draw(s);
        

        Line2D axisBack = new Line2D.Float(0f, car.left, 0f, car.right);
        graphics.draw(axisBack);
        
        Line2D axisFront = new Line2D.Float(car.axis, car.left, car.axis, car.right);
        graphics.draw(axisFront);

        // Blickrichtung
        graphics.setColor(Color.GREEN);
        Line2D look = new Line2D.Float(0f, 0f, car.axis + 3, 0f);
        graphics.draw(look);

        // Ausschlag eines gesteuerten Redes 
        float steeringAngle = car.steeringAngle;
        double steeringX = Math.cos(steeringAngle / 180.0 * Math.PI);
        double steeringY = Math.sin(steeringAngle / 180.0 * Math.PI);
        graphics.setColor(Color.BLUE);
        Line2D steering = new Line2D.Double(0f, 0f, steeringX * 2, steeringY * 2);
        graphics.draw(steering);

        AffineTransform transform = AffineTransform.getTranslateInstance(car.back - 0.2, car.left - 0.15);
        transform.scale(1.0/20.0, 1.0/20.0);
//        graphics.drawImage(mCarImage, transform, null);
        
//        graphics.setColor(Color.GREEN);
//        Point origin = car.getRelative(new Point(0,0));
//        Line2D originLine = new Line2D.Float(0f, 0f, origin.x, origin.y);
//        graphics.draw(originLine);
        
        
        
//        AffineTransform m = new AffineTransform();
//        m.rotate(-object.rotation / 180.0 * Math.PI);
//        m.translate(-object.x , -object.y);
//        
//        Point2D test1 = new Point2D.Float();
//        Point2D test2 = new Point2D.Float();
//        m.transform(test1, test2);
//        
//        graphics.setColor(Color.GREEN);
//        Line2D testLine = new Line2D.Float(test1, test2);
//        graphics.draw(testLine);
        
        
//        canvas.drawRect(car.back, car.left, car.front, car.right, DEFAULT_PAINT);
//        canvas.drawLine(0f, car.left, 0f, car.right, DEFAULT_PAINT);
//        canvas.drawLine(car.axisFront, car.left, car.axisFront, car.right, DEFAULT_PAINT);
//        canvas.drawLine(car.front, 0f, car.front + 1f, 0f, DEFAULT_PAINT);

    }

    
}
