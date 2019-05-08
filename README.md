# 开发者指南

* [The Application Framework | 应用程序框架](#应用程序框架)
    + [The Life-Cycle | 生命周期](#生命周期)
    + [Modules Overview | 模块概览](#模块概览)
    + [Starter Classes and Configuration | 启动类及配置](#启动类及配置)
    + [Querying | 查询](#查询)
    + [Logging | 日志](#日志)
    + [Threading | 线程](#线程)
    + [Interfacing With Platform-Specific Code | 与特定于平台的代码的接口](#与特定于平台的代码的接口)
* [A Simple Game | 一个简单游戏](#一个简单游戏)
    + [Extending the Simple Game | 扩展简单游戏](#扩展简单游戏)
* [File Handling | 文件处理](#文件处理)
* [Networking | 联网](#联网)
* [Preferences | 首选项](#首选项)
* [Input Handling | 输入处理](#输入处理)
    + [Configuration & Querying](#配置与查询)
    + [Mouse, Touch and Keyboard](#鼠标触摸键盘)
    + [Controllers]
    + [Gesture Detection]
    + [Simple Text Input]
    + [Accelerometer]
    + [Compass]
    + [Gyroscope]
    + [Vibrator]
    + [Cursor Visibility & Catching]
    + [Back and Menu Key Catching]
    + [On-Screen Keyboard]
* [Memory Management | 内存管理](#内存管理)
* [Audio | 音频](#音频)
    + [Sound Effects]
    + [Streaming Music]
    + [Playing PCM Audio]
    + [Recording PCM Audio]
* [Graphics | 图形](#图形)
    + [Querying & configuring graphics (monitors, display modes, vsync) | 查询和配置图形（监视器、显示模式、垂直同步](#查询和配置图形)
    + [Continuous & Non-Continuous Rendering | 连续和非连续渲染](#连续和非连续渲染)
    + [Clearing the Screen | 清除屏幕](#清除屏幕)
    + [Taking a Screenshot | 截图](#截图)
    + [Profiling | 分析](#分析)
    + [Viewports | 视口](#视口)
    + [OpenGL ES Support | OpenGL_ES支持](#OpenGL_ES支持)
        - [Configuration & Querying OpenGL | 配置和查询OpenGL]
        - [Direct Access | 直接访问]
        - [Utility Classes | 实用类]
            - [Rendering Shapes | 绘制形状]
            - [Textures & TextureRegions | 纹理和纹理特征]
            - [Meshes | 网格]
            - [Shaders | 着色器]
            - [Frame Buffer Objects | 帧缓冲区对象]
    + [2D Graphics | 二维图形](#二维图形)
        - [SpriteBatch, TextureRegions, and Sprites](#纹理)
        - [2D Animation | 二维动画]
        - [Clipping, With the Use of ScissorStack | 剪切，使用剪刀堆叠]
        - [Orthographic Camera | 正交相机]
        - [Mapping Touch Coordinates | 映射触摸坐标]
        - [NinePatches]
        - [Bitmap Fonts | 位图字体]
        - [Using TextureAtlases | 使用纹理贴图集]
        - [Pixmaps | 像素图]
        - [Packing Atlases Offline]
        - [Packing Atlases at Runtime]
        - [Texture Compression | 纹理压缩]
        - [2D ParticleEffects | 二维粒子效应]
        - [Tile Maps | 瓦片地图]
        - [scene2d](#scene2d)
            - [scene2d.ui]
                - [Table]
            - [Skin]
        - [ImGui]Material
    + [3D Graphics | 三维图形](#三维图形)
        - [Quick Start | 快速开始](#快速开始)
        - [Models | 模型](#模型)
        - [Material and Environment | 材质与环境](#材质与环境)
        - [ModelBatch | 模型批处理](#模型批处理)
        - [ModelCache | 模型缓存](#模型缓存)
        - [ModelBuilder, MeshBuilder and MeshPartBuilder | 构建器](#构建器)
        - [3D Animations and Skinning | 三维动画与皮肤](#三维动画与皮肤)
        - [Importing Blender Models in libGDX | 导入Blender模型](#导入Blender模型)
        - [3D Particle Effects | 三维粒子效果](#三维粒子效果)
        - [Perspective Camera | 透视相机](#透视相机)
        - [Picking]
* [Managing Your Assets | 素材管理](#素材管理)
* [Internationalization and Localization | 国际化与本地化](#国际化与本地化)
* [Utilities | 工具类](#工具类)
    + [Reading and Writing JSON]
    + [Reading and Writing XML]
    + [Collections]
    + [Reflection]
    + [jnigen]
* [Math Utilities | 数学工具箱](#数学工具箱)
    + [Interpolation | 插值]
    + [Vectors, Matrices, Quaternions | 向量、矩阵、四元数]
    + [Circles, Planes, Rays, etc. | 圆圈，平面，划痕等等]
    + [Path Interface and Splines | 路径接口和样条曲线]
    + [Bounding Volumes | 包围盒]
    + [Intersection & Overlap Testing | 交叉与重叠测试]
* [Tools | 工具箱](#工具箱)
    + [Texture Packer | 纹理封隔器]
    + [Hiero]
    + [2D Particle Editor | 二维粒子编辑器]
    + [Path Editor | 路径编辑器]
* [Extensions | 扩展](#扩展项目)
    + [Artificial Intelligence]
    + [gdx-freetype]
    + [gdx-pay: cross-platform In-App-Purchasing API]
    + [Physics]
        - [Box2D]
        - [Bullet Physics]
* [Third Party Extensions | 第三方扩展](#第三方扩展)
    + [AdMob in libGDX]
    + [Airpush in libGDX]
    + [Swarm in libGDX]
    + [NextPeer in libGDX]
    + [Google Play Games Services in libGDX]
    + [ProGuard/DexGuard and libGDX]
    + [Excelsior JET and libGDX]
* [Coordinate systems | 坐标系统](#坐标系统)