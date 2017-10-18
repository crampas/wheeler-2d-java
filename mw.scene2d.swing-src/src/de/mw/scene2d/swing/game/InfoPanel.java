package de.mw.scene2d.swing.game;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.mw.scene2d.model.CarSceneObject;

public class InfoPanel extends JPanel
{
    private ScenePanel mScenePanel;
    
    private VelocityInfoField mVelocityInfoPanel = new VelocityInfoField();
    private PositionInfoField mPositionInfoPanel = new PositionInfoField();
    private DamageInfoField mDamageInfoField = new DamageInfoField();
    private FpsInfoField mFpsInfoField = new FpsInfoField();
    
    public InfoPanel(ScenePanel scenePanel)
    {
        mScenePanel = scenePanel;
        
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        add(mVelocityInfoPanel);
        add(mPositionInfoPanel);
        add(mDamageInfoField);
        add(mFpsInfoField);
    }
    
    public void update()
    {
        CarSceneObject car = mScenePanel.getCar();
        mVelocityInfoPanel.update(car);
        mPositionInfoPanel.update(car);
        mDamageInfoField.update(car);
        mFpsInfoField.update(mScenePanel);
    }

    static class VelocityInfoField extends JLabel
    {
        public void update(CarSceneObject car)
        {
            String text = String.format("velocity %.2fm/s %.2fkm/h", car.velocity, car.velocity * 3.6);
            setText(text);
        }
    }

    static class PositionInfoField extends JLabel
    {
        public void update(CarSceneObject car)
        {
            String text = String.format("x=%.2fm y=%.2fm", car.position.x, car.position.y);
            setText(text);
        }
    }
    
    static class DamageInfoField extends JLabel
    {
        public void update(CarSceneObject car)
        {
            String text = String.format("damage=%.2f", car.damaged);
            setText(text);
        }
    }
    
    static class FpsInfoField extends JLabel
    {
        public void update(ScenePanel panel)
        {
            String text = String.format("fps=%d", panel.getFps());
            setText(text);
        }
    }

}
