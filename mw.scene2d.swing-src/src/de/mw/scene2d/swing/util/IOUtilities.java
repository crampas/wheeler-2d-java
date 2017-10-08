package de.mw.scene2d.swing.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtilities
{
    public static byte[] readFileAsByteArray(String filename)
    {
        try
        {
            InputStream input = new FileInputStream(filename);
            ByteArrayOutputStream output = new ByteArrayOutputStream(); 
            byte[] buffer = new byte[4096];
            while(true)
            {
                int readCount = input.read(buffer);
                if(readCount < 0)
                {
                    break;
                }
                output.write(buffer, 0, readCount);
            }
            input.close();
            return output.toByteArray();
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }
}
