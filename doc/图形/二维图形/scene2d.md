## Overview 概览

scene2d是一个2D场景图，用于使用actor的层次结构构建应用程序和UI。如果您正在寻找scene2d的UI组件，请参阅Scene2d.ui

它提供以下功能：

- group的旋转和缩放适用于所有子actor。子actor始终在自己的坐标系中工作，父变换是透明应用的。
- 通过SpriteBatch简化2D绘图。每个actor都绘制自己的未旋转和未缩放的坐标系，其中0,0是actor的左下角。
- 点击旋转和缩放演员的检测。每个actor使用其自己的未旋转和未缩放的坐标系来确定它是否被击中。
- 将输入和其他事件路由到适当的actor。事件系统是灵活的，使父级参与者能够处理子级之前或之后的事件。
- 随着时间的推移易于操纵actor的动作系统。可以将动作链接并组合以实现复杂效果。

scene2d非常适合布局，绘制和处理游戏菜单，HUD叠加，工具和其他UI的输入。该scene2d.ui包提供了很多actor和其他实用程序专门用于构建UI。

场景图具有它们耦合模型和视图的缺点。演员存储通常被认为是游戏中的模型数据的数据，例如它们的大小和位置。演员也是观点，因为他们知道如何画自己。这种耦合使得MVC分离变得困难。当仅用于UI或不关心MVC的应用程序时，耦合不是问题。

scene2d的核心有三个类：

- Actor类是图中的一个节点，它具有位置，矩形大小，原点，比例，旋转和颜色。
- Group类是一个可能有子actor的actor。
- Stage类有一个摄像头，SpriteBatch和一个root group，并处理绘制actor和分配输入事件。

### Stage 舞台

Stage是一个InputProcessor。当它接收输入事件时，它会在适当的actor上触发它们。如果将舞台用作其他内容（例如，HUD）之上的UI，则可以使用InputMultiplexer首先为舞台提供处理事件的机会。如果舞台中的actor处理事件，则stage的InputProcessor方法将返回true，表示事件已被处理，不应继续到下一个InputProcessor。

Stage有一个act方法，它从上一帧开始占用增量时间。这会导致act调用场景中每个actor 的方法，允许actor根据时间采取一些操作。默认情况下，Actor act方法更新actor上的所有操作。调用act舞台是可选的，但如果省略，则不会发生演员动作和进入/退出事件。

### Viewport 视口

舞台的视口由Viewport实例确定。视口管理Camera和控制舞台在屏幕上的显示方式，舞台的宽高比（是否拉伸）以及是否出现黑条（信箱）。视口还将屏幕坐标转换为舞台坐标和从舞台坐标转换屏幕坐

视口在舞台构造函数中指定或使用setViewport。如果在可以调整应用程序窗口大小的位置运行（例如，在桌面上），则应在调整应用程序窗口大小时设置阶段的视口。

这是一个没有actor的最基本的scene2d应用程序的例子，使用了ScreenViewport。使用此视口，舞台中的每个单元对应于1个像素。这意味着舞台永远不会拉伸，但根据屏幕或窗口的大小，可以看到或多或少的舞台。这通常对UI应用程序有用。

```
private Stage stage;

public void create () {
	stage = new Stage(new ScreenViewport());
	Gdx.input.setInputProcessor(stage);
}

public void resize (int width, int height) {
	// See below for what true means.
	stage.getViewport().update(width, height, true);
}

public void render () {
	float delta = Gdx.graphics.getDeltaTime();
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	stage.act(delta);
	stage.draw();
}

public void dispose () {
	stage.dispose();
}
```

更新视口时传递true会更改摄像机位置，使其在舞台上居中，使左下角为0,0。这对于通常不会更改摄像机位置的UI非常有用。自己管理摄像机位置时，传递false或省略布尔值。如果未设置舞台位置，默认情况下0,0将位于屏幕的中央。

这是一个使用的例子StretchViewport。舞台的640x480尺寸将拉伸至屏幕尺寸，可能会改变舞台的纵横比。

```
stage = new Stage(new StretchViewport(640, 480));
```

这是一个使用的例子FitViewport。舞台的640x480尺寸可以缩放以适应屏幕而不改变宽高比，然后在任一侧添加黑条以占据剩余空间（letterboxing）。

```
stage = new Stage(new FitViewport(640, 480));
```

这是一个使用的例子ExtendViewport。舞台的尺寸640x480首先缩放以适应而不改变宽高比，然后增加舞台的较短尺寸以填充屏幕。纵横比没有改变，并且没有黑条，但是在一个方向上阶段可能更长。

```
stage = new Stage(new ExtendViewport(640, 480));
```

以下是使用ExtendViewport最大尺寸的示例。和以前一样，640x480的舞台尺寸首先缩放到适合而不改变宽高比，然后增加舞台的较短尺寸以填充屏幕。但是，舞台的尺寸不会超过最大尺寸800x480。这种方法允许您显示更多的世界，以支持许多不同的宽高比，而不显示黑条。

```
// Set the viewport to the whole screen.
Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

// Draw anywhere on the screen.

// Restore the stage's viewport.
stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
```

显示黑条的大多数视口都使用glViewport，因此舞台无法在黑条内绘制。该glViewport可设置为全屏显示在舞台外的黑边画画。

## Drawing 绘图

当draw在舞台上调用时，它会调用绘制舞台中的每个actor。draw可以重写Actor的方法来执行绘图：

```
public class MyActor extends Actor {
	TextureRegion region;

	public MyActor () {
		region = new TextureRegion(...);
                setBounds(region.getX(), region.getY(), 
			region.getWidth(), region.getHeight());
	}

	@Override
	public void draw (Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
			getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
}
```

```
private ShapeRenderer renderer = new ShapeRenderer();

public void draw (Batch batch, float parentAlpha) {
	batch.end();

	renderer.setProjectionMatrix(batch.getProjectionMatrix());
	renderer.setTransformMatrix(batch.getTransformMatrix());
	renderer.translate(getX(), getY(), 0);

	renderer.begin(ShapeType.Filled);
	renderer.setColor(Color.BLUE);
	renderer.rect(0, 0, getWidth(), getHeight());
	renderer.end();

	batch.begin();
}
```

### Group transform

## Hit detection 命中检测

```
public Actor hit (float x, float y, boolean touchable) {
	if (touchable && getTouchable() != Touchable.enabled) return null;
	return x >= 0 && x < width && y >= 0 && y < height ? this : null;
}
```
## Event system 事件系统

### InputListener 输入监听器

```
actor.setBounds(0, 0, texture.getWidth(), texture.getHeight());

actor.addListener(new InputListener() {
	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		System.out.println("down");
		return true;
	}
	
	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		System.out.println("up");
	}
});
```

### Other listeners 其他监听器

```
actor.addListener(new ActorGestureListener() {
	public boolean longPress (Actor actor, float x, float y) {
		System.out.println("long press " + x + ", " + y);
		return true;
	}

	public void fling (InputEvent event, float velocityX, float velocityY, int button) {
		System.out.println("fling " + velocityX + ", " + velocityY);
	}

	public void zoom (InputEvent event, float initialDistance, float distance) {
		System.out.println("zoom " + initialDistance + ", " + distance);
	}
});
```

## Actions

```
MoveToAction action = new MoveToAction();
action.setPosition(x, y);
action.setDuration(duration);
actor.addAction(action);
```

### Action pooling

```
Pool<MoveToAction> pool = new Pool<MoveToAction>() {
	protected MoveToAction newObject () {
		return new MoveToAction();
	}
};
MoveToAction action = pool.obtain();
action.setPool(pool);
action.setPosition(x, y);
action.setDuration(duration);
actor.addAction(action);
```

```
MoveToAction action = Actions.action(MoveToAction.class);
action.setPosition(x, y);
action.setDuration(duration);
actor.addAction(action);
```

```
actor.addAction(Actions.moveTo(x, y, duration));
```

```
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
...
actor.addAction(moveTo(x, y, duration));
```

### Complex actions

```
actor.addAction(sequence(moveTo(200, 100, 2), color(Color.RED, 6), delay(0.5f), rotateTo(180, 5)));
```

### Action completion

```
actor.addAction(sequence(fadeIn(2), run(new RunnableAction() {
	public void run () {
		System.out.println("Action complete!");
	}
})));
```

### Interpolation 插值

```
MoveToAction action = Actions.action(MoveToAction.class);
action.setPosition(x, y);
action.setDuration(duration);
action.setInterpolation(Interpolation.bounceOut);
actor.addAction(action);
```

```
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.math.Interpolation.*;
...
actor.addAction(parallel(moveTo(250, 250, 2, bounceOut), color(Color.RED, 6), delay(0.5f), rotateTo(180, 5, swing)));
actor.addAction(forever(sequence(scaleTo(2, 2, 0.5f), scaleTo(1, 1, 0.5f), delay(0.5f))));
```