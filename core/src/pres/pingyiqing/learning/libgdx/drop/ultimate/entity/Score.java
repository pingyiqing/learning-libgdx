package pres.pingyiqing.learning.libgdx.drop.ultimate.entity;

import com.badlogic.ashley.core.Entity;
import pres.pingyiqing.learning.libgdx.core.component.Display;
import pres.pingyiqing.learning.libgdx.core.component.Position;

public class Score extends Entity {
    private int score;
    public Position position;
    public Display display;

    public Score(int score, Position position) {
        this.score = score;
        this.position = position;
        this.display = new Display(text());
        add(position).add(display);
    }

    public void addScore() {
        score++;
        display.text = text();
    }

    public String text() {
        return "Drops Collected: " + score;
    }
}
