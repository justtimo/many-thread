package com.wby.thread.manythread.Chaptor13字符串.node3无意识的递归;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/2 17:47
 * @Description:    java每个类从根本上来说都是继承自Object类，标准容器也不例外。
 *  因此容器也有toString方法，并且重写了toString以便能够表达容器自身，以及容器包含的对象。
 */
public class ArrayListDisplay {
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<String>(Arrays.asList("wby", "wby1", "wby2", "wby3", "wby4", "wby5", "wby6"));
        for (String string : strings) {
            strings.add(string);
        }
        System.out.println(strings);

    }
}
/**
* @Description: 如果你希望打印内存地址，也许你会考虑this关键字
*/
class InfinitedRecursion{
    public String toString() {
        return "InfinitedRecursion address: "+this+"\n";
        //return "InfinitedRecursion address: "+super.toString()+"\n";
    }

    public static void main(String[] args) {
        ArrayList<InfinitedRecursion> infinitedRecursions = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            infinitedRecursions.add(new InfinitedRecursion());
        }
        System.out.println(infinitedRecursions);
    }
}
/**
* @Description: 上例会出现异常。
 *  原因是："InfinitedRecursion address: "+this 这里发生了自动类型转换：由InfinitedRecursion类型转换为String类型
 *  他怎么转换呢，正是调用this上的toString方法，于是就发生了递归调用
 *  如果想打印，不该使用this，而是应该使用super.toString()
*/
