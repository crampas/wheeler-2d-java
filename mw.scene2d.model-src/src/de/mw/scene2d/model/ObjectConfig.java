package de.mw.scene2d.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ObjectConfig
{
    public float left = -1.1f;
    public float right = 1.1f;
    public float front = -0.75f; 
    public float back = -7.0f;
    public float axis = -3.5f;
    
    public ObjectConfig()
    {
        
    }
}
