package de.mw.scene2d.swing.game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import de.mw.scene2d.model.CarSceneObject;
import de.mw.scene2d.model.ObjectConfig;
import de.mw.scene2d.model.Point;
import de.mw.scene2d.model.Scene;
import de.mw.scene2d.swing.view.SwingSceneView;

public class SceneFrame extends JFrame
{
    

    public static void main(String[] arguments) throws Exception
    {
//        TrailerConfig t1c = new TrailerConfig();
//        JAXBContext jaxbContext = JAXBContext.newInstance(TrailerConfig.class);
//        Marshaller m = jaxbContext.createMarshaller();
//        m.marshal(t1c, new File("TrailerConfig-001.xml"));

        
        SceneFrame mainFrame = new SceneFrame();

        mainFrame.setSize(1200, 800);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
    
    private ScenePanel mScenePanel;
    private SteeringWheelControl mSteeringWheelControl;
    private AcceleratorControl mAcceleratorControl;
    private InfoPanel mInfoControl;
    private SwingSceneView mSceneMapView;

    
    
    public SceneFrame()
    {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        mScenePanel = new ScenePanel();
        contentPane.add(mScenePanel, BorderLayout.CENTER);
        CarDamageNoiseGenerator damageNoiseGenerator = new CarDamageNoiseGenerator();
        mScenePanel.setCarDamageListener(damageNoiseGenerator);
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPane.add(rightPanel, BorderLayout.EAST);

        BorderLayout rightBottomPanelLayout = new BorderLayout();
        rightBottomPanelLayout.setVgap(5);
        JPanel rightBottomPanel = new JPanel(rightBottomPanelLayout);
        rightPanel.add(rightBottomPanel, BorderLayout.SOUTH);

        
        
        mSteeringWheelControl = new SteeringWheelControl(mScenePanel, 200);
        rightPanel.add(mSteeringWheelControl, BorderLayout.NORTH);
        
        mAcceleratorControl = new AcceleratorControl(mScenePanel, 200);
        rightBottomPanel.add(mAcceleratorControl, BorderLayout.SOUTH);
        
        
        mSceneMapView = new SwingSceneView(mScenePanel.getScene());
        mSceneMapView.setPaintGrid(true);
        mSceneMapView.setViewportScale(2);
        mSceneMapView.setViewportPosition(new Point(50, 50));
        mSceneMapView.setPreferredSize(new Dimension(200, 200));
        rightBottomPanel.add(mSceneMapView, BorderLayout.CENTER);
        
        mInfoControl = new InfoPanel(mScenePanel);
        rightPanel.add(mInfoControl, BorderLayout.CENTER);
        
        Timer updateTimer = new Timer(1, new UpdateListener());
        updateTimer.start();
        
        addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                mAcceleratorControl.dispatchEvent(e);
                mSteeringWheelControl.dispatchEvent(e);
                
                if(e.getKeyChar() == KeyEvent.VK_ENTER)
                {
                    mScenePanel.switchCar();
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e)
            {
                mSteeringWheelControl.dispatchEvent(e);
                mAcceleratorControl.dispatchEvent(e);
            }
            
            @Override
            public void keyPressed(KeyEvent e)
            {
                mSteeringWheelControl.dispatchEvent(e);
                mAcceleratorControl.dispatchEvent(e);
            }
        });
    }
    
    private class UpdateListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            mSteeringWheelControl.update();
            mAcceleratorControl.update();

            double steeringAngle = mSteeringWheelControl.getSteeringAngle();
            double velocity = mAcceleratorControl.getActualValue();
            
            CarSceneObject car = mScenePanel.getCar();
            car.steeringAngle = (float)steeringAngle;
            car.velocity = (float)velocity;
            mScenePanel.update();
            mSceneMapView.repaint();

            mInfoControl.update();
        }
    }

    public AcceleratorControl getAcceleratorControl()
    {
        return mAcceleratorControl;
    }

}
