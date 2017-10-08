package de.mw.scene2d.model;

import java.io.Serializable;

public class IntArray2 implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private final int d1;
    private final int d2;
    private final int[] value;
    
    public IntArray2(int d1, int d2)
    {
        this.d1 = d1;
        this.d2 = d2;
        value = new int[d1 * d2];
    }
    
    public int get(int i1, int i2)
    {
        return value[i1 + i2 * d1];
    }

    
    public void set(int i1, int i2, int v)
    {
        value[i1 + i2 * d1] = v;
    }
    
    public void setAll(int v)
    {
        for (int i = 0; i < value.length; ++i)
        {
            value[i] = v;
        }
    }

    public int getD1()
    {
        return d1;
    }

    public int getD2()
    {
        return d1;
    }
}
