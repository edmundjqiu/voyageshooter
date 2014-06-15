package action;

import com.badlogic.gdx.math.Vector2;
import component.Component;
import component.PhysicsComponent;
import main.Stage;
import org.luaj.vm2.LuaThread;

/**
 * Created by Edmund on 3/18/14.
 */
public class MoveAction extends Action {
    private PhysicsComponent actorPhysics;
    private float distance;
    private float elapsedDistance;
    private Vector2 direction;
    private float speed;

    public MoveAction(LuaThread callback, Component actor,
        float distance, float speed, float xDirection, float yDirection)
    {
        super(callback);
        actorPhysics = getActorPhysics(actor);
        this.distance = distance;
        this.speed = speed;
        direction = new Vector2(xDirection, yDirection);
        direction.nor();

    }

    public MoveAction(Component actor, float distance,
        float speed, float xDirection, float yDirection)
    {
        actorPhysics = getActorPhysics(actor);
        this.distance = distance;
        this.speed = speed;
        direction = new Vector2(xDirection, yDirection);
        direction.nor();
    }

    private PhysicsComponent getActorPhysics(Component actor)
    {
        Stage s = actor.getStage();
        return (PhysicsComponent)s.getComponentManager().
                getComponent(actor, PhysicsComponent.class);
    }

    public void onStart()
    {
        elapsedDistance = 0;
    }

    public void update(float dt)
    {
        Vector2 pos = actorPhysics.getPosition();
        float ds = dt * speed;
        pos.add(ds * direction.x, ds * direction.y);
        elapsedDistance += ds;
        if (elapsedDistance >= distance)
        {
            this.markActionComplete();
        }
    }


}
