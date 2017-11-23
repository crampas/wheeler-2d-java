package de.mw.scene2d.swing.game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import de.mw.scene2d.model.CarSceneObject;
import de.mw.scene2d.model.Point;
import de.mw.scene2d.model.SceneListener;
import de.mw.scene2d.swing.view.SwingSceneView;

public class SceneFrame extends JFrame
{
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
		mScenePanel.addCarDamageListener(damageNoiseGenerator);

		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		contentPane.add(rightPanel, BorderLayout.EAST);

		BorderLayout rightBottomPanelLayout = new BorderLayout();
		rightBottomPanelLayout.setVgap(5);
		JPanel rightBottomPanel = new JPanel(rightBottomPanelLayout);
		rightPanel.add(rightBottomPanel, BorderLayout.SOUTH);

		mSteeringWheelControl = new SteeringWheelControl(mScenePanel, 200);
		rightPanel.add(mSteeringWheelControl, BorderLayout.NORTH);

		{
			JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
			rightBottomPanel.add(buttonPanel, BorderLayout.NORTH);

			{
				JButton testButton = new JButton("Show Map");
				testButton.addActionListener((ActionEvent arg) -> mSceneMapView.repaint());
				testButton.setFocusable(false);
				buttonPanel.add(testButton);
			}
			{
				JButton button = new JButton("Uncouple Trailer");
				button.addActionListener((ActionEvent arg) -> mScenePanel.uncoupleTrailer());
				button.setFocusable(false);
				buttonPanel.add(button);
			}
		}

		mSceneMapView = new SwingSceneView(mScenePanel.getScene());
		mSceneMapView.setPaintGrid(false);
		mSceneMapView.setPreferredSize(new Dimension(200, 200));
		rightBottomPanel.add(mSceneMapView, BorderLayout.CENTER);

		mAcceleratorControl = new AcceleratorControl(mScenePanel, 200);
		rightBottomPanel.add(mAcceleratorControl, BorderLayout.SOUTH);

		mInfoControl = new InfoPanel(mScenePanel);
		rightPanel.add(mInfoControl, BorderLayout.CENTER);

		addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				mAcceleratorControl.dispatchEvent(e);
				mSteeringWheelControl.dispatchEvent(e);

				if (e.getKeyChar() == KeyEvent.VK_ENTER)
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

	private SceneListener mSimulationUpdateLisener;
	
	public void startSimulation(SceneListener updateLisener)
	{
		mSimulationUpdateLisener = updateLisener;
		{
			int width = mScenePanel.getScene().getGround().getWidth();
			int height = mScenePanel.getScene().getGround().getHeight();

			mSceneMapView.setViewportScale(200.0 / (10.0 * width));
			mSceneMapView.setViewportPosition(new Point(width * 10 / 2, height * 10 / 2));
		}

		Timer updateTimer = new Timer(1, new UpdateListener());
		updateTimer.start();
	}

	private class UpdateListener implements ActionListener
	{
		long mLastUpdateTimeMillis = 0;

		@Override
		public void actionPerformed(ActionEvent e)
		{
			long currentTimeMillis = System.currentTimeMillis();
			float dt = ((float) (currentTimeMillis - mLastUpdateTimeMillis)) / 1000f;
			boolean firstRun = mLastUpdateTimeMillis == 0;
			mLastUpdateTimeMillis = currentTimeMillis;

			if (firstRun)
				return;

			mSteeringWheelControl.update(dt);
			mAcceleratorControl.update(dt);

			double steeringAngle = mSteeringWheelControl.getSteeringAngle();
			CarSceneObject car = mScenePanel.getCar();
			car.steeringAngle = (float) steeringAngle;

			mScenePanel.update(dt);
			// mSceneMapView.repaint();

			mInfoControl.update();
			
			mSimulationUpdateLisener.update(0, dt);
		}
	}

	public AcceleratorControl getAcceleratorControl()
	{
		return mAcceleratorControl;
	}

	public ScenePanel getScenePanel()
	{
		return mScenePanel;
	}

}
