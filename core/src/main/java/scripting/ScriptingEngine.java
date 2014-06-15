package scripting;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.*;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Edmund on 1/9/14.
 */

public class ScriptingEngine {

    /** The global LuaJ value, effectively the state */
    private LuaValue luaGlobals;

    /** For calling functions with no parameters */
    public static final LuaValue[] EMPTY_LUAVALUE =
            new LuaValue[] {};

    /** For resuming threads with no parameters */
    public static final Varargs EMPTY_VARARGS =
            LuaValue.varargsOf( new LuaValue[] {} );

    /**
     *  ScriptingEngine holds the Lua global variable and provides useful methods
     *  of accessing features from LuaJ.
     */
    public ScriptingEngine()
    {
        luaGlobals = JsePlatform.standardGlobals();
    }

    public ScriptingEngine(LuaValue luaGlobals)
    {
        this.luaGlobals = luaGlobals;
    }

    /**
     * Loads and runs a script referenced by a filename
     * @param filename The lua file which will be executed in luaGlobals
     */
    public void loadScript(String filename)
    {
        try
        {
            LoadState.load( new FileInputStream(filename),
                    filename, luaGlobals ).call();
        }
        catch (IOException e)
        {
            System.err.println("Failed to load: " + filename);
        }
    }

    /**
     * Resumes a LuaThread without any parameters
     * Beware - Varargs is weird and when dealing with
     * coroutine.yield(a1, a2...)
     * a1 will be found under args.checkvalue(2),
     * a2 with 3, so forth. NIL with 1.
     * @param thread the LuaThread being resumed
     * @return the Vararg results of the next coroutine.yield(args)
     */
    public Varargs resumeThread(LuaThread thread)
    {
        return thread.resume(EMPTY_VARARGS);
    }

    /**
     * Resumes a LuaThread and passes the parameters of Varargs
     * Beware - Varargs is weird and when dealing with
     * coroutine.yield(a1, a2...)
     * a1 will be found under args.checkvalue(2), a2 with 3,
     * so forth. NIL with 1.
     * @param thread the LuaThread being resumed
     * @param parameters the Vararg parameters being passed to LuaThread
     * @return the Vararg results of the next coroutine.yield(args)
     */
    public Varargs resumeThread(LuaThread thread, Varargs parameters)
    {
        return thread.resume(parameters);
    }

    /**
     * Resumes a LuaThread and passes the Objects in an
     * Object[] as parameters to LuaJ
     * @param thread the LuaThread being resumed
     * @param parameters the Object[] parameters being passed to LuaThread
     */
    public Varargs resumeThread(LuaThread thread, Object... parameters)
    {
        return thread.resume(objectsToVarargs(parameters));
    }

    /**
     * Converts an array of Objects, or Object[] to Varargs
     * @param objects an array of Object, or Object[]
     * @return the values in form of Varargs
     */
    public static Varargs objectsToVarargs(Object[] objects)
    {
        return LuaValue.varargsOf(objectsToLuaValues(objects));
    }

    /**
     * Converts an array of Objects into one of LuaValue
     * @param objects Object[] to convert to LuaValue[]
     * @return An array LuaValue[] with converted values
     */
    public static LuaValue[] objectsToLuaValues(Object[] objects)
    {
        int length = objects.length;
        LuaValue[] luaValues = new LuaValue[length];
        for (int i = 0; i < length; i++)
        {
            luaValues[i] = CoerceJavaToLua.coerce(objects[i]);
        }
        return luaValues;
    }

    /**
     * Calls the function referenced by functionName
     * registered in the Lua global
     * @param functionName the name of the function
     *                     registered in the Lua global
     * @return the Vararg results from Lua return a1, a2...
     */
    public Varargs callFunction(String functionName)
    {
        return getLuaFunction(functionName).invoke(EMPTY_LUAVALUE);
    }

    /**
     * Calls the function referenced by functionName
     * registered in the Lua global
     * @param functionName the name of the function registered in the Lua global
     * @param values the LuaValue[] parameters being passed
     * @return the Vararg results from Lua return a1, a2...
     */
    public Varargs callFunction(String functionName, LuaValue... values)
    {
        return getLuaFunction(functionName).invoke(values);
    }

    /**
     * Creates a thread fromthe function referenced by
     * functionName in the Lua global
     * @param functionName the name of the function registered in the Lua global
     * @return the LuaThread created from the provided function
     */
    public LuaThread createThread(String functionName)
    {
        return new LuaThread(luaGlobals.checkglobals(),
                getLuaFunction(functionName));
    }

    /**
     * Obtains from the Lua global the function referenced by functionName
     * @param functionName the name of the function registered in the Lua global
     * @return the LuaFunction which represents the function referred
     * to by functionName
     */
    public LuaFunction getLuaFunction(String functionName)
    {
        return (LuaFunction)luaGlobals.get(functionName);
    }

    /**
     * Yield the currently running thread
     */
    public void yieldThread()
    {
        luaGlobals.checkglobals().yield(EMPTY_VARARGS);
    }

    public LuaValue[] getVariables(String[] references)
    {
        LuaValue[] values = new LuaValue[references.length];
        for (int i = 0; i < references.length; i++)
        {
            String ref = references[i];
            values[i] = luaGlobals.get(ref);
        }

        return values;
    }

}
