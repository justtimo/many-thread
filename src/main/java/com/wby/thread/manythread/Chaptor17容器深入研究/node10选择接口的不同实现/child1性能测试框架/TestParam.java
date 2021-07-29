package com.wby.thread.manythread.Chaptor17容器深入研究.node10选择接口的不同实现.child1性能测试框架;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/29 19:01
 * @Description:
 */
/**
 * @Description: 每个Test对象都存储了该测试的名字。当你调用test0方法时，必须给出待测容器，以及"信使"或"数据传输对象"，它们保存有用于该特定测试的各种参数。
 * 这些参数包括size，它表示在容器中的元素数量;以及loops，它用来控制该测试迭代的次数。这些参数在每个测试中都有可能会用到，也有可能会用不到。
 *
 * 每个容器都要经历一系列对testO的调用，每个都带有不同的TestParam，因此TestParam还包含静态的arrayO方法，使得创建TestParam对象数组变得更容易。
 * arrayO的第一个版本接受的是可变参数列表，其中包括可互换的size和loops的值;而第二个版本接受相同类型的列表，但是它的值都在String中——通过这种方式，它可以用来解析命令行参数;
 */
public class TestParam {
    public final int size;
    public final int loops;
    public TestParam(int size, int loops) {
        this.size = size;
        this.loops = loops;
    }
    // Create an array of TestParam from a varargs sequence:
    public static TestParam[] array(int... values) {
        int size = values.length/2;
        TestParam[] result = new TestParam[size];
        int n = 0;
        for(int i = 0; i < size; i++)
            result[i] = new TestParam(values[n++], values[n++]);
        return result;
    }
    // Convert a String array to a TestParam array:
    public static TestParam[] array(String[] values) {
        int[] vals = new int[values.length];
        for(int i = 0; i < vals.length; i++)
            vals[i] = Integer.decode(values[i]);
        return array(vals);
    }
} ///:~
/**
* @Description: 见Tester.java
*/
