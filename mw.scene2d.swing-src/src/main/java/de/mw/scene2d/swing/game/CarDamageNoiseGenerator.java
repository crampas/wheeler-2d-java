package de.mw.scene2d.swing.game;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import de.mw.scene2d.model.CarDamageListener;
import de.mw.scene2d.model.CarSceneObject;

public class CarDamageNoiseGenerator implements CarDamageListener, Runnable
{
    SourceDataLine mSoundLine;
    byte[] mSoundData;
    private int mFrameSize = 16384;
    private int mSoundPointer = 0;
    
    public CarDamageNoiseGenerator()
    {
        try
        {
            InputStream wavStream = new FileInputStream("res/sound/test.wav");
            InputStream bufferedStream = new BufferedInputStream(wavStream);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedStream);
            AudioFormat format = audioInputStream.getFormat();
            mSoundData = streamToByteArray(audioInputStream);
            
            
            AudioFormat outputFormat = new AudioFormat(format.getEncoding(), 
                (int)(format.getSampleRate()), format.getSampleSizeInBits(), format.getChannels(),  
                format.getFrameSize(), format.getFrameRate(), format.isBigEndian());
            
            DataLine.Info lineInfo1 = new DataLine.Info(SourceDataLine.class, outputFormat);
            mSoundLine = (SourceDataLine)AudioSystem.getLine(lineInfo1);
            
            System.out.println(mSoundData.length);
            mSoundLine.open(outputFormat, mFrameSize); // mSoundData.length 
            mSoundLine.start();
            
            Thread playThread = new Thread(this);
            playThread.start();
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
        
    }
    
    
    private Object mSyncObject = new Object();
    private boolean mDamagePending = false;
    
    private void playNoise()
    {
        if(mSoundPointer + mFrameSize > mSoundData.length)
        {
            mSoundPointer = 0;
        }
        mSoundLine.write(mSoundData, mSoundPointer, mFrameSize); // mSoundData.length
        mSoundPointer = mSoundPointer + mFrameSize;
    }

    @Override
    public void run()
    {
        try
        {
            while(true)
            {
                synchronized (mSyncObject)
                {
                    if(!mDamagePending)
                    {
                        mSoundPointer = 0;
                    }
                    mSyncObject.wait();
                    mDamagePending = false;
                }
                playNoise();
            }
        }
        catch (InterruptedException e)
        {
        }
    }
    
    public static byte[] streamToByteArray(InputStream is) throws IOException
    {
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();  
        
        byte buffer[] = new byte[4096]; 
        while(true)
        {
            int count = is.read(buffer);
            if(count <= 0)
                break;
            dataStream.write(buffer, 0, count);
        }
        is.close();
        
        return dataStream.toByteArray();
    }

    @Override
    public void onCarDamaged(CarSceneObject car, float damage)
    {
        if(damage > 0)
        {
            synchronized (mSyncObject)
            {
                mDamagePending = true;
                mSyncObject.notify();
            }
        }
    }
}
