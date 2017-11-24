package de.mw.scene2d.swing.editor.ground;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import de.mw.scene2d.model.Scene;
import de.mw.scene2d.swing.game.SwingGround;
import de.mw.scene2d.swing.view.SwingSceneView;

public class GroundEditorPanel extends SwingSceneView
{
    private SwingGround mGround;
    private TileSelectionSource mTileSelectionSource;
    
    protected static Stroke CURSOR_STROKE = new BasicStroke(1.0f);

    private GroundEditorTool mTool = new TileTool(this);

    
    public GroundEditorPanel(SwingGround ground)
    {
        super(new Scene());

        mGround = ground;
        mScene.setGround(ground);

        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                onMouseDragged(e);
            }
            @Override
            public void mouseMoved(MouseEvent e)
            {
                onMouseMoved(e);
            }
        });
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e)
            {
                onMouseReleased(e);
            }
            
            @Override
            public void mousePressed(MouseEvent e)
            {
                onMousePressed(e);
            }
        });
        
        addMouseWheelListener(new MouseWheelListener()
        {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e)
            {
                onMouseWheelMoved(e);
            }
        });
    }

    private void onMouseWheelMoved(MouseWheelEvent e)
    {
        int distance = e.getWheelRotation();
        double scale = getViewportScale();
        scale = Math.min(Math.max(scale - distance, 1.0), 100.0);
        setViewportScale(scale);
        repaint();
    }
    
    private Point mDragPoint;
    
    private void onMouseMoved(MouseEvent e)
    {
        repaint();
    }
    
    private void onMouseDragged(MouseEvent e)
    {
        if((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0)
        {
            Point2D p2 = translateView(e.getPoint());
            int x = (int)Math.floor(p2.getX() / 10);
            int y = (int)Math.floor(p2.getY() / 10);
            
            mTool.processAddTile(x, y);

            repaint();
            
            return;
        }
        
        if((e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) != 0)
        {
            Point newDragPoint = e.getPoint();
            int dx = newDragPoint.x - mDragPoint.x;
            int dy = newDragPoint.y - mDragPoint.y;
            
            double scale = getViewportScale();
            getViewportPosition().x -= dx / scale;
            getViewportPosition().y -= dy / scale;
            
            mDragPoint = newDragPoint;
            
            repaint();
        }
    }
    
    public int getSelectedTileIndex()
    {
    	return mTileSelectionSource.getSelectedTileIndex();
    }
    
    
    
    public void changePatch(int x, int y, int tileIndex)
    {
        mGround.setTileIndex(x, y, tileIndex);
    }
    
    private void onMousePressed(MouseEvent e)
    {
        if((e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) != 0)
        {
            mDragPoint = e.getPoint();
            return;
        }
        
        if((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0)
        {
            Point2D p2 = translateView(e.getPoint());
            int x = (int)Math.floor(p2.getX() / 10);
            int y = (int)Math.floor(p2.getY() / 10);
            
            mTool.processSetTile(x, y);
            
            repaint();
            
            return;
        }
        
        
    }

    private Point2D translateView(Point viewPoint)
    {
        AffineTransform transform = getViewportTransform();
        AffineTransform inversTransform = null;
        try
        {
            inversTransform = transform.createInverse();
        }
        catch(NoninvertibleTransformException ex)
        {
            throw new RuntimeException(ex);
        }
        Point2D worldPoint = inversTransform.transform(viewPoint, null);
        return worldPoint;
    }
    
    private void onMouseReleased(MouseEvent e)
    {
        mDragPoint = null;
    }
    
    protected void paintComponent(java.awt.Graphics g) 
    {
        Graphics2D g2 = (Graphics2D)g; 
        super.paintComponent(g);
        
        
        Point mousePoint = getMousePosition();
        if(mousePoint != null)
        {
            AffineTransform transform = getViewportTransform();
            AffineTransform inversTransform = null;
            try
            {
                inversTransform = transform.createInverse();
            }
            catch(NoninvertibleTransformException ex)
            {
                throw new RuntimeException(ex);
            }
            Point2D mousePointWorld = inversTransform.transform(mousePoint, null);
            
            int tileIndexX = (int)Math.floor(mousePointWorld.getX() / 10.0); 
            int tileIndexY = (int)Math.floor(mousePointWorld.getY() / 10.0);

            g.setColor(Color.RED);
            int tileIndex = mGround.getTileIndex(tileIndexX, tileIndexY);
            String tooltip = String.format("(%d;%d) (%.1f;%.1f) %d %d", tileIndexX, tileIndexY, 
            		mousePointWorld.getX(), mousePointWorld.getY(), 
            		tileIndex >> 16, tileIndex & 0xffff);
            g.drawString(tooltip, mousePoint.x + 15, mousePoint.y + 15);

            g2.setTransform(transform);
            g2.setStroke(CURSOR_STROKE);
            g.drawRect(tileIndexX * 10, tileIndexY * 10, 10, 10);
            
        }
        
    }

    public TileSelectionSource getTileSelectionSource()
    {
        return mTileSelectionSource;
    }

    public void setTileSelectionSource(TileSelectionSource tileSelectionSource)
    {
        mTileSelectionSource = tileSelectionSource;
    }
}
