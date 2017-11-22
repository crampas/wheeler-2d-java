package de.mw.scene2d.model;

public class SignSceneObject extends SceneObject
{
	public String text; 
	
	public SignSceneObject(String text)
	{
		this.text = text;
	}

	@Override
	public void update(float time, float td)
	{
        for (SceneObject object : childList)
        {
            float dx = position.x - object.position.x;
            float dy = position.y - object.position.y;
            float distance = dx * dx + dy * dy;
            active = distance > 15 * 15;
            if(active)
                break;
        }
	}

}
