package de.mw.scene2d.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Scene
{
    private Ground mGround;
    private List<SceneObject> mSceneObjectList = new ArrayList<SceneObject>();
    private List<SceneListener> mSceneListenerList = new ArrayList<SceneListener>();
    

    public Ground getGround()
    {
        return mGround;
    }

    public void setGround(Ground ground)
    {
        mGround = ground;
    }

    public List<SceneObject> getSceneObjectList()
    {
        return mSceneObjectList;
    }

    public List<SceneListener> getSceneListenerList()
    {
        return mSceneListenerList;
    }

}
