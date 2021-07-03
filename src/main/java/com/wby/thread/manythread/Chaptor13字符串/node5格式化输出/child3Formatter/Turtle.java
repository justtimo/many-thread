package com.wby.thread.manythread.Chaptor13字符串.node5格式化输出.child3Formatter;

import java.io.PrintStream;
import java.util.Formatter;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/2 20:18
 * @Description:    java中所有行的格式化功能都由util.Formatter类处理。
 *  可以将Formatter当做一个翻译器，他将你的格式化字符串与数据翻译成你想要的结果。
 *  当你创建一个Formatter对象时，需要向构造器传递一些信息，告诉它最终的结果将向那里输出
 */
public class Turtle {
    private String name;
    private Formatter f;
    public  Turtle(String name,Formatter f){
        this.name = name;
        this.f = f;
    }
    public void move(int x,int y){
        f.format("%s thr turtle is at (%d,%d)\n",name,x, y);
    }

    public static void main(String[] args) {
        PrintStream outAlias = System.out;
        Turtle tonny = new Turtle("Tonny", new Formatter(System.out));
        Turtle terry = new Turtle("Terry", new Formatter(outAlias));
        tonny.move(0,0);
        terry.move(4,8);
        tonny.move(3,4);
        terry.move(2,5);
        tonny.move(3,3);
        terry.move(3,3);
    }
}
/**
* @Description: 所有的tonny都将输出到System.out，而所有的Terry都将输出到System.out的一个别名中。
 * Formatter的构造器经过重载可以接受多种输出目的地，不过最常用的还是PrintStream(如上例)、OutputStream和File。
 *
 * 上例中还是用了新的格式化修饰符%s，他表示插入的参数是String类型，。这个例子使用的是最简单类型的格式修饰符---它只具有转换类型而没有其他功能
*/
