package com.wby.thread.manythread.Chapetor15泛型.node4泛型方法.child5简化元祖的使用;

import com.wby.thread.manythread.Chapetor15泛型.node2简单泛型.child1一个元组类库.Amphibian;
import com.wby.thread.manythread.Chapetor15泛型.node2简单泛型.child1一个元组类库.Vehicle;
import com.wby.thread.manythread.net.mindview.util.FiveTuple;
import com.wby.thread.manythread.net.mindview.util.FourTuple;
import com.wby.thread.manythread.net.mindview.util.ThreeTuple;
import com.wby.thread.manythread.net.mindview.util.TwoTuple;

import static com.wby.thread.manythread.net.mindview.util.Tuple.tuple;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/9 16:34
 * @Description:
 */
class Tuple {
    public static <A,B> TwoTuple<A,B> tuple(A a, B b) {
        return new TwoTuple<A,B>(a, b);
    }
    public static <A,B,C> ThreeTuple<A,B,C>
    tuple(A a, B b, C c) {
        return new ThreeTuple<A,B,C>(a, b, c);
    }
    public static <A,B,C,D> FourTuple<A,B,C,D>
    tuple(A a, B b, C c, D d) {
        return new FourTuple<A,B,C,D>(a, b, c, d);
    }
    public static <A,B,C,D,E>
    FiveTuple<A,B,C,D,E> tuple(A a, B b, C c, D d, E e) {
        return new FiveTuple<A,B,C,D,E>(a, b, c, d, e);
    }
} ///:~
/**
* @Description: 下面是修改后的 TupleTest.java，用来测试Tuple.java
*/
class TupleTest2 {
    static TwoTuple<String,Integer> f() {
        return tuple("hi", 47);
    }
    static TwoTuple f2() { return tuple("hi", 47); }
    static ThreeTuple<Amphibian,String,Integer> g() {
        return tuple(new Amphibian(), "hi", 47);
    }
    static
    FourTuple<Vehicle,Amphibian,String,Integer> h() {
        return tuple(new Vehicle(), new Amphibian(), "hi", 47);
    }
    static
    FiveTuple<Vehicle,Amphibian,String,Integer,Double> k() {
        return tuple(new Vehicle(), new Amphibian(),
                "hi", 47, 11.1);
    }
    public static void main(String[] args) {
        TwoTuple<String,Integer> ttsi = f();
        System.out.println(ttsi);
        System.out.println(f2());
        System.out.println(g());
        System.out.println(h());
        System.out.println(k());
    }
} /* Output: (80% match)
(hi, 47)
(hi, 47)
(Amphibian@7d772e, hi, 47)
(Vehicle@757aef, Amphibian@d9f9c3, hi, 47)
(Vehicle@1a46e30, Amphibian@3e25a5, hi, 47, 11.1)
*///:~
/**
* @Description: 注意，方法f()返回一个参数化的TwoTuple对象，而f2()返回的是非参数化的 TwoTuple对象。
 *
 *  在这个例子中，编译器并没有关于f2（）的警告信息，因为我们并没有将其返回值作为参数化对象使用。
 *  在某种意义上，他被“向上转型”为一个非参数化的TwoTuple。然而，如果试图将f2（）的返回值转型为参数化的TwoTuple，编译器就会发出警告
*/
