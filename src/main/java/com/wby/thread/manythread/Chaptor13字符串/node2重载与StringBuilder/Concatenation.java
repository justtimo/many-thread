package com.wby.thread.manythread.Chaptor13字符串.node2重载与StringBuilder;

import java.util.Random;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/2 17:08
 * @Description:    不可变性带来了一定的效率问题，为String对象重载的+操作符就是一个例子。
 *  重载的意思是：一个操作符应用在特定的类时，被赋予了特殊的意义（用于String的+ +=是java中仅有的两个重载过的操作符，java并不允许程序员重载任何操作符）
 *  操作符+可以用来连接String
 */
public class Concatenation {
    public static void main(String[] args) {
        String memo="memo";
        String s="abc"+memo+"def"+"fg";
    }
}
/**
* @Description: 使用java -c Concatenation可以查看字节码文件，可以看到，java编译器自动引入了StringBuilder，因为他更高效
 *  也许你觉得可以随意使用String，反正编译器优化了性能。但是在此之前，我们先看一下编译器能优化到什么程度。
 *  下面的例子一个方法使用了多个String对象；方法而使用了StringBuilder
*/
class WitherStringBuilder{
    public String impilict(String[] fileds){
        String result="";
        for (int i = 0; i < fileds.length; i++) {
            result+=fileds[i];
        }
        return result;
    }
    public String explict(String[] fileds){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < fileds.length; i++) {
            result.append(fileds[i]);
        }
        return result.toString();
    }
}
/**
* @Description: 再次使用 java -c Concatenation可以查看字节码文件
 *  在第一个方法内的循环体中，每循环一次，都会生成一个新的StringBuilder
 *  在第二个方法中，只生成了一个StringBuilder，而且循环体的代码更简洁。
 *  显示的创建StringBuilder还允许你预先为其指定大小，如果你知道最终字符串有多大，那么预指定StringBuilder的大小可以有效避免多次重新分配缓存
 *
 *  因此，如果toString方法使用了循环，那么最好自己创建一个StringBuilder对象，用它来构造最终的结果。参考下例：
*/
class UsingStringBuilder{
    public static Random rand=new Random();
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < 2; i++) {
            result.append(rand.nextInt(100));
            result.append(",");
        }
        result.delete(result.length() - 2,result.length() );
        result.append("]");
        return result.toString();
    }

    public static void main(String[] args) {
        UsingStringBuilder usingStringBuilder = new UsingStringBuilder();
        System.out.println(usingStringBuilder);
    }
}
/**
* @Description: 可以看到，StringBuilder提供了多种方法，除此之外还有一个StringBuffer，他是线程安全的，因此开销会大一些。
*/
