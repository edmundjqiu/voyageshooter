package component;

import main.Stage;
import org.luaj.vm2.LuaThread;
import scripting.ScriptingEngine;

/**
 * Created by Edmund on 3/17/14.
 */
public class ScriptingComponent extends Component {

    private String mainFunctionName;
    private boolean started;

    public ScriptingComponent(Stage stage)
    {
        super(stage);
        started = false;
    }

    public void setMainFunction(String mainFunctionName)
    {
        this.mainFunctionName = mainFunctionName;
    }

    public void begin()
    {
        ScriptingEngine s = this.getStage().getScriptingEngine();
        LuaThread t = s.createThread(mainFunctionName);
        t.resume(ScriptingEngine.objectsToVarargs(new Object[]{t, this}));
    }

//    public void receiveMessage(Message msg)
//    {
//
//    }

    public void update(float dt)
    {
        if (!started)
        {
            begin();
            started = true;
        }

    }
}
