package com.wby.thread.manythread.Chaptor13字符串.node1不可变String;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/1 19:22
 * @Description:    String对象时不可变的。String类每个看起来会修改String值的方法，实际上都是创建了一个全新的String，以包含修改后的内容，而最初的String则丝毫未动
 */
public class Immutable {
    public static String upcase(String s){
        return s.toUpperCase();
    }

    public static void main(String[] args) {
        String q="qweqeqa";
        System.out.println(q);
        String qq=upcase(q);
        System.out.println(qq);
        System.out.println(q);
    }
}
/**
* @Description: 当把q传给upcase时，实际上传递的是引用的一个拷贝。
 *  其实，每当把String对象作为方法的参数时，都会复制一份引用，而该引用所指向的对象其实一直呆在单一的物理位置上，从未动过。
*/
