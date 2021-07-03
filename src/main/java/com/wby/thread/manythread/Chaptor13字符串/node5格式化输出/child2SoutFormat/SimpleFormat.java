package com.wby.thread.manythread.Chaptor13字符串.node5格式化输出.child2SoutFormat;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/2 20:13
 * @Description:    1.5引入了format方法可用于PrintStream和PrintWriter对象，其中也包括System.out对象。
 *  format()模仿自C的printf().
 */
public class SimpleFormat {
    public static void main(String[] args) {
        int x=5;
        double y=6.33356;
        //老方式
        System.out.println("Row1:["+x+" "+y+" ]");
        //新方式
        System.out.printf("Row1: [%d %f]\n",x,y);
        //或者
        System.out.format("Row1: [%d %f]\n",x,y);
    }
}
/**
* @Description:  printf和format是等价的，他们只需要一个简单的格式化字符串，加上一串参数即可，每个参数对应一个格式修饰符。
*/
