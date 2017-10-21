package de.mw.scene2d.swing.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import de.mw.scene2d.model.ObjectConfig;

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
    
    public static ObjectConfig readObjectConfig(String filename)
    {
    	try
    	{
	        JAXBContext jaxbObjectConfigContext = JAXBContext.newInstance(ObjectConfig.class);
	        Unmarshaller jaxbObjectConfigUnmarshaller = jaxbObjectConfigContext.createUnmarshaller();
			
	        ObjectConfig carConfig = (ObjectConfig)jaxbObjectConfigUnmarshaller.unmarshal(new File(filename));
	        return carConfig;
    	}
    	catch(Exception ex)
    	{
    		throw new RuntimeException(ex);
    	}
    	
    }
}
