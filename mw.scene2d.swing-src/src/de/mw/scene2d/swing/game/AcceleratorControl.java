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
    
    private double mTargetValue = 0.0; // to remove 
    private double mActualValue = 0.0; // to remove
    
    private GaugePanel mGauge = new GaugePanel(); 
    
    private ScenePanel mScenePanel;
    private boolean mHold = false; // to remove?
    
    private float mDamagePauseTime = 0;
    
    
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
                    getCar().engine.direction *= -1;
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
    
    
    
    public void update(float dt)
    {
    	mDamagePauseTime -= dt;
    	if (mDamagePauseTime > 0)
    		return;
    	
        CarSceneObject car = getCar();
        double backForce = 0.1 * car.velocity * car.velocity;
        
        double force = getCar().engine.force;
        if(mUpPressed)
        {
            mHold = false; 
            force = Math.min(force + 2.0, car.engine.forceMax);
        }
        else if(mDownPressed)
        {
            mHold = false;
//            mTargetValue *= 0.95;
            force = Math.max(force - 2.0, car.engine.forceMin);
        }
        else
        {
            // keine automatische Rückstellung 
            if(force > backForce)
            {
                mHold = true;
            }
            if(mHold)
            {
                force = Math.max(force - 2.0, backForce);
            }
            else
            {
                force = (int)(force / 2);
            }
        }
        car.engine.force = (float)force;
  
        double effectiveForce = force - backForce;
        
        double a = effectiveForce / (effectiveForce > 0 ? 40 : 10); // Beschleunigung
        double dv = a * dt; // Geschwindigkeitszunahme
        mTargetValue += dv; 

        
        mTargetValue = Math.max(mTargetValue, 0);
        mTargetValue = Math.min(mTargetValue, car.engine.velocityMax);
        
        mActualValue = mTargetValue * car.engine.direction;
        
        mGauge.setValue(mActualValue * 3.6);
        
        car.velocity = (float)mActualValue;
        
        repaint();
    }

	private CarSceneObject getCar()
	{
		CarSceneObject car = mScenePanel.getCar();
		return car;
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
        String directionIndicator = getCar().engine.direction > 0 ? "\u2191" : "\u2193";
        g.drawString(directionIndicator, 30, height / 2);
        
        
        // Kraft
        {
            g2.setStroke(mStroke1);
            g2.setColor(Color.ORANGE);
            g.drawRect(0, 0, 20, height -1);
            int forceSize = (int)(getCar().engine.force / getCar().engine.forceMax * height / 2);
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
			mDamagePauseTime = 1f;
//			getCar().engine.force = 0;
			mActualValue = 0;
			mTargetValue = 0;
//			mScenePanel.getCar().velocity = 0;
		}
	}
}
