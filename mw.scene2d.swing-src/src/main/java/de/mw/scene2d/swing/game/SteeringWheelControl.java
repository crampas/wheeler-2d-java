package de.mw.scene2d.swing.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import de.mw.scene2d.model.CarSceneObject;

/**
 * Lenkrad. 
 */
public class SteeringWheelControl extends JPanel
{
    private ScenePanel mScenePanel;
    private double mSteeringAngle = 0.0; 
    
    public SteeringWheelControl(ScenePanel scenePanel, int preferredWidth)
    {
        mScenePanel = scenePanel;
        setPreferredSize(new Dimension(preferredWidth, preferredWidth));
        
        
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
    
    
    double force = 0.0;
    double forceMax = 200.0;
    double backForce = 0.0;
    
    public void update(float dt)
    {
    	float forceStep = dt * 40;
    	
        if(mLeftPressed)
        {
            force = Math.max(force - forceStep, -forceMax);
        }
        else if(mRightPressed)
        {
            force = Math.min(force + forceStep, forceMax);
        }
        else
        {
            force = 0;
        }

        double angle = mSteeringAngle;
        
        // automatische Rückstellung der Lenkung
        CarSceneObject car = mScenePanel.getCar();
        backForce = angle * Math.abs(car.velocity) * 0.16;
        double effectiveForce = force - backForce;
        
        angle = angle + effectiveForce * dt * 2;
        
        angle = Math.max(angle, -40);
        angle = Math.min(angle, 40);
        mSteeringAngle = angle;
        repaint();
    }
    
    private Stroke mStroke1 = new BasicStroke(1);
    private Stroke mStroke2 = new BasicStroke(2);
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        double r = Math.min(width, height) - 2;
        int centerX = width / 2;
        int centerY = height / 2;
    

        // Kraft
        {
            g2.setStroke(mStroke1);
            g2.setColor(Color.ORANGE);
            g.drawRect(0, centerY + 20, width - 1, 20);
            int forceWidth = (int)(force / forceMax * r);
            g.fillRect(centerX + Math.min(forceWidth, 0), centerY + 22, Math.abs(forceWidth), 17);
        }

        g2.setStroke(mStroke2);
        g.setColor(Color.LIGHT_GRAY);
        g.drawOval(centerX - (int)r / 2, centerY - (int)r / 2, (int)r - 1, (int)r - 1);
        
        // Stellung des Rades
        {
            g.setColor(Color.BLUE);
            double wheelX = 0.5 * r * Math.sin(mSteeringAngle / 180.0 * Math.PI);
            double wheelY = -0.5 * r * Math.cos(mSteeringAngle / 180.0 * Math.PI);
            g.drawLine(centerX, centerY, centerX + (int)wheelX, centerY + (int)wheelY);
        }
        
        // Steuerrad (flacher Pfeil)
        {
            g.setColor(Color.DARK_GRAY);
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
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
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
