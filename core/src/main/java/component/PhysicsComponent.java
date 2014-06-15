package component;

import com.badlogic.gdx.math.Vector2;
import main.Stage;

/**
 * The physical representation of the object in 2D space
 * Created by Edmund on 1/12/14.
 */

public class PhysicsComponent extends Component implements Rectangular {

    private QuadTreeNode quadTreeHandle;
    private Vector2 pos;
    private Vector2 velocity;
    private Vector2 orientation;
    private Vector2 baseOrientation;

    public PhysicsComponent(Stage stage)
    {
        super(stage);
        quadTreeHandle = stage.getQuadTree();
    }

    public void setPosition(Vector2 pos)
    {
        this.pos = pos;
    }

    public void setVelocity(Vector2 velocity)
    {
        this.velocity = velocity;
    }

    public void setOrientation(Vector2 orientation)
    {
        this.orientation = orientation;
    }

    public void setBaseOrientation(Vector2 baseOrientation)
    {
        this.baseOrientation = baseOrientation;
    }

    public Vector2 getPosition()
    {
        return pos;
    }

    public Vector2 getVelocity()
    {
        return velocity;
    }

    public Vector2 getOrientation() { return orientation; }

    public Vector2 getBaseOrientation() { return baseOrientation; }

    public String toString()
    {
        return "(" + pos.x + ", " + pos.y + ")";
    }

    public void update(float dt)
    {

//        long before = System.nanoTime();
        quadTreeHandle.remove(this);
//        long removeTime = System.nanoTime() - before;
        pos.add(velocity.x * dt, velocity.y * dt);
//        before = System.nanoTime();
        quadTreeHandle.add(this);
//        long addTime = System.nanoTime() - before;

//        System.out.println("Takes " + removeTime + " to remove " );
//        System.out.println("and " + addTime + " to add" );
    }

    /**
     * Have to override to add a removal from the QuadTree
     */
    public void markDead()
    {
        super.markDead();
        quadTreeHandle.remove(this);
    }


    //The following methods are necessary for QuadTree

    //Oh shoot, forgot to consider non-point particles.
    //Uh. Need another data structure for that, for now
    //just have a dummy "50"

    public double getWidth()
    {
        return 50.0d;
    }

    public double getHeight()
    {
        return 50.0d;
    }

    public double getX()
    {
        return getPosition().x;
    }

    public double getY()
    {
        return getPosition().y;
    }

}