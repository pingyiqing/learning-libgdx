package pres.pingyiqing.learning.libgdx.drop.extend;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * 雨滴小游戏 扩展版
 */
public class ExtendedDrop extends Game {
    float windowWidth;
    float windowHeight;
    SpriteBatch batch;
    BitmapFont font;

    public ExtendedDrop(int width, int height) {
        this.windowWidth = width >= 800 ? width : 800;
        this.windowHeight = height >= 600 ? height : 600;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new ExtendedDropMenuScreen(this));
    }

    @Override
    public void render() {
        super.render(); //important!
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
