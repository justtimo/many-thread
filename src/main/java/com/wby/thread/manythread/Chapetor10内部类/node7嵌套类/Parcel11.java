package com.wby.thread.manythread.Chapetor10内部类.node7嵌套类;

import com.wby.thread.manythread.Chapetor10内部类.node4内部类与向上转型.Contents;
import com.wby.thread.manythread.Chapetor10内部类.node4内部类与向上转型.Destination1;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/17 19:52
 * @Description: 如果不需要内部类对象与其外围类对象之间有联系，n那么可以将内部类声明为static。这通常称为 嵌套类
 *  普通的内部类对象隐式的保存了一个引用，指向创建他的外围类对象。然而当内部类时static时，意味着 ：static用于内部类的含义：
 *      1.要创建嵌套类对象，并不需要其外围类对象
 *      2.不能从嵌套类的对象中访问非静态的外围类对象
 *   嵌套类与普通内部类还有一个区别：
 *      普通内部类的字段与方法，只能放在类的外部层次，所以普通的内部类不能有static数据和字段，也不能包含嵌套类。
 *   但是嵌套类可以包含这些东西
 */
public class Parcel11 {
    public static class ParcelContents implements Contents {
        private int i=11;
        @Override
        public int value() {
            return i;
        }
    }
    public static class ParcelDestination implements Destination1 {
        private String label;
        private ParcelDestination(String whereTo){
            label = whereTo;
        }
        @Override
        public String readLeable() {
            return label;
        }
        private static void f(){ }
        static int x=10;
        static class AnotherLevel{
            public static void f(){ }
            static int x = 10;
        }
    }
    public static Destination1 destination1(String s){
        return new ParcelDestination(s);
    }
    public static Contents contents() {
        return new ParcelContents();
    }

    public static void main(String[] args) {
        Contents contents = contents();
        System.out.println(contents.value());
        Destination1 wwby = destination1("wwby");
        System.out.println(wwby.readLeable());
    }
}
/**
* @Description: TODO main()中。没有任何Paracel11的对象是必须的，而是选取static成员的普通语法来调用方法：这些方法返回Contents和Destination1的引用
 * 就像之前的例子一样，普通内部类中，通过this引用可以链接到其外围类对象。嵌套类就没有这个特殊的this引用。这使得他类似于一个static方法。
*/
