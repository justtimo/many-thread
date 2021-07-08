package com.wby.thread.manythread.charector14类型信息.node2Class.child2泛化的Class引用;//: typeinfo/GenericClassReferences.java


/**
 * @Description:  class引用总是指向某个Class对象，它可以制造类的实例，并包含可作用于这些实例的所有方法代码。
 *  他还包含该类的静态成员，因此，Class引用表示的就是它所指向的对象的确切类型，而该对象便是Class类的对象
 *
 *  但是1.5将他的类型变得更具体，而这是通过允许你对Class引用所指向的Class对象的类型进行限定而实现的，这里用到了泛型语法。
 *
 *  下面的例子中两种语法都是正确的
 */

public class GenericClassReferences {
  public static void main(String[] args) {
    Class intClass = int.class;
    Class<Integer> genericIntClass = int.class;
    genericIntClass = Integer.class; // Same thing
    intClass = double.class;
    // genericIntClass = double.class; // Illegal
  }
} ///:~
/**
* @Description: 普通的类引用不会产生警告信息，你可以看到，尽管泛型类引用只能赋值为指向其声明的类型，但是普通的引用可以被重新赋值为指向任何其他的
 *  Class对象。通过泛型语法，可以让编译器强制执行额外的类型检查。如果希望稍微放松一些呢？看起来可以执行类似的操作：
 *    Class<Number> genericNumberClass=int.class
 *  这看起来起作用，因为Integer继承自Number。但是他无法工作，因为Integer Class对象不是Number Class对象的子类(虽然诡异，但15章会深入讨论)
 *
 *  为了在使泛化的Class引用是放松限制，使用通配符？来表示任何事物。因此，可以在上例的普通Class引用中添加通配符，可以产生相同的效果
*/
class WildcardClassReferences {
  public static void main(String[] args) {
    Class<?> intClass = int.class;
    intClass = double.class;
  }
} ///:~
/**
* @Description: 1.5中，Class<?>优于Class，即便他们是等价的。
 *  Class不会产生编译器警告信息。Class<?>的好处是他表示你并非碰巧或由于疏忽而使用了一个非具体的类引用，而是你就想选择一个非具体版本。
 *
 *  为了创建一个Class引用，他被限定为某种类型，或该类型的任何子类型，你需要将通配符与extends结合，创建一个范围。
 *  现做如下声明：
*/
class BoundedClassReferences {
  public static void main(String[] args) {
    Class<? extends Number> bounded = int.class;
    bounded = double.class;
    bounded = Number.class;
    // Or anything else derived from Number.
  }
} ///:~
/**
* @Description: 见FilledList.java
*/
