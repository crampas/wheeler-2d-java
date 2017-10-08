package de.mw.scene2d.swing.view;

import java.awt.Graphics2D;

import de.mw.scene2d.model.SceneObject;

public interface SwingRendererContext
{
    public void renderObject(Graphics2D graphics, SceneObject object);
    public float getViewportRotation();
}
