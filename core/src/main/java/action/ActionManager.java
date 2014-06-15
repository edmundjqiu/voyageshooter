package action;


import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by Edmund on 1/13/14.
 */

public class ActionManager {

    private LinkedList<Action> currentActions;
    private LinkedList<Action> removed;

    public ActionManager()
    {
        currentActions = new LinkedList<Action>();
        removed = new LinkedList<>();
    }

    /**
     * Adds an Action to the ActionManager and begin running it
     * @param a
     */
    public void beginAction(Action a)
    {
        a.onStart();
        currentActions.add(a);
    }

    /**
     * Updates each currently active Action
     * @param dt elapsed time in seconds
     */
    public void update(float dt)
    {
        //ConcurrentModificationExceptions! Can't just 'end' as we
        //iterate through this because removed actions will resume
        //threads which add new Actions to the list.

        ListIterator<Action> iter = currentActions.listIterator();
        while (iter.hasNext())
        {
            Action a = iter.next();
            if (a.isActionComplete())
            {
                removed.add(a);
                iter.remove();
            }
            else
            {
                a.update(dt);
            }
        }

        //Now finalize the actions which are to be removed
        for (Action a : removed)
        {
            a.onEnd();
        }
        removed.clear();

    }

}

