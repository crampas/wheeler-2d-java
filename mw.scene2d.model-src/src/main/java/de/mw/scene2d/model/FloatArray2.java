package de.mw.scene2d.model;

public class FloatArray2
{
    private int d1;
    private int d2;
    private float[] value;
    
    public FloatArray2(int d1, int d2)
    {
        this.d1 = d1;
        this.d2 = d2;
        value = new float[d1 * d2];
    }
    
    public float get(int i1, int i2)
    {
        return value[i1 + i2 * d1];
    }

    
    public void set(int i1, int i2, float v)
    {
        value[i1 + i2 * d1] = v;
    }

    public void forAll(Callback c)
    {
        for(int i1 = 0; i1 < d1; i1++)
        {
            for(int i2 = 0; i2 < d2; i2++)
            {
                c.process(this, i1, i2);
            }
        }
    }

    public void forAll(int param, Callback c)
    {
        for(int i1 = 0; i1 < d1; i1++)
        {
            for(int i2 = 0; i2 < d2; i2++)
            {
                c.process(this, i1, i2, param);
            }
        }
    }

    public static class Callback
    {
        public void process(FloatArray2 array, int i1, int i2)
        {}
        public void process(FloatArray2 array, int i1, int i2, int param)
        {}
    }
}
