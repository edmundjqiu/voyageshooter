package scripting;

import action.MoveAction;
import action.ScriptWaitAction;
import component.Component;
import main.Stage;
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

/**
 * For now many of the JavaAPI purposes have been
 * replaced by scripts.
 * Created by Edmund on 3/17/14.
 */
public class JavaAPI extends TwoArgFunction {
    public JavaAPI()
    {

    }

    public LuaValue call(LuaValue modname, LuaValue env) {

        LuaValue library = tableOf();
        library.set( "print", new print() );
        library.set( "wait", new wait() );
        library.set( "move", new move() );
        env.set( "GameAPI", library );

        return library;
    }

    static class print extends OneArgFunction {
        public LuaValue call(LuaValue x) {
            System.out.println(x.strvalue());
            return LuaValue.valueOf(0);
        }
    }

    /**
     * Registers an action to wait until the specified amount of time
     * elapses before calling the specified thread to resume.
     */
    static class wait extends ThreeArgFunction {
        public LuaValue call(LuaValue waitDur, LuaValue stageRef,
                             LuaValue callBackThread) {
            Stage stage = (Stage)stageRef.checkuserdata();
            LuaThread callBack = (LuaThread)callBackThread.checkuserdata();

            ScriptWaitAction wait = new ScriptWaitAction(
                    callBack,
                    (float)waitDur.checkdouble()
            );

            stage.getActionManager().beginAction(wait);
            stage.getScriptingEngine().yieldThread();

            return LuaValue.valueOf(0);
        }
    }

    /**
     * move(
     *      component
     *      distance (scalar)
     *      speed (scalar)
     *      direction (x)
     *      direction(y)
     *  )
     */
    static class move extends VarArgFunction {
        public Varargs invoke(Varargs args) {

            Component actor = (Component)args.checkuserdata(1);
            float distance = (float)args.checkdouble(2);
            float speed = (float)args.checkdouble(3);
            float xDirection = (float)args.checkdouble(4);
            float yDirection = (float)args.checkdouble(5);

            MoveAction move = new MoveAction(
                    actor, distance, speed,
                    xDirection, yDirection
            );

            Stage s = actor.getStage();
            s.getActionManager().beginAction(move);

            return ScriptingEngine.EMPTY_VARARGS;
        }

    }


}
