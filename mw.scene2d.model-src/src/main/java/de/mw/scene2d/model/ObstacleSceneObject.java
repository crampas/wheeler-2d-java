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
        float xMin = position.x - 5f;
        float xMax = position.x + 5f;
        float yMin = position.y - 5f;
        float yMax = position.y + 5f;
        
        
        for (SceneObject object : mSceneObjectList)
        {
            if(object == this || object instanceof ObstacleSceneObject)
            {
                continue;
            }
            
            boolean inFrame = object.position.x >= xMin && object.position.x <= xMax &&
                object.position.y >= yMin && object.position.y <= yMax;
            active = inFrame;
            if(active)
            {
                break;
            }
        }
    }
}
