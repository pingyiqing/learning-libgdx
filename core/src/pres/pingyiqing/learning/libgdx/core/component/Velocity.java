package pres.pingyiqing.learning.libgdx.core.component;

import com.badlogic.ashley.core.Component;


public class Velocity implements Component {
    public float x;
    public float y;

    public Velocity() {
        this.x = 0;
        this.y = 0;
    }

    public Velocity(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
