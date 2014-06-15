package main;

import gameplay.EnemyComponent;
import component.Component;
import component.DisplayComponent;
import component.PhysicsComponent;
import gameplay.BulletComponent;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by Edmund on 3/19/14.
 */
public class ObjectFactory {

    private ComponentFactory componentFactory;
    private Stage stage;

    public ObjectFactory(Stage stage, ComponentFactory componentFactory)
    {
        this.stage = stage;
        this.componentFactory = componentFactory;
    }

    public LinkedList<Component> makeBullet(float x, float y, float vx, float vy)
    {
        DisplayComponent d = componentFactory.displayComponent();
        d.assignTexture(stage.bullet);

        PhysicsComponent p = componentFactory.physicsComponent(x, y, vx, vy);

        BulletComponent t = componentFactory.bulletComponent();

        return assemble(d, p, t);
    }

    public LinkedList<Component> makePrimitiveEnemy()
    {
        DisplayComponent d = componentFactory.displayComponent();
        d.assignTexture(stage.tower);

        PhysicsComponent p = componentFactory.physicsComponent(
                500.0f, 500.0f, 0.0f, 0.0f);

        EnemyComponent e = componentFactory.enemyComponent();
        e.setMainFunction("enemyBehavior");

        return assemble(d, p, e);
    }

    private LinkedList<Component> assemble(Component... components)
    {
        LinkedList<Component> assembled = new LinkedList<>();
        Collections.addAll(assembled, components);
        return assembled;
    }


}
