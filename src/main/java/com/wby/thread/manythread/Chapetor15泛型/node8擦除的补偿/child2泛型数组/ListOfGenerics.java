package com.wby.thread.manythread.Chapetor15泛型.node8擦除的补偿.child2泛型数组;//: generics/ListOfGenerics.java

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
* @Description: 正如在 Erased.java中所见，不能创建范型数组。一般的解决方案是在任何想要创建泛型数组的地方都使用ArrayList
*/
public class ListOfGenerics<T> {
  private List<T> array = new ArrayList<T>();
  public void add(T item) { array.add(item); }
  public T get(int index) { return array.get(index); }
} ///:~
/**
* @Description: 这里你将获得数组的行为，以及由泛型提供的编译器的类型安全
 *
 * 有时，你仍旧向创建泛型类型的数组(例如，ArrayList内部使用的是数组)。
 * 有趣的是，可以按照编译器喜欢的方式来定义一个引用，例如：
*/
class Generic<T> {}

class ArrayOfGenericReference {
  static Generic<Integer>[] gia;
} ///:~
/**
* @Description: 编译器将接受这个程序，而不会产生任何警告。但是，永远都不能创建这个确切类型的数组(包括类型参数)，因此这有一点令人困惑。既然所有
 * 数组无论他们持有的类型如何，都具有相同的结构(每个数组槽位的尺寸和数组的布局)，那么看起来能够创建一个Object数组，并将其转型为所希望的数组类型。
 * 事实上这可以编译，但不能运行。他将产生ClassCaseException
*/
class ArrayOfGeneric {
  static final int SIZE = 100;
  static Generic<Integer>[] gia;
  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    // Compiles; produces ClassCastException:
    //! gia = (Generic<Integer>[])new Object[SIZE];
    // Runtime type is the raw (erased) type:
    gia = (Generic<Integer>[])new Generic[SIZE];
    System.out.println(gia.getClass().getSimpleName());
    gia[0] = new Generic<Integer>();
    //! gia[1] = new Object(); // Compile-time error
    // Discovers type mismatch at compile time:
    //! gia[2] = new Generic<Double>();
  }
} /* Output:
Generic[]
*///:~
/**
* @Description:  问题在于数组将跟踪他们的实际类型，而这个类型是在数组被创建时确定的，因此，即使gia已经被转型为Generic<Integer>[]，但是这个
 * 信息只存在于编译期(并且如果没有@SuppressWarnings("unchecked")注解，将得到有关这个转型的警告)。
 * 运行时，他依然是Object数组，而这将引发异常。
 * 成功创建泛型数组的唯一方式就是创建一个被擦除类型的新数组，然后对其转型。
 *
 * 看一个更复杂的例子。考虑一个简单地泛型数组包装器：
*/
class GenericArray<T> {
  private T[] array;
  @SuppressWarnings("unchecked")
  public GenericArray(int sz) {
    array = (T[])new Object[sz];
  }
  public void put(int index, T item) {
    array[index] = item;
  }
  public T get(int index) { return array[index]; }
  // Method that exposes the underlying representation:
  public T[] rep() { return array; }
  public static void main(String[] args) {
    GenericArray<Integer> gai =
            new GenericArray<Integer>(10);
    // This causes a ClassCastException:
    //! Integer[] ia = gai.rep();
    // This is OK:
    Object[] oa = gai.rep();
  }
} ///:~
/**
* @Description:  与之前相同，我们不能声明T[] array=new T[sz]，因此我们创建了一个对象数组，然后将其转型。
 * rep方法将返回T[]，它在main方法中将用于gai，因此应该是Integer[]，但是如果调用它，并尝试将结果作为Integer[]引用来捕获，就会得到
 * ClassCaseException，这还是因为实际运行时类型是Object[]。
 *
 * 如果注释掉@SuppressWarnings("unchecked")注解之后再编译GenericArray.java，编译器将会产生警告：
 *    Note: GenericArray.java uses unchecked of unsafe operations
 *    Note:Recompile with -Xlint:unchecked for details:
 * 这种情况下，我们将之获得单个警告，并且相信着事关转型。
 * 但是如果真的想要确定是否是这么回事，就应该用-Xlint:unchecked来编译
*/
///:~
/**
* @Description: 这确实是对转型的抱怨。因为警告会变得令人迷惑，所以一旦我们验证某个特定警告是可预期的，那么我们的上册就是用 @SuppressWarnings("unchecked")
 * 关闭它。通过这种方式，当警告确实出现时，我们就可以真正的展开对他的调查了。
 *
 * 因为有了擦除，数组的运行时类型就只能是Object[]。如果我们立即将其转型为T[]，那么编译期该数组的实际类型就会丢失，而编译器可能会错过某些潜在的
 * 错误检查。正因如此，最好是在集合内部使用Object[]，然后当你使用数组元素时，添加一个对T的转型。
 * 来看看如何作用于GenericArray.java示例的：
*/
class GenericArray2<T> {
  private Object[] array;
  public GenericArray2(int sz) {
    array = new Object[sz];
  }
  public void put(int index, T item) {
    array[index] = item;
  }
  @SuppressWarnings("unchecked")
  public T get(int index) { return (T)array[index]; }
  @SuppressWarnings("unchecked")
  public T[] rep() {
    return (T[])array; // Warning: unchecked cast
  }
  public static void main(String[] args) {
    GenericArray2<Integer> gai =
            new GenericArray2<Integer>(10);
    for(int i = 0; i < 10; i ++)
      gai.put(i, i);
    for(int i = 0; i < 10; i ++)
      System.out.print(gai.get(i) + " ");
    System.out.println();
    try {
      Integer[] ia = gai.rep();
    } catch(Exception e) { System.out.println(e); }
  }
} /* Output: (Sample)
0 1 2 3 4 5 6 7 8 9
java.lang.ClassCastException: [Ljava.lang.Object; cannot be cast to [Ljava.lang.Integer;
*///:~
/**
* @Description: 初看起来，这好像没多大变化，只是转型挪了地方。如果没有@SuppressWarnings("unchecked")注解，你仍旧会得到unchecked警告。
 * 但是，现在的内部表示是Object[]而不是T[]。
 * 当get方法被调用时，他将对象转型为T，这实际上是正确的类型，因此这是安全的。然而，如果你调用rep方法，他还是尝试着将Object[]转型为T[]，这仍旧是不正确的，
 * 将在编译期产生警告，在运行时产生异常。因此没有任何方式可以推翻底层的数组类型，他只能是Object[]。
 * 在内部将array当做Object[]而不是T[]处理的优势是：我们不太可能旺季这个数组的运行时类型，从而意外的引入缺陷(尽管大多数也可能是所有这类缺陷都可以在运行
 * 是快速地探测到)。
 *
 * 对于新代码，应该传递一个类型标记。在这种情况，GenericArray看起来会像下面这样：
*/
class GenericArrayWithTypeToken<T> {
  private T[] array;
  @SuppressWarnings("unchecked")
  public GenericArrayWithTypeToken(Class<T> type, int sz) {
    array = (T[]) Array.newInstance(type, sz);
  }
  public void put(int index, T item) {
    array[index] = item;
  }
  public T get(int index) { return array[index]; }
  // Expose the underlying representation:
  public T[] rep() { return array; }
  public static void main(String[] args) {
    GenericArrayWithTypeToken<Integer> gai =
            new GenericArrayWithTypeToken<Integer>(
                    Integer.class, 10);
    // This now works:
    Integer[] ia = gai.rep();
  }
} ///:~
/**
* @Description:  类型标记Class<T>被传递到构造器中，以便从擦除中恢复，使得我们可以创建需要的实际类型的数组，尽管从转型中产生的警告必须用@SuppressWarnings("unchecked")
 * 压制住。一旦我们获得了实际类型，就可以返回它，并获得想要的结果，就像在main方法看到的那样。该数组的运行时类型是确切类型T[]。
 *
 * 遗憾的是，如果查看1.5标准类库中的源代码，你就会看到从Object数组到参数化类型的转型遍及各处。
 * 例如：下面是经过整理和简化之后的从Collection中赋值ArrayList的构造器
 *  public ArrayList(Collection c){
 *      size=c.size();
 *      elementData=(E[])new Object[size];
 *      c.toArray(elementData);
 *  }
 *  如果你通读ArrayList.java，就会发现他充满了这种转型。如果我们编译它，会发生什么呢？
 *  Note: ArrayList.java uses unchecked of unsafe operations
 *  Note:Recompile with -Xlint:unchecked for details:
 *  可以肯定，标准类库会产生大量的警告。如果你曾经用过C++，特别是ANSI C之前的版本，你就会记得警告的特殊效果：当你发现可以忽略他们时，你就可以忽略。
 *  正是因为这个原因，最好从编译器中不要发出任何消息，除非程序员必须对其进行响应。
 *
 *  Neal Gafter指出，重写java类库时，他十分懒散，我们不应该想他那样。
 *  他还指出，在不破坏现有接口的情况下，他将无法修改某些java类库代码。因此，即使在java类库源码中出现了某些惯用法，也不能表示这是正确的解决之道。当
 *  查看类库代码时，你不能认为他就是应该在自己的代码中遵循的示例
*/














