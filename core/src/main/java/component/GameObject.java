package component;

import java.util.LinkedList;

/**
 * Basically a wrapper for components, and facilitates messaging
 * Created by Edmund on 3/17/14.
 */
public class GameObject {

    private LinkedList<Component> components;
    private boolean alive;

    public GameObject()
    {
        components = new LinkedList<>();
        alive = true;
    }

    public LinkedList<Component> getComponents()
    {
        return components;
    }
    public void addComponent(Component c)
    {
        components.add(c);
    }

    public void markDead()
    {
        alive = false;
    }

    public boolean isAlive()
    {
        return alive;
    }


}
