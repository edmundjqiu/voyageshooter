package main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import action.ActionManager;
import component.*;
import gameplay.BulletComponent;
import gameplay.EnemyComponent;

import scripting.ScriptingEngine;
import java.util.LinkedList;

/**
 * Holds everything in the current game
 * Created by Edmund on 3/17/14.
 */
public class Stage {

    //Managers/engines
    private ActionManager actionManager;
    private ScriptingEngine scriptingEngine;
    private ComponentManager componentManager;

    //QuadTree for Physics
    private QuadTreeNode quadTree;

    //Factories
    private ComponentFactory componentFactory;
    private ObjectFactory objectFactory;
    private ActionFactory actionFactory;

    //Testing stuff
    SpriteBatch batch;
    Texture res;
    public TextureRegion tower;
    public TextureRegion bullet;
    ShapeRenderer shapeRenderer;



    float rotat = 0;

    public Stage()
    {
        //Graphics
        batch = new SpriteBatch();
        res = new Texture("android/assets/res1.png");
        tower = new TextureRegion(res, 100, 150, 50, 50);
        bullet = new TextureRegion(res, 200, 250, 45, 45);
        shapeRenderer = new ShapeRenderer();

        //Object Factories
        actionFactory = new ActionFactory(this);
        componentFactory = new ComponentFactory(this);
        objectFactory = new ObjectFactory(this, componentFactory);

        //Create the QuadTree
        quadTree = new QuadTreeNode(0, 0, 1024, 768);

        //Managers
        actionManager = new ActionManager();
        componentManager = new ComponentManager();

        //Prep the scriptingEngine
        scriptingEngine = new ScriptingEngine();

        scriptingEngine.loadScript("android/assets/core.lua");
        scriptingEngine.loadScript("android/assets/TESTING.lua");

        //Must register the stage
        scriptingEngine.callFunction("registerStage",
                ScriptingEngine.objectsToLuaValues(
                        new Object[] {
                                this
                        }
                )
        );

        //The thread needs to have a reference to itself
//        LuaThread t = scriptingEngine.createThread("f");
//        scriptingEngine.resumeThread(t, new Object[] { t } );

        //Test
        componentManager.packageComponents(objectFactory.makePrimitiveEnemy());


    }

    public void update(float dt)
    {
        //Update actions
        actionManager.update(dt);

//        //Update all component physics
//        componentManager.updateComponentsOfClass(PhysicsComponent.class, dt);
//
//        //Update bullets data
//        componentManager.updateComponentsOfClass(BulletComponent.class, dt);
//
//        //Update bullets data
//        componentManager.updateComponentsOfClass(EnemyComponent.class, dt);

        componentManager.updateAll(dt);

        rotat = rotat + 0.5f;
    }

    public void draw()
    {

        batch.begin();

        //Rotation test
        batch.draw(tower,
        200,
        200,
        25,//float originX,
        25,//float originY,
        50,//float width,
        50,//float height,
        2, //float scaleX,
        2, //float scaleY,
        rotat);//float rotation);




        //Drawing code moved to DisplayComponent
        LinkedList<Component> p;
        p = componentManager.getAllComponentsOfClass(DisplayComponent.class);
        for (Component comp : p)
        {
            ((DisplayComponent)comp).draw(batch);
        }

        batch.end();


        //Visually depict the bounding boxes
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 0, 1);
        LinkedList<QuadTreeNode> allNodes = quadTree.getAllNodes();
        for (QuadTreeNode n : allNodes)
        {
            shapeRenderer.rect((float)n.getX(), (float)n.getY(),
                    (float)n.getWidth(), (float)n.getHeight());
        }
        shapeRenderer.end();

    }


    //A lot of getters for managers and factories

    public ComponentManager getComponentManager() {
        return componentManager;
    }

    public ActionManager getActionManager()
    {
        return actionManager;
    }

    public ScriptingEngine getScriptingEngine()
    {
        return scriptingEngine;
    }

    public ComponentFactory getComponentFactory() { return componentFactory; }

    public ObjectFactory getObjectFactory() { return objectFactory; }

    public ActionFactory getActionFactory() { return actionFactory; }

    public QuadTreeNode getQuadTree() { return quadTree; }

}
