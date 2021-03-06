package de.mw.scene2d.swing.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import de.mw.scene2d.model.CarSceneObject;
import de.mw.scene2d.model.Edge;
import de.mw.scene2d.model.Ground;
import de.mw.scene2d.model.ParkingRectSceneObject;
import de.mw.scene2d.model.Point;
import de.mw.scene2d.model.Scene;
import de.mw.scene2d.model.SceneObject;
import de.mw.scene2d.model.SignSceneObject;
import de.mw.scene2d.model.SpriteSceneObject;
import de.mw.scene2d.model.TempSceneObject;
import de.mw.scene2d.model.TrailerSceneObject;
import de.mw.scene2d.swing.game.SwingGround;

public class SwingSceneView extends JPanel implements SwingRendererContext
{
    public final Scene mScene;
    
    private Point mViewportPosition = new Point(0f, 0f);
    private double mViewportScale = 20;
    private float mViewportRotation = 0f;

    private long mLastPaintMillis = 0;
    private long mLastPaintInterval = 0;
    
    private int mFps;

    private boolean mPaintGrid = false;
    private boolean mPaintNavPaths = false;
    
    
    public SwingSceneView(Scene scene)
    {
        mScene = scene;
        
        setBackground(Color.decode("#3dc779"));
        setOpaque(true);
    }
    
     
    
    protected static Stroke GRID_STROKE = new BasicStroke(0.01f);
    protected static Color GRID_COLOR = new Color(200, 200, 200);

    private static Stroke NAVPATH_STROKE = new BasicStroke(0.01f);
    private static Color NAVPATH_COLOR = new Color(200, 100, 100);
    
    protected void drawBackgroundTile(Graphics2D graphics, int x0, int y0, int patch)
    {
        Ground ground = mScene.getGround();
        float tileDimension = ground.tileDimension;
        ((SwingGround)ground).drawTile(graphics, x0 * tileDimension, y0 * tileDimension, x0 * tileDimension + tileDimension, y0 * tileDimension + tileDimension, patch);
        
        if(x0 < 0 || x0 >= ground.getWidth() || y0 < 0 || y0 >= ground.getHeight())
        {
            return;
        }
        
        if (mPaintNavPaths)
        	drawNavPaths(graphics, x0, y0);
    }

    protected void drawNavPaths(Graphics2D graphics, int tileX, int tileY)
    {
        Ground mGround = mScene.getGround();
        List<Edge> navPathList = ((SwingGround)mGround).getTileNavPathList(tileX, tileY);
        if(navPathList == null)
        {
            return;
        }
        
        graphics.setStroke(NAVPATH_STROKE);
        graphics.setColor(NAVPATH_COLOR);
        Line2D.Float edgeLine = new Line2D.Float();
        for (Edge edge : navPathList)
        {
            edgeLine.setLine(edge.p0.x, edge.p0.y, edge.p1.x, edge.p1.y); 
            graphics.draw(edgeLine);
        }
    }

    
    protected void drawNavPaths(Graphics2D graphics)
    {
        Ground mGround = mScene.getGround();
        graphics.setStroke(NAVPATH_STROKE);
        graphics.setColor(NAVPATH_COLOR);
        Line2D.Float edgeLine = new Line2D.Float();
        for (Edge edge : mGround.getNavPathEdgeList())
        {
          edgeLine.setLine(edge.p0.x, edge.p0.y, edge.p1.x, edge.p1.y); 
          graphics.draw(edgeLine);
        }
    }

    
    protected AffineTransform getViewportTransform()
    {
        int width = getWidth();
        int height = getHeight();
        AffineTransform transform = new AffineTransform();
        
        transform.translate(width / 2, height / 2);
        transform.scale(mViewportScale, mViewportScale);
        transform.rotate(getViewportRotation() / 180.0 * Math.PI);
        transform.translate(-mViewportPosition.x, -mViewportPosition.y);
        
        return transform;
    }
    
    protected void paintComponent(java.awt.Graphics g) 
    {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        Graphics2D objectGraphics = (Graphics2D)g2.create();
        
        //=========================================================================================
        // Poritionierung des Viewports
        //-----------------------------------------------------------------------------------------
        int width = getWidth();
        int height = getHeight();
        AffineTransform transform = getViewportTransform();
        objectGraphics.setTransform(transform);
        
        //=========================================================================================
        // sichtbarer Bereich in Objektkoordinaten berechnen
        //-----------------------------------------------------------------------------------------
        AffineTransform inversTransform = null;
        try
        {
            inversTransform = transform.createInverse();
        }
        catch(NoninvertibleTransformException ex)
        {
            throw new RuntimeException(ex);
        }

        Point2D borderTopLeft = inversTransform.transform(new Point2D.Double(0, 0), null);
        Point2D borderTopRight = inversTransform.transform(new Point2D.Double(width, 0), null);
        Point2D borderBottomLeft = inversTransform.transform(new Point2D.Double(0, height), null);
        Point2D borderBottomRight = inversTransform.transform(new Point2D.Double(width, height), null);

        double minX = Math.min(Math.min(borderTopLeft.getX(), borderTopRight.getX()), 
                Math.min(borderBottomLeft.getX(), borderBottomRight.getX()));
        double minY = Math.min(Math.min(borderTopLeft.getY(), borderTopRight.getY()), 
                Math.min(borderBottomLeft.getY(), borderBottomRight.getY()));
        double maxX = Math.max(Math.max(borderTopLeft.getX(), borderTopRight.getX()), 
                Math.max(borderBottomLeft.getX(), borderBottomRight.getX()));
        double maxY = Math.max(Math.max(borderTopLeft.getY(), borderTopRight.getY()), 
                Math.max(borderBottomLeft.getY(), borderBottomRight.getY()));
        
        //=========================================================================================
        // Ground zeichnen
        //-----------------------------------------------------------------------------------------
        if(true)
        {
            Ground ground = mScene.getGround();
            float tileDimension = ground.tileDimension;

            int xIndexMin = (int)Math.floor(minX / tileDimension);
            int yIndexMin = (int)Math.floor(minY / tileDimension);
            int xIndexMax = (int)Math.floor(maxX / tileDimension);
            int yIndexMax = (int)Math.floor(maxY / tileDimension);
            
            for(int yIndex = yIndexMin; yIndex <= yIndexMax; ++yIndex)
            {
                for(int xIndex = xIndexMin; xIndex <= xIndexMax; ++xIndex)
                {
                    int tileIndex = ground.getTileIndex(xIndex, yIndex);
                    drawBackgroundTile(objectGraphics, xIndex, yIndex, tileIndex);
                }
            }
        }
        //=========================================================================================
        // paint Grid
        //-----------------------------------------------------------------------------------------
        if(mPaintGrid)
        {
            objectGraphics.setStroke(GRID_STROKE);
            objectGraphics.setColor(GRID_COLOR);

            Ground ground = mScene.getGround();

            double tileDimension = ground.tileDimension;
            double gridMinX = Math.ceil(minX / tileDimension) * tileDimension;
            double gridMaxX = Math.floor(maxX / tileDimension) * tileDimension;
            double gridMinY = Math.ceil(minY / tileDimension) * tileDimension;
            double gridMaxY = Math.floor(maxY / tileDimension) * tileDimension;
            
            for(int x = (int)gridMinX; x <= gridMaxX; x += tileDimension)
            {
                objectGraphics.drawLine(x, (int)minY, x, (int)maxY);
            }
            for(int y = (int)gridMinY; y <= gridMaxY; y += tileDimension)
            {
                objectGraphics.drawLine((int)minX, y, (int)maxX, y);
            }
        }
        
        //=========================================================================================
        // Objekte zeichnen
        //-----------------------------------------------------------------------------------------
        for (SceneObject sceneObject : mScene.getSceneObjectList())
        {
            SceneObjectSwingRenderer renderer = getSceneObjectRenderer(sceneObject);
            renderer.renderObject(this, objectGraphics, sceneObject);
        }

        //=========================================================================================

        objectGraphics.dispose();
        
        
        //=========================================================================================
        // FPS berechnen
        //-----------------------------------------------------------------------------------------
        long currentPaintMillis = System.currentTimeMillis();
        long currentPaintInterval = currentPaintMillis - mLastPaintMillis;
        mLastPaintInterval = (100 * currentPaintInterval + mLastPaintInterval) / 101;
        double fps = 1000.0 / (double)mLastPaintInterval;
        mFps = (int)fps;
        mLastPaintMillis = currentPaintMillis;
    }
    
    
    SceneObjectSwingRenderer<SceneObject> mSceneObjectRenderer = new SceneObjectSwingRenderer<SceneObject>(this); 
    CarSceneObjectSwingRenderer mCarSceneObjectRenderer = new CarSceneObjectSwingRenderer(this);
    TrailerSceneObjectSwingRenderer mTrailerSceneObjectRenderer = new TrailerSceneObjectSwingRenderer(this);
    TempSceneObjectRenderer mTempRenderer = new TempSceneObjectRenderer(this);
    SignSceneObjectRenderer mSignRenderer = new SignSceneObjectRenderer(this);
    ParkingRectSceneObjectSwingRenderer mParkingRectRenderer = new ParkingRectSceneObjectSwingRenderer(this);
    SpriteSceneObjectSwingRenderer mSpriteRenderer = new SpriteSceneObjectSwingRenderer(this); 
    
    public SceneObjectSwingRenderer getSceneObjectRenderer(SceneObject object)
    {
        Class objectType = object.getClass();
        if(SceneObject.class.equals(objectType))
        {
            return mSceneObjectRenderer;
        }
        if(CarSceneObject.class.equals(objectType))
        {
            return mCarSceneObjectRenderer;
        }
        if(TrailerSceneObject.class.equals(objectType))
        {
            return mTrailerSceneObjectRenderer;
        }
        if(TempSceneObject.class.equals(objectType))
        {
            return mTempRenderer;
        }
        if(SignSceneObject.class.equals(objectType))
        {
            return mSignRenderer;
        }
        if(ParkingRectSceneObject.class.equals(objectType))
        {
            return mParkingRectRenderer;
        }
        if(SpriteSceneObject.class.equals(objectType))
        {
            return mSpriteRenderer;
        }
        
        return mSceneObjectRenderer;
    }

    private Map<String, BufferedImage> mImageCache = new HashMap<String, BufferedImage>();
    
    public BufferedImage getImage(String imageId)
    {
    	BufferedImage image = mImageCache.get(imageId);
    	if (image == null)
    	{
        	image = readImage(imageId);
        	mImageCache.put(imageId, image);
    	}
    	return image;
    }

	private BufferedImage readImage(String imageId)
	{
		try
		{
		    URL groundUrl = new File("res/sprites/" + imageId).toURI().toURL();
		    return ImageIO.read(groundUrl);
		}
		catch(IOException ex)
		{
		    throw new RuntimeException(ex);
		}
	}
    
    @Override
    public void renderObject(Graphics2D graphics, SceneObject object)
    {
        SceneObjectSwingRenderer renderer = getSceneObjectRenderer(object);
        renderer.renderObject(this, graphics, object);
    }

    @Override
    public float getViewportRotation()
    {
        return mViewportRotation;
    }

    public double getViewportScale()
    {
        return mViewportScale;
    }

    public void setViewportScale(double viewportScale)
    {
        mViewportScale = viewportScale;
    }

    public Point getViewportPosition()
    {
        return mViewportPosition;
    }

    public void setViewportPosition(Point viewportPosition)
    {
        mViewportPosition = viewportPosition;
    }

    public void setViewportRotation(float viewportRotation)
    {
        mViewportRotation = viewportRotation;
    }

    public Scene getScene()
    {
        return mScene;
    }

    public int getFps()
    {
        return mFps;
    }

    public boolean isPaintGrid()
    {
        return mPaintGrid;
    }

    public void setPaintGrid(boolean paintGrid)
    {
        mPaintGrid = paintGrid;
    }

	public boolean isPaintNavPaths()
	{
		return mPaintNavPaths;
	}

	public void setPaintNavPaths(boolean paintNavPaths)
	{
		mPaintNavPaths = paintNavPaths;
	}
}
