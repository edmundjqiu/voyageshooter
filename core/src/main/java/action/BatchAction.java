package action;

import java.util.ArrayList;
import java.util.List;

/**
 * An Action which wraps a sequence of Actions which
 * are executed sequentially. BatchAction finishes
 * when the last in the Batch finishes.
 *
 * Completely untested as of 3/17/2014, needs testing
 *
 * Created by Edmund on 3/13/14.
 */
public class BatchAction extends Action {
    /** Holds the list of actions */
    private ArrayList<Action> actions;

    /** The index along actions the BatchAction is up to */
    private int actionIndex;

    public BatchAction()
    {
        actions = new ArrayList<>();
        actionIndex = 0;
    }

    /**
     * Constructs a BatchAction from some List of Actions.
     * @param newActions a List of actions to add
     */
    public BatchAction(List<Action> newActions)
    {
        actions = new ArrayList<>();
        for (Action a : newActions) actions.add(a);
    }

    /**
     * Appends a new Action to the end of the list
     * @param newAction an Action not yet started
     */
    public void addAction(Action newAction)
    {
        actions.add(newAction);
    }

    public Action getCurrentAction()
    {
        return actions.get(actionIndex);
    }

    /**
     * Starts the first action. Will do nothing
     * if the size of the Batch is 0.
     */
    public void onStart()
    {
        if (actions.size() > 0)
            getCurrentAction().onStart();
    }

    /**
     *
     * @param dt
     */
    public void update(float dt)
    {
        //If BatchAction is empty, we're done.
        if (actions.size() == 0)
        {
            markActionComplete();
            return;
        }

        //Update the current action. If the action
        //is complete, move on to the next. If
        //we reach the last act, we are complete.
        Action currentAction = getCurrentAction();
        if (currentAction.isActionComplete())
        {
            actionIndex++;
            if (actionIndex == actions.size())
            {
                markActionComplete();
            }
            else
            {
                getCurrentAction().onStart();
            }
        }
        else
        {
            currentAction.update(dt);
        }




    }

}
