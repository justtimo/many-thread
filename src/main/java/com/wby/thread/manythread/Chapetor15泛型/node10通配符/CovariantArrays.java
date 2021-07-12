package com.wby.thread.manythread.Chapetor15泛型.node10通配符;//: generics/CovariantArrays.java

import java.util.ArrayList;
import java.util.List;

class Jonathan extends Apple {}

public class CovariantArrays {
  public static void main(String[] args) {
    Fruit[] fruit = new Apple[10];
    fruit[0] = new Apple(); // OK
    fruit[1] = new Jonathan(); // OK
    // Runtime type is Apple[], not Fruit[] or Orange[]:
    try {
      // Compiler allows you to add Fruit:
      fruit[0] = new Fruit(); // ArrayStoreException
    } catch(Exception e) { System.out.println(e); }
    try {
      // Compiler allows you to add Oranges:
      fruit[0] = new Orange(); // ArrayStoreException
    } catch(Exception e) { System.out.println(e); }
  }
} /* Output:
java.lang.ArrayStoreException: Fruit
java.lang.ArrayStoreException: Orange
*///:~
/**
* @Description:  main()中的第一行创建了一个Apple数组，并将其赋值给一个Fruit数组引用。这是有意义的，因为Apple也是种Fruit，因此Apple素组应该也是一个Fruit数组。
 *
 * 但是，如果实际的数组类型是Apple[]，你只能在其中放置Apple或Apple的子类型，这在编译期和运行时都可以工作。
 * 但是请注意，编译器允许你将Fruit放置到这个数组中，这对于编译器来说是有意义的，因为他有一个Fruit[]引用----他有什么理由不允许将Fruit独享或任何从Fruit集成出来
 * 的对象(例如Orange)，放置到这个数组中呢？因此，编译期，这时允许的。但是运行时的数组机制知道他处理的是Apple[]，因此会在向数组中防止异构类型时抛出异常。
 *
 * 实际上，向上转型不适合用在这里。你做的是讲一个数组赋值给另一个数组。
 * 数组的行为应该是他可以持有其他对象，这里只是因为我们能够向上转型而已。所以，数组对象可以保留有关他们包含的对象类型的规则。
 * 就好像数组对他们持有的对象是有意识的，因此在编译期检查和运行时检查之间，你不能滥用他们。
 *
 * 对数组的这种赋值并不是那么可怕，因为在运行时可以发现你已经插入了不正确的类型。
 * 但是泛型的主要目标之一是将这种错误检测移入到编译期。因此当我们试图使用泛型容器来代替数组时，会发生什么呢？
*/
class NonCovariantGenerics {
  // Compile Error: incompatible types:
  //List<Fruit> flist = new ArrayList<Apple>();
} ///:~
/**
* @Description:  尽管在第一次阅读这段代码时会认为：不能将一个Apple容器赋值给一个Fruit容器。
 *  别忘了，泛型不仅和容器相关正确的说法是：不能把一个设计Apple的泛型赋给一个设计Fruit的泛型。
 *  如果就像在数组中的情况一样，编译器对代码的了解足够多，可以确定所涉及到的容器，那么他可能会留下一些余地。
 *  但是他不知道任何有关这方面的信息，因此他拒绝向上转型。
 *  然而实际上这根本不是向上转型---Apple的ListFruit的List。Apple的List将持有Apple和Apple的子类型，而Fruit的List将持有任何类型的Fruit，诚然，
 *  这包括Apple在内，但是他不是一个Apple的List，他仍旧是Fruit的List。Apple的List在类型上不等价于Fruit的List，即使Apple是Fruit类型。
 *
 *  真正的问题是我们在谈论容器的类型，而不是容器持有的类型。与数组不同，泛型没有内建的协变类型。
 *  这是因为数组在语言中是完全定义的，因此可以内建了编译期和运行时的检查，但是在使用泛型时，编译器和运行时系统都不知道你想用类型做些什么，以及应该采用
 *  什么样的规则。
 *
 *  但是有时你想要在两个类型之间建立某种类型的向上转型关系，这正是通配符所允许的：
*/
class GenericsAndCovariance {
  public static void main(String[] args) {
    // Wildcards allow covariance:
    List<? extends Fruit> flist = new ArrayList<Apple>();
    // Compile Error: can't add any type of object:
    // flist.add(new Apple());
    // flist.add(new Fruit());
    // flist.add(new Object());
    flist.add(null); // Legal but uninteresting
    // We know that it returns at least Fruit:
    Fruit f = flist.get(0);
  }
} ///:~
/**
* @Description: flist类型现在是List<? extends Fruit>，你可以将其读作“具有任何从Fruit继承的类型的列表”。
 * 但是，这并不意味着这个List将持有任何类型的Fruit。通配符引用的是明确的类型，因此它意味着“某种flist引用没有指定的具体类型”。因此这个被赋值的List
 * 必须持有诸如Fruit或Apple这样的某种指定类型，但是为了向上转型为flist，这个类型是什么并没有人关心。
 *
 * 如果唯一的限制是这个List要持有某种具体的Fruit或Fruit的子类型，但是你实际上并不关心他是什么，那么你能用这样的List做什么呢？如果不知道List持有
 * 什么类型，怎么样才能安全的向其中添加对象呢？
 * 就像在CovariantArrays.java中向上转型数组一样，不能做。除非编译器而不是运行时系统可以阻止这种操作的发生，你很快就会发现这一问题。
 *
 * 你可能会认为，事情有些极端了，因为现在你甚至不能向刚声明过将持有Apple对象的List中放置一个Apple对象了。
 * 是的，但是编译器并不知道这点。List<? extends Fruit>可以合法的指向一个List<Orange>。一旦执行这种类型的向上转型，你将丢掉向其中传递任何对象的能力，
 * 甚至是传递Object也不行。
 *
 * 另一方面，如果调用一个返回Fruit的方法，则是安全的，因为你知道在这个List中任何对象至少具有Fruit类型，因此编译器允许这么做。
 */
