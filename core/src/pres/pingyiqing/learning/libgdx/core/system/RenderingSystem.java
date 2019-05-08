package pres.pingyiqing.learning.libgdx.core.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pres.pingyiqing.learning.libgdx.core.component.Display;
import pres.pingyiqing.learning.libgdx.core.component.Position;

/**
 * @Description:
 * @Author: Ping Yiqing
 * @Date: 2018/12/26
 * @Version: V1.0
 * @Modified By:
 * @Modified Date:
 * @Why:
 */
public class RenderingSystem extends EntitySystem {
    private SpriteBatch batch;
    private BitmapFont font;
    public OrthographicCamera camera;

    private ImmutableArray<Entity> entities;

    private ComponentMapper<Position> pm = ComponentMapper.getFor(Position.class);
    private ComponentMapper<Display> dm = ComponentMapper.getFor(Display.class);

    public RenderingSystem(SpriteBatch batch, BitmapFont font) {
        this.batch = batch;
        this.font = font;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(Position.class, Display.class).get());
    }

    @Override
    public void update(float deltaTime) {
        // 1. 用深蓝色来清除屏幕
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // 2. 在我们的简单例子中，我们不改变相机的属性，但是每帧更新一次相机通常是一个好习惯
        camera.update();
        // 3. 让精灵批处理用相机的坐标系渲染
        batch.setProjectionMatrix(camera.combined);
        // 4. 每帧开启一个新的批处理来绘制
        batch.begin();

        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            Position position = pm.get(entity);
            Display display = dm.get(entity);
            display.render(position, batch, font);
        }
        batch.end();
    }
}
