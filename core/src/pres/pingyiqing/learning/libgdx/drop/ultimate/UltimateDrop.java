package pres.pingyiqing.learning.libgdx.drop.ultimate;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pres.pingyiqing.learning.libgdx.core.system.MovementSystem;
import pres.pingyiqing.learning.libgdx.core.system.RenderingSystem;

/**
 * 雨滴小游戏 极致版
 */
public class UltimateDrop extends Game {
    float windowWidth;
    float windowHeight;
    Engine engine;
    AssetManager assetManager;
    SpriteBatch batch;
    BitmapFont font;

    public UltimateDrop(int width, int height) {
        this.windowWidth = width >= 800 ? width : 800;
        this.windowHeight = height >= 600 ? height : 600;
    }

    @Override
    public void create() {
        //初始化游戏引擎
        engine = new Engine();
        //初始化资源管理器
        assetManager = new AssetManager();
        //渲染系统
        batch = new SpriteBatch();
        font = new BitmapFont();
        engine.addSystem(new RenderingSystem(batch, font));
        //移动系统
        engine.addSystem(new MovementSystem());

        this.setScreen(new UltimateDropLoadingScreen(this));
    }

    public void render() {
        super.render(); //important!
    }

    public void dispose() {
        assetManager.dispose();
        batch.dispose();
        font.dispose();
    }
}
