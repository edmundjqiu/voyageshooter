package action;

import org.luaj.vm2.LuaThread;

/**
 * Created by Edmund on 1/14/14.
 */
public class ScriptWaitAction extends Action {

    private float waitSeconds;
    private float passedTime;

    public ScriptWaitAction(LuaThread callback, float waitSeconds)
    {
        super(callback);
        this.waitSeconds = waitSeconds;
        passedTime = 0.0f;
    }

    public ScriptWaitAction(String ID, float waitSeconds)
    {
        //Look up the main script of the ID, incomplete as of yet
        super(null);
        this.waitSeconds = waitSeconds;
        passedTime = 0.0f;
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
        passedTime += dt;
        if (passedTime >= waitSeconds)
        {
            markActionComplete();
        }
    }
}
