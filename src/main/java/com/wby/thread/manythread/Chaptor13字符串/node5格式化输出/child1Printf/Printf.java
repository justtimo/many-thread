package com.wby.thread.manythread.Chaptor13字符串.node5格式化输出.child1Printf;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/2 20:07
 * @Description:    printf()在运行的时候，首先将x插入到%d的位置，然后将y的值插入到%y的位置。
 *  这些占位符被称作 格式修饰符 ，它不但说明了插入数据的位置，同时说明了插入什么类型的变量，以及如何格式化。
 *  在下面的例子中，%d表示x是一个整数，%f表示y是一个浮点数
 */
public class Printf {
    public static void main(String[] args) {
        int x=10;
        double y=2.00d;
        System.out.printf("Row1: [%d %f]\n",x,y);
    }
}
