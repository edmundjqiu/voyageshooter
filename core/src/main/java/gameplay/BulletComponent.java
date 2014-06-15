package gameplay;

import component.ComponentManager;
import component.PhysicsComponent;
import component.ScriptingComponent;
import main.Stage;

/**
 * Created by Edmund on 3/17/14.
 */
public class BulletComponent extends ScriptingComponent {

    public BulletComponent(Stage stage)
    {
        super(stage);
    }

    public void update(float dt)
    {
        //Check if out of bounds
        int xLoc = getXLocation();
        int yLoc = getYLocation();
        if ((xLoc > 1200 || xLoc < -50) ||
                (yLoc > 830 || yLoc < -50))
        {
            this.gameObjectDeath();
        }
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


}
