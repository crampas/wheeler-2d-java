package de.mw.scene2d.swing.creator.road;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class RoadCreator
{

    public static void main(String[] args) throws Exception
    {
    	RoadCreator creator = new RoadCreator();
    	BufferedImage image = creator.createTileSetImage();
    	ImageIO.write(image, "PNG", new File("target/RoadCreator.png"));
    }
    
    int tileDimension = 128;
    
    Color randsteinColor = Color.DARK_GRAY;
    int randsteinWidth = 24;
    
    Color lineColor = Color.WHITE;
    int lineWidth = 4;
    
    
    public BufferedImage createTileSetImage()
    {
    	BufferedImage tileSetImage = new BufferedImage(tileDimension * 4, tileDimension * 4, BufferedImage.TYPE_4BYTE_ABGR);
    	
    	Graphics2D graphics = tileSetImage.createGraphics();
    	for(int tileIndex = 0; tileIndex < 16; ++tileIndex)
    	{
    		int x = tileIndex % 4;
    		int y = tileIndex / 4;
        	graphics.drawImage(createTileImage(tileIndex),
        			x * tileDimension, y * tileDimension, x * tileDimension + tileDimension, y * tileDimension + tileDimension, 
        			0, 0, tileDimension, tileDimension, null);
    	}
    	
    	return tileSetImage;
    }
    
    public BufferedImage createTileImage(int tileIndex)
    {
    	BufferedImage image = new BufferedImage(tileDimension, tileDimension, BufferedImage.TYPE_4BYTE_ABGR);

    	Graphics2D graphics = image.createGraphics();

    	drawCorners(graphics);
    	drawBorders(graphics, (tileIndex & 2) == 0, (tileIndex & 8) == 0, (tileIndex & 1) == 0, (tileIndex & 4) == 0);
    	drawLine(graphics, (tileIndex & 2) != 0, (tileIndex & 8) != 0, (tileIndex & 1) != 0, (tileIndex & 4) != 0);
    	
    	return image;
    }


	private void drawCorners(Graphics2D graphics)
	{
		graphics.setColor(randsteinColor);
    	graphics.fillRect(0, 0, randsteinWidth, randsteinWidth);
    	graphics.fillRect(0, tileDimension - randsteinWidth, randsteinWidth, randsteinWidth);
    	graphics.fillRect(tileDimension - randsteinWidth, 0, randsteinWidth, randsteinWidth);
    	graphics.fillRect(tileDimension - randsteinWidth, tileDimension - randsteinWidth, randsteinWidth, randsteinWidth);
	}
    
	private void drawBorders(Graphics2D graphics, boolean left, boolean top, boolean right, boolean bottom)
	{
		graphics.setColor(randsteinColor);
		if (left)
			graphics.fillRect(0, 0, randsteinWidth, tileDimension);
		if (top)
			graphics.fillRect(0, 0, tileDimension, randsteinWidth);
		if (right)
			graphics.fillRect(tileDimension - randsteinWidth, 0, randsteinWidth, tileDimension);
		if (bottom)
			graphics.fillRect(0, tileDimension - randsteinWidth, tileDimension, randsteinWidth);
	}

	private void drawLine(Graphics2D graphics, boolean left, boolean top, boolean right, boolean bottom)
	{
		graphics.setColor(lineColor);
		int margin = tileDimension / 8;
		int length = tileDimension / 4;
		if (left)
			graphics.fillRect(margin, tileDimension / 2 - lineWidth / 2, length, lineWidth);
		if (top)
			graphics.fillRect(tileDimension / 2 - lineWidth / 2, margin, lineWidth, length);
		if (right)
			graphics.fillRect(tileDimension / 2 + margin, tileDimension / 2 - lineWidth / 2, length, lineWidth);
		if (bottom)
			graphics.fillRect(tileDimension / 2 - lineWidth / 2, tileDimension / 2 + margin, lineWidth, length);
	}
	
}
