package main;

import com.badlogic.gdx.math.Vector2;
import gameplay.EnemyComponent;
import gameplay.BulletComponent;
import component.DisplayComponent;
import component.PhysicsComponent;

/**
 * Created by Edmund on 3/19/14.
 */

public class ComponentFactory {

    private Stage stage;

    public ComponentFactory(Stage stage)
    {
        this.stage = stage;
    }

    public DisplayComponent displayComponent()
    {
        return new DisplayComponent(stage);
    }

    public PhysicsComponent physicsComponent(float x, float y, float vx, float vy)
    {
        PhysicsComponent p = new PhysicsComponent(stage);
        p.setVelocity(new Vector2(vx, vy));
        p.setPosition(new Vector2(x, y));
        return p;
    }

    public BulletComponent bulletComponent()
    {
        return new BulletComponent(stage);
    }

    public EnemyComponent enemyComponent()
    {
        return new EnemyComponent(stage);
    }





}
