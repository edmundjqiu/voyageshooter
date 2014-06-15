package component;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import main.Stage;

/**
 * Created by Edmund on 3/17/14.
 */
public class DisplayComponent extends Component {
    private TextureRegion texture;
    private static final int DEFAULT_SCALE = 1;

    public DisplayComponent(Stage stage)
    {
        super(stage);
    }

    public void assignTexture(TextureRegion texture)
    {
        this.texture = texture;
    }

    public TextureRegion getTexture()
    {
        return texture;
    }

    public void update(float dt)
    {
        //Would update some animation, right?
    }

    public void draw(SpriteBatch batch)
    {
        ComponentManager comp = getStage().getComponentManager();
        PhysicsComponent physics = (PhysicsComponent)comp.
                getComponent(this, PhysicsComponent.class);

        Vector2 pos = physics.getPosition();
        float width = (float)physics.getWidth();
        float height = (float)physics.getHeight();

        batch.draw(texture,
            pos.x,
            pos.y,
            width/2,//float originX,
            height/2,//float originY,
            width,//float width,
            height,//float height,
            DEFAULT_SCALE, //float scaleX,
            DEFAULT_SCALE, //float scaleY,
            0.0f);//float rotation);

    }



}
