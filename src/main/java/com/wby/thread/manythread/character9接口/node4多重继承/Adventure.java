package com.wby.thread.manythread.character9接口.node4多重继承;//: interfaces/Adventure.java
// Multiple interfaces.

/**
 * 接口不仅仅只是一种更纯粹形式的抽象类，它的目标比这要高。因为接口是根本没有任何具体实现的——也就是说，没有任何与接口相关的存储;
 * 因此，也就无法阻止多个接口的组合。这一点是很有价值的，因为你有时需要去表示"一个x是一个a和一个b以及一个e"。
 * 在C++中，组合多个类的接口的行为被称作多重继承。它可能会使你背负很沉重的包袱，因为每个类都有一个具体实现。
 * 在Java中，你可以执行相同的行为，但是只有一个类可以有具体实现;因此，通过组合多个接口，C++中的问题是不会在Java中发生的;
 *
 * 在导出类中，不强制要求必须有一个是抽象的或"具体的"（没有任何抽象方法的）基类。如果要从一个非接口的类继承，那么只能从一个类去继承。其余的基元素都必须是接口。
 * 需要将所有的接口名都置于implements关键字之后，用逗号将它们一一隔开。可以继承任意多个接口，并可以向上转型为每个接口，因为每一个接口都是一个独立类型。
 *
 * 下面的例子展示了一个具体类组合数个接口之后产生了一个新类;
 */
interface CanFight {
  void fight();
}

interface CanSwim {
  void swim();
}

interface CanFly {
  void fly();
}

class ActionCharacter {
  public void fight() {}
}

class Hero extends ActionCharacter
    implements CanFight, CanSwim, CanFly {
  public void swim() {}
  public void fly() {}
}

public class Adventure {
  public static void t(CanFight x) { x.fight(); }
  public static void u(CanSwim x) { x.swim(); }
  public static void v(CanFly x) { x.fly(); }
  public static void w(ActionCharacter x) { x.fight(); }
  public static void main(String[] args) {
    Hero h = new Hero();
    t(h); // Treat it as a CanFight
    u(h); // Treat it as a CanSwim
    v(h); // Treat it as a CanFly
    w(h); // Treat it as an ActionCharacter
  }
} ///:~
/**
 * 可以看到，Hero组合了具体类ActionCharacter和接口CanFight、CanSwim和CanFly。当通过这种方式将一个具体类和多个接口组合到一起时，这个具体类必须放在前面，后面跟着的才是接口（否则编译器会报错）。
 *
 * 注意，CanFight接口与ActionCharacter类中的fightO方法的特征签名是一样的，而且，在 Hero中并没有提供fightO的定义。可以扩展接口，但是得到的只是另一个接口。
 * 当想要创建对象时，所有的定义首先必须都存在。即使Hero没有显式地提供fightO）的定义，其定义也因 ActionCharacter而随之而来，这样就使得创建Hero对象成为了可能。
 *
 * 在Adventure类中，可以看到有四个方法把上述各种接口和具体类作为参数。当Hero对象被创建时，它可以被传递给这些方法中的任何一个，这意味着它依次被向上转型为每一个接口。
 * 由于Java中这种设计接口的方式，使得这项工作并不需要程序员付出任何特别的努力。
 *
 * 一定要记住，前面的例子所展示的就是使用接口的核心原因∶为了能够向上转型为多个基类型（以及由此而带来的灵活性）。
 * 然而，使用接口的第二个原因却是与使用抽象基类相同∶防止客户端程序员创建该类的对象，并确保这仅仅是建立一个接口。
 * 这就带来了一个问题;我们应该使用接口还是抽象类?如果要创建不带任何方法定义和成员变量的基类，那么就应该选择接口而不是抽象类。
 * 事实上，如果知道某事物应该成为一个基类，那么第一选择应该是使它成为一个接口（该主题在本章的总结中将再次讨论）。
 */
