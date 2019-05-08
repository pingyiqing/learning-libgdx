package pres.pingyiqing.learning.libgdx.drop.ultimate.entity;

import com.badlogic.ashley.core.Entity;
import pres.pingyiqing.learning.libgdx.core.component.Display;
import pres.pingyiqing.learning.libgdx.core.component.Position;
import pres.pingyiqing.learning.libgdx.core.component.Velocity;

public class Bucket extends Entity {

    public Position position;
    public Velocity velocity;
    public Display display;

    public Bucket(Position position, Velocity velocity, Display display) {
        super();
        this.position = position;
        this.velocity = velocity;
        this.display = display;
        add(position).add(velocity).add(display);
    }
}
