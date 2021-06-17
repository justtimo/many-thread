package com.wby.thread.manythread.Chapetor10内部类.node6匿名内部类;

import com.wby.thread.manythread.Chapetor10内部类.node4内部类与向上转型.Contents;
import com.wby.thread.manythread.Chapetor10内部类.node4内部类与向上转型.Destination1;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/16 20:25
 * @Description:
 * contents()方法将返回值的生成和表示这个返回值的类定义结合在了一起。另外，这个类是没有名字的。
 * 这种语法是指：创建一个继承自Contents的匿名类的对象。通过new表达式返回的引用被自动向上转型为对Contents的引用。
 */
public class Parcel7 {
    public Contents contents(){
        return new Contents() {//插入一个类定义
            private int i=11;
            @Override
            public int value() {
                return i;
            }
        };
    }

    public static void main(String[] args) {
        Parcel7 p = new Parcel7();
        Contents contents = p.contents();
        System.out.println(contents.value());
    }
}
/**
* @Description: TODO 上述匿名内部类的语法是以下形式的简化形式
*/
class Parcel7B{
    class MyContents implements Contents{
        private int i=11;
        @Override
        public int value() {
            return i;
        }
    }
    public Contents contents() {
        return new MyContents();
    }

    public static void main(String[] args) {
        Parcel7B parcel7B = new Parcel7B();
        Contents contents = parcel7B.contents();
        System.out.println(contents.value());
    }
}

/**
* @Description: TODO 下面的例子演示了：如果你的基类需要一个有参数的构造器，你该怎么办?
 *  只需要简单地传递合适的参数给基类的构造器即可、。这里是将x传递给new Wrapping(x).尽管Wrapping只是一个具有具体实现的普通类，但是他还是被导出类当做公共的“接口”来使用。
 *
 *  匿名内部类末尾的分号。并不是用来标记此内部类结束的。实际上，他是标记表达式的结束。只不过这个表达式恰好包含了一个匿名内部类而已。
*/
class Parcel8{
    public Wrapping wrapping(int x){
        return new Wrapping(x){
            public int value() {
                return super.value()*47;
            }
        };
    }

    public static void main(String[] args) {
        Parcel8 parcel8 = new Parcel8();
        Wrapping wrapping = parcel8.wrapping(10);
        System.out.println(wrapping.value());
    }
}
class Wrapping{
    private int i;
    public Wrapping(int x){
        i=x;
    }
    public int value(){
        return i;
    }
}

/**
* @Description: TODO 在匿名内部类定义字段时，还能够对其执行初始化操作
*/
class Parcel9{
    /**
    * @Description: TODO 如果定义一个匿名内部类，并且希望它使用一个在其外部定义的对象，那么该参数引用必须是final的，否则编译报错。
    */
    public Destination1 destination1(final String dest){
        return new Destination1() {
            private String label=dest;
            @Override
            public String readLeable() {
                return label;
            }
        };
    }

    public static void main(String[] args) {
        Parcel9 p = new Parcel9();
        Destination1 wby = p.destination1("wby");
    }
}
/**
* @Description: TODO  仅仅给一个字段赋值上述例子的方法就很好。但是，如果想做一些类似构造器的行为呢？
 * TODO 因为匿名内部类根本没有名字，所以匿名内部类中不可能有命名构造器！！！ 但是通过 实例初始化 ，就呢能够达到为匿名内部类创建一个构造器的效果
*/
class Parcel9B{
    /**
    * @Description: TODO 此例子中，不要求变量i一定是final的。因为i被传递给匿名内部类的构造器，他并不会直接在匿名类内部被直接使用
    */
    public static Base getBase(int i){
        return new Base(i) {
            {
                System.out.println("inside instance initializer");
            }
            @Override
            public void f() {
                System.out.println("In Parcel9B f()");
            }
        };
    }

    public static void main(String[] args) {
        Base base = getBase(7);
        base.f();
    }
}
abstract class Base{
    public Base(int i){
        System.out.println("Base Constractor . i =  "+i);
    }
    public abstract void f();
}

/**
* @Description: TODO 带实例初始化的“parcel”形式，注意：destination1（）的参数必须是final的，因为他们是在匿名内部类使用的
*/
class Parcel10{
    public Destination1 destination1(final String dest,final float price){
        return new Destination1() {
            private String lable=dest;
            private int cost;
            {
                cost=Math.round(price);
                /**
                * @Description: TODO 可以看到，他们不能作为字段初始化动作的一部分来执行（if语句）。所以对于匿名内部类而言。实例初始化的实际效果就是构造器。当然他受到了限制：不能重载实例化方法，所以只能有一个这样的构造器
                */
                if (cost>100){
                    System.out.println("Over budget!");
                }
            }
            @Override
            public String readLeable() {
                return lable;
            }
        };
    }

    public static void main(String[] args) {
        Parcel10 p = new Parcel10();
        Destination1 w_by = p.destination1("w by", 103.568F);
    }
}
/**
* @Description: TODO 匿名内部类与正规的继承相比有些受限制，因为匿名内部类既可以扩展类，也可以实现接口，但是不能两者兼备。而且如果是实现接口，也只能实现一个
*/
