package component;

import java.util.*;

/**
 * Holds a copy of every component for easy individual updating
 * Also assigns an ID to components organized under objects
 * Component-Entity systems need something to bind and organize
 * components as Entities, so this is it.
 *
 * Created by Edmund on 1/14/14.
 *
 * Note: Maybe I can make the thing an object pool and just
 * reinitialize dead GameObjects with new data instead of
 * constructing new ones...
 *
 * @author Edmund
 * @version March 21, 2014
 */
public class ComponentManager {

    /** Store Components based on their type */
    private HashMap<Class, LinkedList<Component>> typeMappings;
    private LinkedList<Class> classTypes;

    /** Maps IDs to the GameObjects Components comprise*/
    private GameObject[] idMapping;
    private final int INITIAL_ID_CAPACITY = 500;
    private int mappingCapacity;
    private int idIndex;

    public ComponentManager()
    {
        typeMappings = new HashMap<>();
        classTypes = new LinkedList<>();

        idMapping = new GameObject[INITIAL_ID_CAPACITY];
        mappingCapacity = INITIAL_ID_CAPACITY;
        Arrays.fill(idMapping, null);
        idIndex = 0;
    }

    /**
     * Packages components into a GameObject, assigns the ID
     * of the GameObject, and places the GameObject in memory
     * @param components
     */
    public void packageComponents(List<Component> components)
    {
        //First assign an ID
        int nextIndex = nextGameObjectIndex();

        //Second, create GameObject and add Components
        //Third, add each component to their respective lists
        GameObject obj = new GameObject();
        for (Component c : components)
        {
            c.setObjectID(nextIndex);
            obj.addComponent(c);
            this.addComponent(c);
        }

        //Finally, add GameObject at designated index
        idMapping[nextIndex] = obj;
    }

    /**
     * Return the next available space on idMapping
     * Something here isn't working.
     * @return an available index
     */
    private int nextGameObjectIndex()
    {
        int indicesChecked = 0;
        int nextIndex = -1;

        boolean indexFound = false;
        while (!indexFound)
        {
            GameObject currentObj = idMapping[idIndex];
            //If current is null, then we never declared it.
            if (currentObj == null || !currentObj.isAlive())
            {
                nextIndex = idIndex;
                indexFound = true;
                if (currentObj != null && !currentObj.isAlive())
                {
                    //Clear the object
                    idMapping[idIndex] = null;
                }
            }
            else
            {
                indicesChecked++;
            }

            //If we've checked all objects, allocate resized array
            //and copy over elements
            if (indicesChecked == mappingCapacity)
            {
                mappingCapacity += INITIAL_ID_CAPACITY;

                //Thanks to Arrays, this is one line of code, not 12.
                idMapping = Arrays.copyOf(idMapping, mappingCapacity);

                indicesChecked = 0;
                idIndex++;
            }
            else
            {
                //Either increment the index, or wrap around to 0
                int next = idIndex + 1;
                idIndex = (next == mappingCapacity) ? 0 : next;
            }


        }

        return nextIndex;
    }


    /**
     * For a given GameObject, mark that it is dead
     * and that its constituent Components are dead
     * @param id
     */
    public void markGameObjectDeath(int id)
    {
        GameObject specifiedObj = idMapping[id];
        specifiedObj.markDead();
        LinkedList<Component> componentsInID = getGameObjComponents(id);
        for (Component c : componentsInID)
        {
            c.markDead();
        }
    }

    /**
     * Utility method.
     * Get the list of Components from the GameObject by id
     */
    private LinkedList<Component> getGameObjComponents(int id)
    {
        return idMapping[id].getComponents();
    }


    /**
     * Utility method.
     * Adds a component to the mappings of component types to
     * component lists
     *
     * @param comp the Component to be added
     */
    private void addComponent(Component comp)
    {
        Class componentClass = comp.getClass();

        if (typeMappings.containsKey(componentClass))
        {
            typeMappings.get(componentClass).add(comp);
        }
        else
        {
            //It's a new type of Component

            //Allocate a new list of such Components
            LinkedList<Component> newList = new LinkedList<>();
            newList.add(comp);
            typeMappings.put(componentClass, newList);

            //Add a new entry to the list of components
            classTypes.add(componentClass);
        }
    }

    public Component getComponent(int objectID, Class c)
    {
        LinkedList<Component> objComps = getGameObjComponents(objectID);
        for (Component comp : objComps)
        {
            if (comp.getClass() == c)
            {
                return comp;
            }
        }
        return null;
    }

    /**
     * Suppose an A-Component needs to communicate with a
     * B-Component. A-Component has no direct reference to B-Component
     * so it queries ComponentManager with this method,
     * componentManager.getComponent(this, B-Component.class), and
     * this method efficiently returns B-Component.
     *
     * @param givenComponent the Component in the GameObject
     * @param c the class of the desired component of the GameObject
     * @return the desired Component of the same GameObject
     */
    public Component getComponent(Component givenComponent, Class c)
    {
        return getComponent(givenComponent.getObjectID(), c);
    }

    /**
     * Returns a list of all components of a given class type
     *
     * @param c the class of the desired type of component
     * @return a LinkedList of all Class c components in the manager
     */
    public LinkedList<Component> getAllComponentsOfClass(Class c)
    {
        return typeMappings.get(c);
    }

    /**
     *
     * @param c specific component class for updates
     * @param dt change in seconds
     */
    public void updateComponentsOfClass(Class c, float dt)
    {
        LinkedList<Component> componentsOfType = typeMappings.get(c);
        if (componentsOfType == null) return; //No component of class c

        //Update takes care of removing dead components.
        ListIterator<Component> iter = componentsOfType.listIterator();
        while (iter.hasNext())
        {
            Component comp = iter.next();
            if (comp.isAlive())
            {
                comp.update(dt);
            }
            else
            {
                iter.remove();
            }
        }

    }

    /**
     * Update every Component, of every class
     * @param dt change in seconds
     */
    public void updateAll(float dt)
    {
        for (Class componentClass : classTypes)
        {
            updateComponentsOfClass(componentClass, dt);
        }
    }


}
