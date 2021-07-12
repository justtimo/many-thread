package com.wby.thread.manythread.Chapetor15泛型.node4泛型方法;//: generics/GenericMethods.java


/**
* @Description:  到目前为止，我们看到的泛型，都是应用于整个类上。
 *  但同样可以在类中包含参数化方法，而这个方法所在的类可以是泛型类，也可以不是泛型类。也就是说，是否拥有泛型方法，
 *  与其所在的类是否是泛型没有关系。
 *
 *  泛型方法是的该方法能够独立于类而产生变化。以下是一个基本的指导原则：无论何时，只要你能做到，你就应该尽量使用泛型方法。也就是说，如果使用泛型
 *  方法可以取代将整个类泛型化，那么就应该只是用泛型方法，因为他可以使事情更加明白。
 *  另外，对于一个static的方法而言，无法访问泛型类的类型参数，所以，如果static方法需要使用泛型能力，就必须使用其称为泛型方法。
 *
 *  要定义泛型方法，只需要将泛型参数列表置于返回值之前，就像下面这样：
*/
public class GenericMethods {
  public <T> void f(T x) {
    System.out.println(x.getClass().getName());
  }
  public static void main(String[] args) {
    GenericMethods gm = new GenericMethods();
    gm.f("");
    gm.f(1);
    gm.f(1.0);
    gm.f(1.0F);
    gm.f('c');
    gm.f(gm);
  }
} /* Output:
java.lang.String
java.lang.Integer
java.lang.Double
java.lang.Float
java.lang.Character
GenericMethods
*///:~
/**
* @Description: GenericMethods并不是参数化的，尽管这个类和其内部方法可以被同时参数化，但是在这个例子中，只有方法f()拥有类型参数。
 *  这是由该方法的返回类型前面的类型参数列表指明的。
 *
 *  注意，当使用泛型类时，必须在创建对象的时候指定类型参数的值，而是用泛型方法的时候，通常不必指明参数类型，因为编译器会为我们找出具体的类型。
 *  这称为 类型参数推断 。因此，我们可以像调用普通方法一样调用f()，而且就好像是f()被无限次的重载过。他甚至可以接受GenericMethods作为其类型参数
 *
 *  如果调用f()时传入基本类型，自动打包机制就会介入其中，将基本类型的值包装为对应的对象。
 *  事实上，泛型方法与自动打包避免了许多以前我们不得不自己编写出来的代码。
*/
