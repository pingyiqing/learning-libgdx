package pres.pingyiqing.learning.libgdx.core.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import pres.pingyiqing.learning.libgdx.core.component.Position;
import pres.pingyiqing.learning.libgdx.core.component.Velocity;

import java.util.Iterator;

public class MovementSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<Position> pm = ComponentMapper.getFor(Position.class);
    private ComponentMapper<Velocity> vm = ComponentMapper.getFor(Velocity.class);

    public MovementSystem() {
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(Position.class, Velocity.class).get());
    }

    @Override
    public void update(float deltaTime) {
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity entity = it.next();
            Position position = pm.get(entity);
            Velocity velocity = vm.get(entity);
            position.x += velocity.x * deltaTime;
            position.y += velocity.y * deltaTime;
        }
    }
}
