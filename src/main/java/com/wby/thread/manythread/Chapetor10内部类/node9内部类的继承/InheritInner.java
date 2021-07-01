package com.wby.thread.manythread.Chapetor10内部类.node9内部类的继承;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/29 17:12
 * @Description:    因为内部类的构造器必须链接到指向其外围类对象的引用，所以继承内部类时会有点复杂。
 *  复杂点在于，那个指向外围类对象的“秘密的”引用 必须 被初始化，而在导出类中不再存在可连接的默认对象。
 *  要解决这个问题，必须使用特殊的语法
 */
public class InheritInner extends WithInner.Inner{
    InheritInner(WithInner wi){
        wi.super();
    }
    public static void main(String[] args) {
        WithInner withInner = new WithInner();
        InheritInner inheritInner = new InheritInner(withInner);
    }
}
class WithInner{
    class Inner{}
}
/**
* @Description: TODO  InheritInner只继承内部类，而不是外围类。但是要生成构造器时，默认构造器并不能使用，而且不能只是传递一个指向外围类对象的引用。
 *  此外，必须在构造器内使用如下语法：
 *      enclosingClassReference.super()
 *  这样，才提供了必要的引用。然后程序才能编译通过
*/
