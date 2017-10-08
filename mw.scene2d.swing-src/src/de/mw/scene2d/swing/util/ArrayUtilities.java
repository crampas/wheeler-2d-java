package de.mw.scene2d.swing.util;

import java.util.Arrays;

public class ArrayUtilities
{
    public static int[] remapGroundMap(int[] map, int sourceWidth, int sourceheight, int destWidth, int destHeight, int offsetX, int offsetY, int defaultValue)
    {
        int[] destMap = new int[destHeight * destWidth];
        Arrays.fill(destMap, defaultValue);
        
        for(int index = 0; index < map.length; index++)
        {
            int x = index % sourceWidth;
            int y = index / sourceWidth;
            int destIndex = x + offsetX + (y + offsetY) * destWidth;
            destMap[destIndex] = map[index];
        }
        
        return destMap;
    }
    
    public static byte[] toByteArray(int[] array)
    {
        byte[] data = new byte[array.length * 4];
        int dataIndex = 0;
        for (int value : array)
        {
            data[dataIndex++] = (byte)(value >>> 0);
            data[dataIndex++] = (byte)(value >>> 8);
            data[dataIndex++] = (byte)(value >>> 16);
            data[dataIndex++] = (byte)(value >>> 24);
        }
        return data;
    }

    public static int[] toIntArray(byte[] data)
    {
        int[] value = new int[data.length / 4]; 
        int dataIndex = 0;
        for(int index = 0; index < value.length; index++)
        {
            value[index] |= data[dataIndex++] << 0;
            value[index] |= data[dataIndex++] << 8;
            value[index] |= data[dataIndex++] << 16;
            value[index] |= data[dataIndex++] << 24;
        }
        return value;
    }

}
