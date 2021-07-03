package com.wby.thread.manythread.Chaptor13字符串.node5格式化输出.child4格式化说明符;

import java.util.Formatter;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/3 10:59
 * @Description:    在插入数据时，如果想要控制空格与对齐，可以使用下面的抽象语法：
 *  $[argument_index$][flags][width][.precision]conversion
 *  最常见的应用是改变一个区的最小尺寸，这可以通过指定width实现。Formatter对象通过在必要时添加空格，来确保一个域至少达到某种长度。
 *  默认情况下，数据是右对齐，不过可以通过使用“-”标志改变对其方向。
 *  与width相对的是precision，它用来指明最大尺寸
 *  width可以用于各种数据类型的转换，并且其行为方式都一样。
 *  precision则不然，不是所有数据类型都能使用precision，而且不同数据类型转换时，precision的意义也不同。
 *  precision用于String时，表示打印String输出字符的最大数量；
 *  precision用于浮点数，表示小数部分要显示出来的个数，过多则舍入、过少则补0；
 *  precision不能用于整数，因为整数没有小数部分，否则将触发异常
 */
public class Receipt {
    private double total=0;
    private Formatter f=new Formatter(System.out);
    public void printTitle(){
        f.format("%-15s %5s %10s\n","item","qty","price");
        f.format("%-15s %5s %10s\n","----","---","-----");
    }
    public void print(String name,int qty,double price){
        f.format("%-15.1Ss %5d %10.2f\n",name,qty,price);
    }
    public void printTotal(){
        f.format("%-15s %5s %10.2f\n","Text","",total*0.06);
        f.format("%-15s %5s %10s\n","","","------");
        f.format("%-15s %5s %10.2f\n","Text","",total*1.06);
    }

    public static void main(String[] args) {
        Receipt receipt = new Receipt();
        receipt.printTitle();
        receipt.print("Jack magic Bean",4,4.25);
        receipt.print("princess peas",3,3.25);
        receipt.print("bears ",1,31.25);
        receipt.printTotal();

    }

}
