package main;

import action.ActionManager;
import action.MoveAction;
import action.ScriptWaitAction;
import component.Component;
import org.luaj.vm2.LuaThread;

/**
 * Created by Edmund on 3/19/14.
 */
public class ActionFactory {
    private ActionManager actionManager;
    private Stage stage;

    public ActionFactory(Stage stage)
    {
        this.stage = stage;
        actionManager = stage.getActionManager();
    }

    public ScriptWaitAction scriptWaitAction(float duration,
                                             LuaThread callBack)
    {
        return new ScriptWaitAction(callBack, duration);
    }

    public MoveAction moveAction(Component actor, float distance, float speed,
                                 float xDirection, float yDirection)
    {
        return new MoveAction(actor, distance, speed, xDirection, yDirection);
    }

}
