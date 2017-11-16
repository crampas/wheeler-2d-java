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
import java.util.ArrayList;
import java.util.List;

import de.mw.scene2d.model.TileMap;
import de.mw.scene2d.model.Scene;
import de.mw.scene2d.swing.game.SwingGround;
import de.mw.scene2d.swing.view.SwingSceneView;

public class GroundPanel extends SwingSceneView
{
    private SwingGround mGround;
    private TileSelectionSource mTileSelectionSource;
    
    private List<ReplaceRule> mReplaceRuleList = new ArrayList<ReplaceRule>();
    
    protected static Stroke CURSOR_STROKE = new BasicStroke(1.0f);
    
    
    public GroundPanel(SwingGround ground)
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
        
        
//        mReplaceRuleList.add(new ReplaceRule(
//                new GroundMap(1, 2, 9, 8), new GroundMap(1, 2, 4, 8)));
//        mReplaceRuleList.add(new ReplaceRule(
//                new GroundMap(1, 2, 8, 9), new GroundMap(1, 2, 8, 5)));
//
//        mReplaceRuleList.add(new ReplaceRule(
//                new GroundMap(2, 1, 8, 9), new GroundMap(2, 1, 6, 9)));
//        mReplaceRuleList.add(new ReplaceRule(
//                new GroundMap(2, 1, 9, 8), new GroundMap(2, 1, 9, 7)));
//        
//        mReplaceRuleList.add(new ReplaceRule(
//                new GroundMap(3, 1, 9, 8, 9), new GroundMap(3, 1, 9, 12, 9)));
//        mReplaceRuleList.add(new ReplaceRule(
//                new GroundMap(3, 1, 9, 7, 9), new GroundMap(3, 1, 9, 12, 9)));
//        mReplaceRuleList.add(new ReplaceRule(
//                new GroundMap(3, 1, 9, 6, 9), new GroundMap(3, 1, 9, 12, 9)));
//
//        mReplaceRuleList.add(new ReplaceRule(
//                new GroundMap(1, 3, 8, 4, 8), new GroundMap(1, 3, 8, 12, 8)));
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
            int tileIndex = mTileSelectionSource.getSelectedTileIndex();
            if(tileIndex < 0)
            {
                return;
            }
            
            Point2D p2 = translateView(e.getPoint());
            int x = (int)Math.floor(p2.getX() / 10);
            int y = (int)Math.floor(p2.getY() / 10);
            changePatch(x, y, tileIndex);
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
    
    private void changePatch(int x, int y, int tileIndex)
    {
        mGround.setTileIndex(x, y, tileIndex);
        

        for (ReplaceRule rule : mReplaceRuleList)
        {
            for(int mapY = 0; mapY < rule.mLhs.getHeight(); mapY++)
            {
                for(int mapX = 0; mapX < rule.mLhs.getWidth(); mapX++)
                {
                    boolean match = mGround.getGroundMap().match(rule.mLhs, x - mapX, y - mapY);
                    if(match)
                    {
                        mGround.getGroundMap().replace(rule.mRhs, x - mapX, y - mapY);
                    }
                }
            }
        }
        
        
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
            int tileIndex = mTileSelectionSource.getSelectedTileIndex();
            if(tileIndex < 0)
            {
                return;
            }
            
            Point2D p2 = translateView(e.getPoint());
            int x = (int)Math.floor(p2.getX() / 10);
            int y = (int)Math.floor(p2.getY() / 10);
            changePatch(x, y, tileIndex);
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
