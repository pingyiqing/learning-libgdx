package pres.pingyiqing.learning.libgdx.core.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Display implements Component {
    private int type;
    public Texture texture;
    public CharSequence text;
    public float width;
    public float height;

    public Display(Texture texture) {
        this.texture = texture;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.type = 1;
    }

    public Display(Texture texture, float width, float height) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.type = 1;
    }

    public Display(CharSequence text) {
        this.text = text;
        this.type = 2;
    }

    public void render(Position position, SpriteBatch batch, BitmapFont font) {
        switch (type) {
            case 1:
                renderTexture(position, batch);
                break;
            case 2:
                renderFont(position, batch, font);
                break;
        }
    }

    private void renderTexture(Position position, SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, width, height);
    }

    private void renderFont(Position position, SpriteBatch batch, BitmapFont font) {
        font.draw(batch, text, position.x, position.y);
    }
}
