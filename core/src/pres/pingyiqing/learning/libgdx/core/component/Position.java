package pres.pingyiqing.learning.libgdx.core.component;

import com.badlogic.ashley.core.Component;


public class Position implements Component {
    public float x;
    public float y;

    public Position() {
        this.x = 0;
        this.y = 0;
    }

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
