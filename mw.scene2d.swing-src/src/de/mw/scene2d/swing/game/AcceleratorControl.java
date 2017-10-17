package de.mw.scene2d.swing.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
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
import java.awt.geom.Rectangle2D;
import java.io.Console;

import javax.swing.JPanel;

import de.mw.scene2d.model.CarDamageListener;
import de.mw.scene2d.model.CarSceneObject;

/**
 * Gaspedal.
 */
public class AcceleratorControl extends JPanel implements CarDamageListener
{
    private Font mSpeedFont = new Font("Arial", 0, 25);
    private Color mSpeedColor = Color.decode("#20e000");
    private Font mDirectionFont = new Font("Arial", Font.BOLD, 72);
    
    private double mForceMax = 100.0;
    private double mForceMin = -100.0;
    private double mForce = 0.0;
    private double mBackForce = 0.0;
    private double mDirection = 1;
    private double mTargetValue = 0.0; 
    private double mActualValue = 0.0;
    
    private GaugePanel mGauge = new GaugePanel(); 
    
    private ScenePanel mScenePanel;
    private boolean mHold = false;
    
    public AcceleratorControl(ScenePanel scenePanel, int preferredWidth)
    {
        mScenePanel = scenePanel;
        setPreferredSize(new Dimension(preferredWidth, preferredWidth));
        
        add(mGauge);
        
        
        scenePanel.addCarDamageListener(this);
        
        addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                if(e.getKeyChar() == ' ')
                {
                    mTargetValue = 0;
                    mDirection *= -1;
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_UP)
                {
                    mUpPressed = false;
                }
                else if(e.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    mDownPressed = false;
                }
            }
            
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_UP)
                {
                    mUpPressed = true;
                }
                else if(e.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    mDownPressed = true;
                }
            }
        });
    }
    
    @Override
    public void setBounds(int x, int y, int width, int height)
    {
        super.setBounds(x, y, width, height);
    }
    
    @Override
    public void doLayout()
    {
        mGauge.setBounds(70, 0, getWidth() - 70, getHeight());
    }
    
    private boolean mUpPressed = false;
    private boolean mDownPressed = false;
    
    
    long mLastUpdateTimeMillis = System.currentTimeMillis();
    float mLastDT;
    
    public void update()
    {
        long currentTimeMillis = System.currentTimeMillis();
        float dt = ((float)(currentTimeMillis - mLastUpdateTimeMillis)) / 1000f;
        mLastUpdateTimeMillis = currentTimeMillis; 

        CarSceneObject car = mScenePanel.getCar();
        mBackForce = 0.1 * car.velocity * car.velocity;
        
        if(mUpPressed)
        {
            mHold = false; 
            mForce = Math.min(mForce + 2.0, mForceMax);
        }
        else if(mDownPressed)
        {
            mHold = false;
//            mTargetValue *= 0.95;
            mForce = Math.max(mForce - 2.0, mForceMin);
        }
        else
        {
            // keine automatische Rückstellung 
//            mTargetValue = mTargetValue * 0.95;
            if(mForce > mBackForce)
            {
                mHold = true;
            }
            if(mHold)
            {
                mForce = Math.max(mForce - 2.0, mBackForce);
            }
            else
            {
                mForce = (int)(mForce / 2);
            }

        }
  
        double effectiveForce = mForce - mBackForce;
        
        double a = effectiveForce / (effectiveForce > 0 ? 40 : 10); // Beschleunigung
        double dv = a * dt; // Geschwindigkeitszunahme
        mTargetValue += dv; 

        
        mTargetValue = Math.max(mTargetValue, 0);
        mTargetValue = Math.min(mTargetValue, 100);
        
        mActualValue = mTargetValue * mDirection;
        
        mGauge.setValue(mActualValue * 3.6);
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
        
        int height = getHeight();
        
//        double yvalue = 0.5 * height * mActualValue / 100;
//        int position = height / 2 - (int)yvalue ;
//        
//        g.drawLine(0, height / 2, width, position);
        
        g.setFont(mDirectionFont);
        String directionIndicator = mDirection > 0 ? "\u2191" : "\u2193";
        g.drawString(directionIndicator, 30, height / 2);
        
        
        // Kraft
        {
            g2.setStroke(mStroke1);
            g2.setColor(Color.ORANGE);
            g.drawRect(0, 0, 20, height -1);
            int forceSize = (int)(mForce / mForceMax * height / 2);
            g.fillRect(2, height / 2 - Math.max(forceSize, 0), 17, Math.abs(forceSize));
        }
        
        g.setFont(mSpeedFont);
        FontMetrics metrics = g.getFontMetrics();
        String speedText = Integer.toString((int)(mActualValue * 3.6));
        Rectangle2D textBounds = metrics.getStringBounds(speedText, g);
        int textWidth = (int)textBounds.getWidth();
        g.setColor(mSpeedColor);
        g.drawString(speedText, 135 - textWidth / 2, 135);
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public double getActualValue()
    {
        return mActualValue;
    }

    public void setActualValue(double actualValue)
    {
        mActualValue = actualValue;
    }

    public double getTargetValue()
    {
        return mTargetValue;
    }

    public void setTargetValue(double targetValue)
    {
        mTargetValue = targetValue;
    }

	@Override
	public void onCarDamaged(CarSceneObject car, float damage)
	{
		if (damage > 0)
		{
			mForce = 0;
			mActualValue = 0;
			mTargetValue = 0;
			mScenePanel.getCar().velocity = 0;
		}
	}
}
