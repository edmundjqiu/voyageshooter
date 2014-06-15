package action;

import org.luaj.vm2.LuaThread;
import scripting.ScriptingEngine;

/**
 * Created by Edmund on 1/14/14.
 */
public abstract class Action {

    private boolean isActionComplete = false;
    private LuaThread callBack;

    public Action()
    {

    }

    public Action(LuaThread callback)
    {
        this.callBack = callback;
    }

    public abstract void onStart();
    public abstract void update(float dt);
    public void setCallBack(LuaThread callBack)
    {
        this.callBack = callBack;
    }

    public void markActionComplete()
    {
        isActionComplete = true;
    }

    public boolean isActionComplete()
    {
        return isActionComplete;
    }

    public void onEnd()
    {
        if (callBack != null)
            callBack.resume(ScriptingEngine.EMPTY_VARARGS);
    }


}
