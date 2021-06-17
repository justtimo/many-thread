package com.wby.thread.manythread.Chapetor10内部类.node2链接到外部类;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/6/9 20:30
 * @Description:
 * 目前为止，内部类似乎还只是一个名字隐藏和组织代码的模式。
 * 他还有其他用处：这个例子说明了这点。
 *  当生成一个内部类对象是时，此对象与制造他的 外围对象 之间就有了一种联系，所以他能访问其外围对象的所有成员，而且不需要任何特殊条件。此外，内部类还拥有其外围类的所有元素的访问权。
 */
public class Sequence {
    private Object[] items;
    private int next=0;
    /**
    * @Description: TODO
     * Sequence只是一个固定大小的数组，只是以累的方式包装了起来。调用add()在末尾添加新的Object(只要还有空间)。
     * 要获取Sequence中的每一个对象，可以使用Select接口。这是  迭代器设计模式  的一个例子。
     * Selector允许你检查序列是否到了末尾(end()方法)，以及访问当前对象current()，以及移动到下一个对象next()。
     * 因为Selector是一个接口，所以别的类可以按照自己的方式来实现这个接口，并且别的方法能以此为参数，生成更加通用的代码。
    */
    public static void main(String[] args) {
        Sequence sequence = new Sequence(10);
        for (int i = 0; i < 10; i++){
            sequence.add(Integer.toString(i));
        }
        Selector selector = sequence.selector();
        while (!selector.end()){
            System.out.println(selector.current()+" ");
            selector.next();
        }
    }

    public Sequence(int size){
        items = new Object[size];
    }
    public void add(Object x){
        if (next<items.length){
            items[next++] = x;
        }
    }
    /**
    * @Description: TODO
     * 注意SequeceSelector，你可能觉得他只是一个内部类。但是请注意，
     * 他的三个方法里都用到了objects（也就是items），这是一个引用，他并不是SequeceSelector的一部分，而是外围类的一个private字段。
     * 然而，内部类可以访问外围类的方法和字段，就像自己拥有他们一样，这带来了很大的方便。
    */
    private class SequeceSelector implements Selector{
        private int i=0;

        @Override
        public Boolean end() {
            return i==items.length;
        }

        @Override
        public Object current() {
            return items[i];
        }

        @Override
        public void next() {
            if (i<items.length)
                i++;
        }
    }
    public Selector selector(){
        return new SequeceSelector();
    }
}
/**
* @Description: TODO
 *
 * 所以内部类自动拥有外围类所有成员的访问权限。
*   如何做到的？？？
*   当某个外围类对象创建一个内部类对象时，此内部类对象必定会秘密的捕获一个指向那个外围类对象的引用。然后，在访问此外围类的成员时，就是用那个引用来选择外围类的成员。
*   现在可以看到：内部类的对象只有在与外围类对象相关联的情况下才能被创建（内部类非static类时）
*
*   构建内部类时，需要一个指向外围对象的引用，如果编译器访问不到这个引用就会报错。不过绝大多数时候程序员无需关心这个过程。
*/

