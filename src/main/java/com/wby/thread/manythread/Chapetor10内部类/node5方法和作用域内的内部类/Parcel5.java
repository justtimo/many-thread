package com.wby.thread.manythread.Chapetor10内部类.node5方法和作用域内的内部类;

import com.wby.thread.manythread.Chapetor10内部类.node4内部类与向上转型.Destination1;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/16 20:07
 * @Description: TODO 方法作用域内创建一个完整的类：局部内部类
 * PDestination1是destanation（）的一部分，而不是Parcel5的一部分。所以在destanation（）之外不能访问PDestination1。
 * 注意，return语句的向上转型---返回的是Destination1的引用，他是PDestination1的基类。
 * 当然，在destanation（）中定义了内部类PDestination1，并不意味着destanation（）方法执行完PDestination1就不可用了
 */
public class Parcel5 {
    public static void main(String[] args) {
        Parcel5 parcel5 = new Parcel5();
        Destination1 wbbbbttttt = parcel5.destanation("wbbbbttttt");
    }
    public Destination1 destanation(String s){
        class PDestination1 implements Destination1 {
            private String label;
            private PDestination1(String whereTo){
                label = whereTo;
            }

            @Override
            public String readLeable() {
                return label;
            }
        }
        return new PDestination1(s);
    }

}
