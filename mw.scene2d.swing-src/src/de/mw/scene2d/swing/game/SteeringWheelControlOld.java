package de.mw.scene2d.swing.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

/**
 * Lenkrad. 
 */
public class SteeringWheelControlOld extends JPanel
{
    private double mSteeringAngle = 10.0; 
    
    public SteeringWheelControlOld(int preferredWidth)
    {
        setPreferredSize(new Dimension(preferredWidth, preferredWidth));
        
        
        addMouseMotionListener(new MouseAdapter()
        {
            public void mouseDragged(MouseEvent e)
            {
                processMouseDrag(e.getPoint());
            }
        });
        
        addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
            }
            
            @Override
            public void keyReleased(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                {
                    mRightPressed = false;
                }
                else if(e.getKeyCode() == KeyEvent.VK_LEFT)
                {
                    mLeftPressed = false;
                }
            }
            
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                {
                    mRightPressed = true;
                }
                else if(e.getKeyCode() == KeyEvent.VK_LEFT)
                {
                    mLeftPressed = true;
                }
            }
        });
    }
    
    private boolean mLeftPressed = false;
    private boolean mRightPressed = false;
    
    
    long mLastUpdateTimeMillis = System.currentTimeMillis();
    float mLastDT;
    
    double drawbackVelocity = 0.0;
    boolean hold = false;
    
    public void update()
    {
        long currentTimeMillis = System.currentTimeMillis();
        float dt = ((float)(currentTimeMillis - mLastUpdateTimeMillis)) / 1000f;
        mLastUpdateTimeMillis = currentTimeMillis; 
        
        double angle = mSteeringAngle;
        if(mLeftPressed)
        {
            hold = angle < 0.0 && angle < -5.0; 
            angle -= 0.03 * Math.abs(angle) + 0.1;
        }
        else if(mRightPressed)
        {
            hold = angle > 0.0 && angle > 5.0;
            angle += 0.03 * Math.abs(angle) + 0.1;
        }
        else
        {
            // automatische Rückstellung der Lenkung
            if(!hold)
            {
                angle = angle * 0.95;
            }
        }
        angle = Math.max(angle, -40);
        angle = Math.min(angle, 40);
        mSteeringAngle = angle;
        repaint();
    }
    
    private void processMouseDrag(Point mousePoint)
    {
        double dx = mousePoint.x - 0.5 * getWidth();
        double dy = 0.5 * getHeight() - mousePoint.y;
        if(dy < 0)
        {
            return;
        }
        double tan = dx / dy;
        double angle = Math.atan(tan) * 180.0 / Math.PI;
        angle = Math.max(angle, -40);
        angle = Math.min(angle, 40);
        
        mSteeringAngle = angle;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        int width = getWidth();
        int height = getHeight();
        double r = Math.min(width, height);
        int centerX = width / 2;
        int centerY = height / 2;
        
        g.drawOval(centerX - (int)r / 2, centerY - (int)r / 2, (int)r - 1, (int)r - 1);

        {
            double wheelX = 0.5 * r * Math.sin(mSteeringAngle / 180.0 * Math.PI);
            double wheelY = -0.5 * r * Math.cos(mSteeringAngle / 180.0 * Math.PI);
            g.drawLine(centerX, centerY, centerX + (int)wheelX, centerY + (int)wheelY);
        }
        
        {
            double angle = mSteeringAngle * 10;
            double angleRad = angle / 180.0 * Math.PI;
            double sin = Math.sin(angleRad);
            double cos = Math.cos(angleRad);
            double wheelX1 = 0.5 * r * cos;
            double wheelY1 = 0.5 * r * sin;
            double wheelX2 = -0.5 * r * cos;
            double wheelY2 = -0.5 * r * sin;
            double wheelX3 = 0.25 * r * sin;
            double wheelY3 = -0.25 * r * cos;
            g.drawLine(centerX + (int)wheelX1, centerY + (int)wheelY1, centerX + (int)wheelX2, centerY + (int)wheelY2);
            g.drawLine(centerX + (int)wheelX1, centerY + (int)wheelY1, centerX + (int)wheelX3, centerY + (int)wheelY3);
            g.drawLine(centerX + (int)wheelX2, centerY + (int)wheelY2, centerX + (int)wheelX3, centerY + (int)wheelY3);
        }
        
        g.drawString(Double.toString(mSteeringAngle), 30, 30);
    }

    public double getSteeringAngle()
    {
        return mSteeringAngle;
    }

    public void setSteeringAngle(double steeringAngle)
    {
        mSteeringAngle = steeringAngle;
    }
}
