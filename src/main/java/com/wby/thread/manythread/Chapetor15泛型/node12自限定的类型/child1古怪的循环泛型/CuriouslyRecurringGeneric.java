package com.wby.thread.manythread.Chapetor15泛型.node12自限定的类型.child1古怪的循环泛型;//: generics/CuriouslyRecurringGeneric.java


/**
* @Description: 为了理解自限定类型的含义，我们从这个惯用法的一个简单版本入手，他没有自限定的边界。
 *
 *  不能直接继承一个泛型参数，但是，可以继承在其自己的定义中使用这个泛型参数的类。也就是说，可以声明：
*/
class GenericType<T> {}

public class CuriouslyRecurringGeneric
  extends GenericType<CuriouslyRecurringGeneric> {} ///:~
/**
* @Description: 可以按照Jim Coplien在C++中的 古怪的循环模板模式 的命名方式，称为 古怪的循环泛型(CRG)。
 * “古怪的循环”是指类相当古怪的出现在他自己的基类中这一事实。
 *
 * 为了理解其含义，努力大声说：我在创建一个新类，他继承自一个泛型类型，这个泛型类型接受我的类的名字作为其参数。
 * 当给出导出类的名字时，这个泛型基类能够实现什么呢？好吧，java中的泛型关乎参数和返回类型，因此他能产生使用导出类作为其参数和返回类型的基类。
 * 他还能将带出类型用作其域类型，甚至那些将被擦除为Object的类型。下面是表示了这种情况的一个泛型类：
 * 见BasicHolder.java
*/

/**
* @Description: 这是一个普通的泛型类型，他的一些方法将接受和产生具有其参数类型的对象，还有一个方法将在其存储的域上执行操作(尽管只是
 * 在这个域上执行Object操作)。我们可以在一个古怪的循环泛型中使用BasicHolder：
*/
//: generics/CRGWithBasicHolder.java

class Subtype extends BasicHolder<Subtype> {}

class CRGWithBasicHolder {
    public static void main(String[] args) {
        Subtype st1 = new Subtype(), st2 = new Subtype();
        st1.set(st2);
        Subtype st3 = st1.get();
        st1.f();
    }
} /* Output:
Subtype
*///:~
/**
* @Description: 注意，这里有些东西很重要：新类SubType接受的参数和返回值具有Subtype类型而不仅仅是基类BasicHolder类型。
 *
 * 这就是CRG的本质：基类用导出类替代其参数。
 * 这意味着反省积累变成了一种其所有导出类的公共功能的模板，但是这些功能对于其所有参数和返回值，将使用导出类型。
 *
 * 也就是说，在所产生的类中将使用确切类型而不是基类型。因此，在Subtype中，传递给set的参数和从get返回的类型都是确切的Subtype。
*/
