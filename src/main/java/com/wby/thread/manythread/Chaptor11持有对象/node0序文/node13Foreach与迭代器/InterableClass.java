package com.wby.thread.manythread.Chaptor11持有对象.node0序文.node13Foreach与迭代器;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/1 14:57
 * @Description:    能够与foreach一起工作是所有Collection对象的特性。
 *  之所以能够工作，是因为1.5引入了被称作Iterable的接口，该接口包含一个能够产生Iterator的Iterator()方法，并且Iterable接口被foreach用来在序列中移动。
 *  因此，你如果创建了任何实现Iterable的类，都可以将它用在foreach语句中
 */
public class InterableClass implements Iterable<String>{
    public String[] words="wby, wby1, wby2, wby3, wby4, wby5, wby6,wby7,wby8,wby9,wby10".split(",");

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int index=0;
            @Override
            public boolean hasNext() {
                return index<words.length;
            }

            @Override
            public String next() {
                return words[index++];
            }
        };
    }

    public static void main(String[] args) {
        for (String s : new InterableClass()) {
            System.out.println(s);
        }
    }
}
/**
* @Description: iterator方法返回的是实现了Iterator<String>的匿名内部类实例，该匿名内部类可以遍历数组中的所有单词。
 *
 *  1.5中大量的类都是Iterable类型的，主要包括所有的Collection类（但是不包括各种Map）。例如下面的代码可以显示所有操作系统的变量：
*/
class EnviromentViriables{
    public static void main(String[] args) {
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            System.out.println(entry.getKey()+" , "+entry.getValue());
        }
    }
}
/**
* @Description:  System.getenv()返回一个Map，entrySet()产生一个由Map.Entry构成的Set集合，并且这个Set是Iterable的，可以用在foreach中
 *
 *  foreach可以用于数组或其他任何Iterable，但这不意味着数组肯定是一个Iterable，而任何自动包装也不会自动发生
*/
class ArraysIsNotIterable{
    static <T> void test(Iterable<T> ib){
        for (T t : ib) {
            System.out.println(t);
        }
    }
    public static void main(String[] args) {
        test(Arrays.asList(1,2,3));
        String[] s={"A","B","S"};
        //test(s);
        test(Arrays.asList(s));
    }
}
/**
* @Description: 尝试把数组当做一个Iterable参数传递会导致失败，这说明不存在任何从数组到Iterable的自动转换，必须手动执行这种转换。
*/
