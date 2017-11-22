package de.mw.scene2d.model;

public class SpriteSceneObject extends SceneObject
{
    public float left = -1.15f;
    public float right = 1.15f;
    public float top = 0f; 
    public float bottom = 4.5f;
    
    public String imageId;
    
    public SpriteSceneObject(String imageId)
	{
    	this.imageId = imageId;    	
	}
}
