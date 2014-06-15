package action;

import org.luaj.vm2.LuaThread;
import org.luaj.vm2.Varargs;
import scripting.ScriptingEngine;

/**
 * ScriptedAction is a custom-scripted Action, in which
 * a function in Lua defines what happens every tick,
 * as well as the termination condition.
 *
 * Created by Edmund on 1/14/14.
 */
public class ScriptedAction extends Action {

    /**
     * The name of the Lua function
     */
    private String functionName;
    private ScriptingEngine scriptingEngine;

    public ScriptedAction(LuaThread callback, String functionName)
    {
        super(callback);
        this.functionName = functionName;
    }

    public void onStart()
    {

    }

    /**
     *
     * @param dt
     */
    public void update(float dt)
    {
        Varargs args = scriptingEngine.callFunction(functionName);

    }

}

