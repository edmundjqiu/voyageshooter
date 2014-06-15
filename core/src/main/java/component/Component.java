package component;

import main.Stage;

/**
 * Created by Edmund on 3/17/14.
 */
public abstract class Component {
    /** ID of the object it belongs to */
    private int objectID;

    /** */
    private boolean alive = true;

    /** Handle on the stage */
    private Stage stage;

    public Component(Stage stage)
    {
        this.stage = stage;
    }

    /**
     * Mark every Component in object as dead.
     */
    public void gameObjectDeath()
    {
        stage.getComponentManager().markGameObjectDeath(objectID);
    }

    public void markDead()
    {
        this.alive = false;
    }

    public Stage getStage() { return stage; }
    public boolean isAlive() { return alive; }
    public int getObjectID() { return objectID; }
    public void setObjectID(int objectID) { this.objectID = objectID; }
    public abstract void update(float dt);

}
