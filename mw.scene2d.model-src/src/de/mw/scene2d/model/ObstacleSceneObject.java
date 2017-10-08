package de.mw.scene2d.model;

import java.util.List;


public class ObstacleSceneObject extends SceneObject
{
    private List<SceneObject> mSceneObjectList;
    
    public ObstacleSceneObject(List<SceneObject> sceneObjectList)
    {
        mSceneObjectList = sceneObjectList;
    }
    
    @Override
    public void update(float time, float td)
    {
        float xMin = x - 5f;
        float xMax = x + 5f;
        float yMin = y - 5f;
        float yMax = y + 5f;
        
        
        for (SceneObject object : mSceneObjectList)
        {
            if(object == this || object instanceof ObstacleSceneObject)
            {
                continue;
            }
            
            boolean inFrame = object.x >= xMin && object.x <= xMax &&
                object.y >= yMin && object.y <= yMax;
            active = inFrame;
            if(active)
            {
                break;
            }
        }
    }
}
