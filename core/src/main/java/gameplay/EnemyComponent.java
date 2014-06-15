package gameplay;

import com.badlogic.gdx.math.Vector2;
import component.Component;
import component.ComponentManager;
import component.PhysicsComponent;
import component.ScriptingComponent;
import main.ObjectFactory;
import main.Stage;

import java.util.LinkedList;

/**
 * Created by Edmund on 3/18/14.
 */
public class EnemyComponent extends ScriptingComponent {

    public EnemyComponent(Stage stage)
    {
        super(stage);
    }

    //Expose the following to the scripting engine:
    public int getXLocation()
    {
        ComponentManager comps = this.getStage().getComponentManager();
        PhysicsComponent p = (PhysicsComponent)comps.
                getComponent(this.getObjectID(), PhysicsComponent.class);
        return (int)p.getPosition().x;

    }

    public int getYLocation()
    {
        ComponentManager comps = this.getStage().getComponentManager();
        PhysicsComponent p = (PhysicsComponent)comps.
                getComponent(this.getObjectID(), PhysicsComponent.class);
        return (int)p.getPosition().y;
    }

    /**
     * SCRIPTING ENGINE METHOD.
     * Spawn a bullet of Enemy allegiance, with a given position and velocity
     * @param x
     * @param y
     * @param vx
     * @param vy
     */
    public void spawnBullet(float x, float y, float vx, float vy)
    {
        Stage stage = getStage();
        LinkedList<Component> bullet = stage.getObjectFactory().
                makeBullet(x, y, vx, vy);
        stage.getComponentManager().packageComponents(bullet);

    }


}
