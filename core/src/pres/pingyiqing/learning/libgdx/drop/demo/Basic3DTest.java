package pres.pingyiqing.learning.libgdx.drop.demo;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class Basic3DTest implements ApplicationListener {
    public PerspectiveCamera cam;
    public CameraInputController camController;
    public ModelBatch modelBatch;
    public Model model;
    public ModelInstance instance;
    public Environment environment;

    @Override
    public void create() {
        modelBatch = new ModelBatch();

        // 首先，我们需要添加一个摄像头，这样我们就可以从某个角度查看我们正在创建的3D场景。
        // 在这里，我们创建了一个新的PerectiveCamera，其视野为67度（这是常用的），我们将纵横比设置为当前的宽度和高度。
        // 然后我们将相机设置为右侧10个单元，向上10个单元和10个单元。
        // Z轴指向观察者，因此对于观察者来说，摄像机的正Z值正在使观察者向后移动。
        // 我们将相机设置为（0,0,0），因为这是我们放置3D对象的位置。
        // 我们设置near和far值以确保我们总能看到我们的对象。最后我们更新相机，以便我们所做的所有更改都由相机反映出来。
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(10f, 10f, 10f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        // 现在让我们添加一些我们可以实际渲染的东西。
        // 我们当然可以启动一些建模应用程序并创建一些渲染（我们很快就会实现），但是现在我们只需要按代码创建一个简单的框。
        // 这里我们实例化一个ModelBuilder，它可以用来在代码上创建模型。然后我们创建一个大小为5x5x5的简单模型框。我们还添加了一种带有绿色漫反射颜色的材质，并将位置和法线组件添加到模型中。
        // 在创建模型时，至少需要Usage.Position。Usage.Normal将正常添加到框中，因此例如照明正常工作。用法是VertexAttributes的子类。
        // 模型包含有关渲染内容的所有内容，并跟踪资源。它不包含渲染模型的位置等信息。因此，我们需要创建一个ModelInstance。ModelInstance包含模型应呈现的位置，旋转和缩放。
        // 默认情况下，这是（0,0,0），所以我们只创建一个ModelInstance，它应该在（0,0,0）处呈现。
        // 需要处理模型，因此我们在Dispose（）方法中添加了一行。
        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(5f, 5f, 5f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(model);

        // 看起来不错，但有些照明可能会有所帮助，所以让我们添加照明
        // 这里我们添加一个Environment实例。我们构造它并设置环境光（0.4,0.4,0.4），注意忽略alpha值。
        // 然后我们添加一个DirectionalLight，颜色为（0.8,0.8,0.8），方向为（-1.0，-0.8f，0.2）。
        // 我假设你一般都熟悉灯光。最后，我们在渲染实例时将环境传递给modelbatch。
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        // 看起来好多了。现在让我们控制相机，这样我们就可以从其他角度看模型了。
        // 这里我们添加一个CameraInputController，我们用cam作为参数创建。
        // 我们还将Gdx.input.setInputProcessor设置为此camController，并确保在render调用中更新它。
        // 这就是添加基本相机控制器的全部内容。您现在可以拖动以使相机旋转。
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
    }

    @Override
    public void render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // 现在让我们渲染模型实例。
        // 在这里，我们添加了ModelBatch，它负责渲染，并在create方法中初始化它。
        // 在render方法中，我们清除屏幕，调用modelBatch.begin（cam），渲染我们的ModelInstance，然后调用modelBatch.end（）来完成渲染。
        // 最后，我们需要处理modelBatch以确保所有资源（如它使用的着色器）都已正确处理。
        modelBatch.begin(cam);
        modelBatch.render(instance, environment);
        modelBatch.end();
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        model.dispose();
    }

    @Override
    public void resume() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }
}
