//: typeinfo/toys/ToyTest.java
// Testing class Class.
package com.wby.thread.manythread.charector14类型信息.node2Class;
import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
 * @Description:
 */
interface HasBatteries {}

interface Waterproof {}

interface Shoots {}

class Toy {
  // Comment out the following default constructor
  // to see NoSuchMethodError from (*1*)
  Toy() {}
  Toy(int i) {}
}
class FancyToy extends Toy
implements HasBatteries, Waterproof, Shoots {
  FancyToy() { super(1); }
}

public class ToyTest {
  static void printInfo(Class cc) {
    print("Class name: " + cc.getName() +
      " is interface? [" + cc.isInterface() + "]");
    print("Simple name: " + cc.getSimpleName());
    print("Canonical name : " + cc.getCanonicalName());
  }
  public static void main(String[] args) {
    Class c = null;
    try {
      //获得class对象，全限定类名
      c = Class.forName("com.wby.thread.manythread.charector14类型信息.node2Class.FancyToy");
    } catch(ClassNotFoundException e) {
      print("Can't find FancyToy");
      System.exit(1);
    }
    printInfo(c);
    //getInterfaces()获取类继承的接口
    for(Class face : c.getInterfaces())
      printInfo(face);
    //获取父类
    Class up = c.getSuperclass();
    Object obj = null;
    try {
      // Requires default constructor:
      //newInstance()新建对象，使用默认的构造器
      obj = up.newInstance();
    } catch(InstantiationException e) {
      print("Cannot instantiate");
      System.exit(1);
    } catch(IllegalAccessException e) {
      print("Cannot access");
      System.exit(1);
    }
    //getClass()获得class对象，返回全限定类名
    printInfo(obj.getClass());
  }
} /* Output:
Class name: typeinfo.toys.FancyToy is interface? [false]
Simple name: FancyToy
Canonical name : typeinfo.toys.FancyToy
Class name: typeinfo.toys.HasBatteries is interface? [true]
Simple name: HasBatteries
Canonical name : typeinfo.toys.HasBatteries
Class name: typeinfo.toys.Waterproof is interface? [true]
Simple name: Waterproof
Canonical name : typeinfo.toys.Waterproof
Class name: typeinfo.toys.Shoots is interface? [true]
Simple name: Shoots
Canonical name : typeinfo.toys.Shoots
Class name: typeinfo.toys.Toy is interface? [false]
Simple name: Toy
Canonical name : typeinfo.toys.Toy
*///:~
/**
* @Description: 在main中，用forName()方法在适当的try语句中，创建一个Class引用，并将其初始化为指向FancyToy Class。
 *  注意，传递给forName()的String参数，必须使用全限定名
 *
 *  printInfo()使用getName()来产生全限定类名，并分别使用getSimpleName()、getCanonicalName()来产生不包含包名的类名和全限定类名
 *  isInterface()表示Class对象是否是一个接口。
 *  因此，你可以通过Class对象了解你想了解的类型的所有信息
 *
 *  getInterfaces()返回的是Class对象，表示Class对象所包含的接口
 *  getSuperclass()查询其直接基类，将返回你可以用来进一步查询的Class对象。
 *  因此，你可以在运行时发现一个对象完整的类继承结构
 *
 *  newInstance()是实现“虚拟构造器”的一种途径，使用newInstance()创建的类，必须有默认的构造器：后面章节会展示通过反射用任意构造器来动态的创建对象
 *  虚拟构造器：允许你声明：我不知道你的确切类型，但无论如何要正确的创建你自己
 *
 *  up仅仅是Class的一个引用，在编译期不具备任何更进一步的类型信息。
 *  当你创建新实例时，会得到Object的引用，但是这个引用指向Toy对象
 *  当然，在你可以发送Object能够接受的消息之外的任何消息之前，你必须更多的了解他，并执行某种转型。
 *
*/
