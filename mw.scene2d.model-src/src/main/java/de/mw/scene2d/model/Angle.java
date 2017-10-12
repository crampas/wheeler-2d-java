package de.mw.scene2d.model;

public class Angle
{
	public static Vector fromRad(double rad)
	{
		return new Vector((float)Math.cos(rad), (float)Math.sin(rad));
	}
	
    public static Vector fromDegree(double angle) {
        return fromRad(angle / 180.0 * Math.PI);
    }

    public static float getRad(Vector direction)
    {
        if (direction.y >= 0)
            return (float)Math.acos(direction.x);
        return (float)-Math.acos(direction.x);
    }

    public static float getDegree(Vector direction)
    {
        return (float)(Angle.getRad(direction) * 180.0 / Math.PI);
    }
}
