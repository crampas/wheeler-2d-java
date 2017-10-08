package de.mw.scene2d.swing.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

import javax.swing.JPanel;

public class GaugePanel extends JPanel
{
    private Stroke mStroke1 = new BasicStroke(1);
    private Stroke mStroke2 = new BasicStroke(2);
    
    private double mAngleMin = -130;
    private double mAngleMax = 130;
    private double mValueMin = 0;
    private double mValueMax = 130;
    
    private double mValue = 0;

    
    public GaugePanel()
    {
        setBackground(Color.LIGHT_GRAY);
        setOpaque(true);
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        double r = Math.min(width, height) - 2;
        int centerX = width / 2;
        int centerY = height / 2;

        g2.setStroke(mStroke2);
        g.setColor(Color.DARK_GRAY);
        g.drawOval(centerX - (int)r / 2, centerY - (int)r / 2, (int)r - 1, (int)r - 1);
        
        for(int mark = (int)mValueMin; mark <= mValueMax; mark += 10)
        {
            drawMark(g2, mark);
        }

        double angle = (mAngleMax - mAngleMin) * (mValue - mValueMin) / (mValueMax - mValueMin) + mAngleMin;
        double rad = angle / 180.0 * Math.PI;
        double arrowX = (r / 2 - 5) * Math.sin(rad);
        double arrowY = (r / 2 - 5) * Math.cos(rad);
        g.setColor(Color.RED);
        g.drawLine(centerX, centerY, (int)(centerX + arrowX), (int)(centerY - arrowY));
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }
    
    private void drawMark(Graphics2D g, double value)
    {
        double angle = (mAngleMax - mAngleMin) * (value - mValueMin) / (mValueMax - mValueMin) + mAngleMin;
        double rad = angle / 180.0 * Math.PI;
        int width = getWidth();
        int height = getHeight();
        double r = Math.min(width, height) - 2;
        double arrowX = (r / 2 - 5) * Math.sin(rad);
        double arrowY = (r / 2 - 5) * Math.cos(rad);
        int centerX = width / 2;
        int centerY = height / 2;
        int x = (int)(centerX + arrowX);
        int y = (int)(centerY - arrowY);
        g.setColor(Color.DARK_GRAY);
        g.fillOval(x - 2,y - 2, 4, 4);
    }

    public double getValue()
    {
        return mValue;
    }

    public void setValue(double value)
    {
        mValue = value;
    }
    
}
