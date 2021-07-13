package com.wby.thread.manythread.Chapetor15泛型.node10通配符.child3无界通配符;//: generics/UnboundedWildcards1.java

import com.wby.thread.manythread.Chapetor15泛型.node10通配符.child1编译器有多聪明.Holder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Description:  无界通配符<?>看起来意味着“任何事物”，因此使用无界通配符好像等价于使用原生类型。
 * 事实上，编译器初看起来是支持这种判断的：
*/
public class UnboundedWildcards1 {
  static List list1;
  static List<?> list2;
  static List<? extends Object> list3;
  static void assign1(List list) {
    list1 = list;
    list2 = list;
    // list3 = list; // Warning: unchecked conversion
    // Found: List, Required: List<? extends Object>
  }
  static void assign2(List<?> list) {
    list1 = list;
    list2 = list;
    list3 = list;
  }
  static void assign3(List<? extends Object> list) {
    list1 = list;
    list2 = list;
    list3 = list;
  }
  public static void main(String[] args) {
    assign1(new ArrayList());
    assign2(new ArrayList());
    // assign3(new ArrayList()); // Warning:
    // Unchecked conversion. Found: ArrayList
    // Required: List<? extends Object>
    assign1(new ArrayList<String>());
    assign2(new ArrayList<String>());
    assign3(new ArrayList<String>());
    // Both forms are acceptable as List<?>:
    List<?> wildList = new ArrayList();
    wildList = new ArrayList<String>();
    assign1(wildList);
    assign2(wildList);
    assign3(wildList);
  }
} ///:~
/**
* @Description:  很多情况都和这里看到的情况类似，即编译器很少关心使用的是原生类型还是<?>。
 * 这些情况中，<?>可以认为是一种装饰，但他仍旧是有价值的，因为他是在声明：我是想用JAVA的泛型编写这段代码，我在这里并不是要用原生类型，但是当前情况下，泛型参数可以持有任何类型
 *
 * 第二个例子展示了无界通配符的一个重要应用。
 * 当你处理多个泛型参数时，有时允许一个参数是任意类型，同时为其他参数确定某种特定类型的这种能力显得很重要：
*/
class UnboundedWildcards2 {
  static Map map1;
  static Map<?,?> map2;
  static Map<String,?> map3;
  static void assign1(Map map) { map1 = map; }
  static void assign2(Map<?,?> map) { map2 = map; }
  static void assign3(Map<String,?> map) { map3 = map; }
  public static void main(String[] args) {
    assign1(new HashMap());
    assign2(new HashMap());
    // assign3(new HashMap()); // Warning:
    // Unchecked conversion. Found: HashMap
    // Required: Map<String,?>
    assign1(new HashMap<String,Integer>());
    assign2(new HashMap<String,Integer>());
    assign3(new HashMap<String,Integer>());
  }
} ///:~
/**
* @Description: 但是当你拥有的全都是无界通配符时，就像在Map<?,?>中看到的那样，编译器看起来就无法将其与原生Map区分开了。
 * 另外，UnboundedWildcards1.java展示了编译器处理List<?>和List<? extends Object>时是不同的
 *
 * 令人困惑的事，编译器并非总是关注像List和List<?>之间的这种差异，因此他们看起来就像是相同的事物。
 * 事实上，由于泛型参数将擦除到他的第一个边界，因此List<?>看起来等价于List<Object>，而List实际上也是List<Object>----除非这些语句都不为真。
 * List实际上表示：持有任何Object类型的原生List，而List<?>表示：具有某种特定类型的非原生List，只是我们不知道那种类型是什么
 *
 * 编译器何时才会关注原生类型和涉及无界通配符的类型之间的差异呢？
 * 下面的例子使用了前面定义的Holder<T>，它包含接受Holder作为参数的各种方法，但是它们具有不同的形式：作为原生类型，具有具体的类型参数以及具有无界通配符参数：
 */
class Wildcards {
  // Raw argument:
  static void rawArgs(Holder holder, Object arg) {
    // holder.set(arg); // Warning:
    //   Unchecked call to set(T) as a
    //   member of the raw type Holder
    // holder.set(new Wildcards()); // Same warning

    // Can't do this; don't have any 'T':
    // T t = holder.get();

    // OK, but type information has been lost:
    Object obj = holder.get();
  }
  // Similar to rawArgs(), but errors instead of warnings:
  static void unboundedArg(Holder<?> holder, Object arg) {
    // holder.set(arg); // Error:
    //   set(capture of ?) in Holder<capture of ?>
    //   cannot be applied to (Object)
    // holder.set(new Wildcards()); // Same error

    // Can't do this; don't have any 'T':
    // T t = holder.get();

    // OK, but type information has been lost:
    Object obj = holder.get();
  }
  static <T> T exact1(Holder<T> holder) {
    T t = holder.get();
    return t;
  }
  static <T> T exact2(Holder<T> holder, T arg) {
    holder.set(arg);
    T t = holder.get();
    return t;
  }
  static <T>
  T wildSubtype(Holder<? extends T> holder, T arg) {
    // holder.set(arg); // Error:
    //   set(capture of ? extends T) in
    //   Holder<capture of ? extends T>
    //   cannot be applied to (T)
    T t = holder.get();
    return t;
  }
  static <T>
  void wildSupertype(Holder<? super T> holder, T arg) {
    holder.set(arg);
    // T t = holder.get();  // Error:
    //   Incompatible types: found Object, required T

    // OK, but type information has been lost:
    Object obj = holder.get();
  }
  public static void main(String[] args) {
    Holder raw = new Holder<Long>();
    // Or:
    raw = new Holder();
    Holder<Long> qualified = new Holder<Long>();
    Holder<?> unbounded = new Holder<Long>();
    Holder<? extends Long> bounded = new Holder<Long>();
    Long lng = 1L;

    rawArgs(raw, lng);
    rawArgs(qualified, lng);
    rawArgs(unbounded, lng);
    rawArgs(bounded, lng);

    unboundedArg(raw, lng);
    unboundedArg(qualified, lng);
    unboundedArg(unbounded, lng);
    unboundedArg(bounded, lng);

    // Object r1 = exact1(raw); // Warnings:
    //   Unchecked conversion from Holder to Holder<T>
    //   Unchecked method invocation: exact1(Holder<T>)
    //   is applied to (Holder)
    Long r2 = exact1(qualified);
    Object r3 = exact1(unbounded); // Must return Object
    Long r4 = exact1(bounded);

    // Long r5 = exact2(raw, lng); // Warnings:
    //   Unchecked conversion from Holder to Holder<Long>
    //   Unchecked method invocation: exact2(Holder<T>,T)
    //   is applied to (Holder,Long)
    Long r6 = exact2(qualified, lng);
    // Long r7 = exact2(unbounded, lng); // Error:
    //   exact2(Holder<T>,T) cannot be applied to
    //   (Holder<capture of ?>,Long)
    // Long r8 = exact2(bounded, lng); // Error:
    //   exact2(Holder<T>,T) cannot be applied
    //   to (Holder<capture of ? extends Long>,Long)

    // Long r9 = wildSubtype(raw, lng); // Warnings:
    //   Unchecked conversion from Holder
    //   to Holder<? extends Long>
    //   Unchecked method invocation:
    //   wildSubtype(Holder<? extends T>,T) is
    //   applied to (Holder,Long)
    Long r10 = wildSubtype(qualified, lng);
    // OK, but can only return Object:
    Object r11 = wildSubtype(unbounded, lng);
    Long r12 = wildSubtype(bounded, lng);

    // wildSupertype(raw, lng); // Warnings:
    //   Unchecked conversion from Holder
    //   to Holder<? super Long>
    //   Unchecked method invocation:
    //   wildSupertype(Holder<? super T>,T)
    //   is applied to (Holder,Long)
    wildSupertype(qualified, lng);
    // wildSupertype(unbounded, lng); // Error:
    //   wildSupertype(Holder<? super T>,T) cannot be
    //   applied to (Holder<capture of ?>,Long)
    // wildSupertype(bounded, lng); // Error:
    //   wildSupertype(Holder<? super T>,T) cannot be
    //  applied to (Holder<capture of ? extends Long>,Long)
  }
} ///:~
/**
* @Description:  在rawArgs中，编译器知道Holder是一个泛型类型因此即使它在这里被表示成一个原生类型，编译器仍旧知道向set方法传递一个Object是不安全的。
 * 由于他是原生类型，你可以将任何类型的对象传递给set方法，而这个对象将被向上转型为Object。因此，只要使用了原生类型，都会放弃编译期检查。对get调用说
 * 明了相同的问题：没有任何T类型的对象，因此结果只能是一个Object。
 *
 * 人们很自然的会考虑原生Holder和Holder<?>是大致相同的事物。但是unboundedArg方法强调他们是不同的----他揭示了相同的问题，但是他将这些问题作为错误
 * 而不是警告报告，因为原生Holder将持有任何类型的组合，而Holder<?>将持有具有某种具体类型的统购集合，因此不能只是向其中传递Object。
 *
 * 在exact1和exact2中，可以看到使用了确切的泛型参数---没有任何通配符。将看到，exact2和exact1具有不同的限制，因为他有额外的参数。
 *
 * 在wildSubtype中，在holder类型上的限制被放松为持有任何扩展自T的对象的Holder。这还意味着如果T是Fruit，那么Holder和意识Holder<Apple>，
 * 这是合法的。为了防止将Orange放置到Holder<Apple>中，对set的调用(或者对任何接受这个类型参数为参数的方法的调用)都是不允许的。
 * 但是，你仍然知道任何来自Holder<? extends Fruit>的对象至少是Fruit，因此get(或任何将产生具有这个类型参数的返回值的方法)都是允许的。
 *
 * wildSuperType展示了超类型通配符，这个方法展示了与wildSubType相反的行为：holder可以是持有任何T的基类型的容器。因此，set可以接受T，因为任何
 * 可以工作与基类的对象都可以多态的作用于导出类(这里就是T)。但是，尝试着调用get是没用的，因为有holder持有的类型可以是人和超类型，因此唯一安全的
 * 类型就是Object。
 *
 * 这个示例还展示了对于在unbounded中使用无界通配符能够做什么不能做什么所做出的限制。
 * 对于迁移兼容性，rawArgs将接受所有Holder的不同变体，而不会产生警告。unboundedArgs方法也可以接受相同的所有类型，尽管如前所述，它在方法体内部
 * 处理这些类型的方式并不相同。
 *
 * 如果向接受确切泛型类型(没有通配符)的方法传递一个原生的Holder应用，就会得到一个警告，因为确切的参数期望得到在原生类型中并不存在的信息。如果向exact1
 * 传递一个无界引用，就不会有任何可以确定返回类型的类型信息。
 *
 * 可以看到，exact2具有最多的限制，因为他希望精确地得到一个Holder<T>，以及一个具有类型T的参数，因此，他将产生错误或警告，除非提供确切的参数。有时，这样做
 * 很好，但是如果他过于受限，那么就可以使用通配符，这取决于是否想要从泛型参数中返回类型确定的返回值(就像在wildSubtype中看到的那样)，或者是否想要向泛型参数传递
 * 类型确定的参数（就像wildSuperType中那样）
 *
 * 因此，使用确切类型替代通配符的好处是，可以用泛型参数做更多的事，但是使用通配符使得你必须接受范围更宽的参数化类型作为参数。
 * 因此，必须做个情况的权衡利弊，找到更适合你的需求的方法
*/





