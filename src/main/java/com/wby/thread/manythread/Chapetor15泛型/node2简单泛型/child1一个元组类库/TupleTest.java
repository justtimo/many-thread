//: net/mindview/util/TwoTuple.java
package com.wby.thread.manythread.Chapetor15泛型.node2简单泛型.child1一个元组类库;

/**
* @Description: 仅一次方法调用就能返回多个对象，你应该进场需要这样的功能吧。
 *  可是return语句只允许返回单个对象，因此，解决方法就是创建一个对象，用它来持有想要返回的多个对象。当然，可以在每次需要的时候，专门创建一个类来完成这样
 *  工作。可是有了泛型，我们就能够一次性解决这个问题，以后再也不用在这个问题上浪费时间。同时，我们在编译器就能确保类型安全。
 *
 *  这个概念被称为元组。  它是将一组对象直接打包存储于其中的一个单一的对象。
 *  这个容器对象允许读取其中元素，但是不允许向其中存放新的对象。(这个概念也成为了 数据传送对象，或 信使)
 *
 *  通常，元组可以具有任意长度，同时，元组中的对象可以是任意不同的类型。
 *  不过我们希望能够为每一个对象指明其类型，并且从容器中读取出来时，能够得到正确类型。
 *  要处理不同长度的问题，我们需要创建多个不同的元组。
 *  下面的程序是一个二维元组，他能够有两个对象：
*/
class TwoTuple<A,B> {
  public final A first;
  public final B second;
  public TwoTuple(A a, B b) {
    first = a; second = b;
  }
  public String toString() {
    return "(" + first + ", " + second + ")";
  }
} ///:~
/**
* @Description: 构造器捕获了要存储的对象，而toString()是一个便利函数，用来显示列表中的值。
 *  注意，元组隐含的保持了其中元素的次序。
 *
 *  第一次阅读上面的代码时，你会想这不是违反了JAVA变成的安全性原则？first和second应该声明为private，然后提供getFirst()和getSecond()之类的访问方法才对啊？
 *  让我们看这个例子中的安全性：客户端程序可以读取first和second对象，然后可以随心所欲的使用这两个对象。
 *  但是他们却无法将其他值赋予first或second。因为final声明为你买了相同的安全保险，而且这种格式更简洁明了。
 *
 *  还有另一种设计考虑，即你确实希望允许客户端程序员改变first或second所引用的对象。
 *  然而，采用以上的形式无疑是更安全的做法，这样的话，如果程序员想要使用具有不同元素的元组，就强制要求他们另外创建一个新的TwoTuple对象。
 *
 *  我们可以利用继承机制实现长度更长的元组。从下面的例子可以看到，增加类型参数是简单的事情：
*/
class ThreeTuple<A,B,C> extends TwoTuple<A,B> {
  public final C third;
  public ThreeTuple(A a, B b, C c) {
    super(a, b);
    third = c;
  }
  public String toString() {
    return "(" + first + ", " + second + ", " + third +")";
  }
} ///:~

class FourTuple<A,B,C,D> extends ThreeTuple<A,B,C> {
  public final D fourth;
  public FourTuple(A a, B b, C c, D d) {
    super(a, b, c);
    fourth = d;
  }
  public String toString() {
    return "(" + first + ", " + second + ", " +
            third + ", " + fourth + ")";
  }
} ///:~

class FiveTuple<A,B,C,D,E>
        extends FourTuple<A,B,C,D> {
  public final E fifth;
  public FiveTuple(A a, B b, C c, D d, E e) {
    super(a, b, c, d);
    fifth = e;
  }
  public String toString() {
    return "(" + first + ", " + second + ", " +
            third + ", " + fourth + ", " + fifth + ")";
  }
} ///:~
/**
* @Description: 为了使用元组，你只需要定义一个长度合适的元组，将其作为方法的方绘制，将其作为方法的返回值，然后再return语句中创建该元组，并返回即可。
*/
public class TupleTest {
  static TwoTuple<String,Integer> f() {
    // Autoboxing converts the int to Integer:
    return new TwoTuple<String,Integer>("hi", 47);
  }
  static ThreeTuple<Amphibian,String,Integer> g() {
    return new ThreeTuple<Amphibian, String, Integer>(
            new Amphibian(), "hi", 47);
  }
  public static com.wby.thread.manythread.net.mindview.util.FourTuple<Vehicle, Amphibian, String, Integer> h() {
    return
            new com.wby.thread.manythread.net.mindview.util.FourTuple<Vehicle,Amphibian,String,Integer>(
                    new Vehicle(), new Amphibian(), "hi", 47);
  }
  static
  FiveTuple<Vehicle,Amphibian,String,Integer,Double> k() {
    return new
            FiveTuple<Vehicle,Amphibian,String,Integer,Double>(
            new Vehicle(), new Amphibian(), "hi", 47, 11.1);
  }
  public static void main(String[] args) {
    TwoTuple<String,Integer> ttsi = f();
    System.out.println(ttsi);
    // ttsi.first = "there"; // Compile error: final
    System.out.println(g());
    System.out.println(h());
    System.out.println(k());
  }
} /* Output: (80% match)
(hi, 47)
(Amphibian@1f6a7b9, hi, 47)
(Vehicle@35ce36, Amphibian@757aef, hi, 47)
(Vehicle@9cab16, Amphibian@1a46e30, hi, 47, 11.1)
*///:~
/**
* @Description: 由于有了泛型，你可以很容易的创建元祖，令其返回一组任意类型的对象。
 *  而你所要做的，只是编写表达式。
 *
 *  通过ttsi.first="there"语句的错误，我们可以看出，final声明缺失能够保护public元素，在对象被构造出来之后，声明为final的元素便不能被再赋予其他值了。
 *  在上面的程序中，new表达式确实有点啰嗦。本章稍后会介绍，如何利用泛型方法简化这样的表达式。
*/



















