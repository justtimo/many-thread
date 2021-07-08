package com.wby.thread.manythread.charector14类型信息.node6反射运行时的类信息;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/7 17:05
 * @Description:    如果不知道某个对象的确切类型，RTTI可以告诉你。但是有一个限制：这个类型在编译时必须已知，这样才能使用RTTI识别它，并利用这些信息做一些有用的事。换句话说，编译时编译器必须知道所有要通过RTTI来处理的类
 *
 *  初看起来这不是一个限制，但是假设你获取了一个指向某个并不在你程序空间中的对象引用，在编译时你的程序无法获知这个对象所属的类
 *  既然这个类在编译器为你的程序生成代码之后很久才出现，那么怎样才能使用这样的类呢？
 *
 *  传统的编程环境中不太可能出现这种情况，但是更大的编程世界中，在许多重大的情况下就会发生上面的事情。
 *  首先就是“基于构件的编程”，在此种编程中，将使用某种基于 快速应用开发（RAD） 的应用构建工具，即 集成开发环境(IDE)，来构建项目。这是一种可视化编程的方式，可以通过将代表不同组件的
 *  图标拖曳到表单中来创建程序。然后在编程时通过设置构件的属性值来配置他们。
 *  这种设计时的设置，要求构件可实例化，并且要暴露其部分信息，允许程序员读取和修改构件的属性。此外GUI事件的构件还要求暴露相关方法。
 *
 *  反射提供了一种机制---用来检查可用的方法，并返回方法名。
 *  java通过javaBean(22章会讲到)提供了基于构件的编程架构
 *
 *  人们想要在运行时获取类信息的另个一动机是，希望提供在跨网络的远程平台上创建和运行对象的能力。
 *  这被称为 远程方法调用(RMI) ，它允许java程序将对象分布到多台机器上。
 *
 *  需要这种分布能力是有很多原因的，
 *  例如，执行需要大量运算的任务，为了提高速度，将计算划分为许多小的计算单元，分布到空闲的机器上运行。
 *  又比如，希望将处理特定类型任务的代码(例如多层CS架构中的“业务规则”)，置于特定的机器上，让这台机器称为描述这些动作的公共场所，可以方便的通过改动他就实现影响系统中所有人的效果。(这是有趣的开发方式，因为及其的存在仅仅为了方便软件改动)
 *  同时，分布式计算也支持适用于执行特殊任务的专用硬件，例如矩阵转置，而这对于通用程序来说就太过昂贵
 *
 *  Class类库和reflect类库一起对 反射 的概念进行了支持，该类库包括Field、Method以及Constructor类(每个类都实现了Member接口)
 *  这些类型的对象是由JVM运行时创建的，用以表示未知类里对应的成员。这样你就可以使用Constractor创建新的对象，用get()和set()方法读取和修改与Field对象关联的字段，用invoke方法调用与Method对象关联的方法。
 *  另外，还可以调用getField()、getMethod()、getConstructor()等很便利的方法，以返回字段、方法以及构造器的对象数组。
 *  这样，匿名对象的类信息就能在运行时被完全确定下来，而在编译时不需要知道任何事情。
 *
 *  重要的是，要认识到反射机制并没有什么神奇之处。
 *  当通过反射与一个未知类型的对象打交道时，JVM只是简单地检查这个对象，对于他属于哪个特定的类(就像RTTI一样).
 *  在用它做其他事情之前必须先加载那个类的Class对象。因此，哪个类的.class文件对于JVM来说必须是可获取的(要么在本地，要么通过网络可以获取)
 *
 *  所以，RTTI和反射之间真正的区别是：
 *      RTTI来说，编译器在编译时打开和检查.class文件。(换句话说，我们可以用“普通”方式调用对象的所有方法)
 *      对反射来说，.class文件在编译时是不可获取的，所以是在运行时打开和检查.class文件。
 */
public class Text {
}
