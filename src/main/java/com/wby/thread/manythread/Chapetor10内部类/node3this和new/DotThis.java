package com.wby.thread.manythread.Chapetor10内部类.node3this和new;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/16 16:37
 * @Description: 如果需要生成对外部对象的引用，可以使用外部类的名字+ .this 。这样产生的引用自动的具有正确的类型。
 *
 * 下例展示如何使用.this
 */
public class DotThis {
    void f(){
        System.out.println("DotThis. f() *");
    }

    public static void main(String[] args) {
        DotThis dt = new DotThis();
        Inner i = dt.inner();
        i.outer().f();
    }

    public class Inner{
        public DotThis outer(){
            return DotThis.this;//等价于return new DotThis();
        }
    }
    public Inner inner() {
        return new Inner();
    }

}
/**
* @Description: TODO
 * 有时你可能想告知某些其他对象，去创建某个内部类的对象。你必须在new表达式中提供对其他外部对象的引用，这时需要使用.new语法。
*/
class DotNew{
    public class Inner{}
    /**
    * @Description: TODO
     *要想直接创建内部类对象，必须使用外部类对象来创建该内部类对象。
     * 这解决了内部类对象名字作用域的问题，因此你不必声明（实际上也不能声明）dn.new DotNew.Inner()
    */
    public static void main(String[] args) {
        DotNew dn = new DotNew();
        Inner inner = dn.new Inner();
    }
}


/**
* @Description: TODO
 * 在拥有外部类对象之前是不可能创建内部类对象的。
 * 这是因为内部类对象会暗自连接到创建他的外部类对象上。
 * 但是，如果创建嵌套类（静态内部类），那么他就不需要外部类对象的引用
*/
class Parcel3{
    class Constents{
        private int i=11;
        public int value(){
            return i;
        }
    }
    class Destination{
        private String label;
        Destination(String whereTo) {
            label = whereTo;
        }
        String readLabel(){
            return label;
        }
    }

    public static void main(String[] args) {
        Parcel3 p = new Parcel3();
        Constents constents = p.new Constents();
        Destination wwwWbybbybyby = p.new Destination("WWWWbybbybyby");
    }
}
