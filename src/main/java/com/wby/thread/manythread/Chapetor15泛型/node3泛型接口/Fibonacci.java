package com.wby.thread.manythread.Chapetor15泛型.node3泛型接口;


import com.wby.thread.manythread.net.mindview.util.Generator;

import java.util.Iterator;

public class Fibonacci implements Generator<Integer> {
    private int count = 0;
    public Integer next() { return fib(count++); }
    private int fib(int n) {
        if(n < 2) return 1;
        return fib(n-2) + fib(n-1);
    }
    public static void main(String[] args) {
        Fibonacci gen = new Fibonacci();
        for(int i = 0; i < 18; i++)
            System.out.print(gen.next() + " ");
    }
} /* Output:
1 1 2 3 5 8 13 21 34 55 89 144 233 377 610 987 1597 2584
*///:~
/**
 * @Description: 虽然我们在Fibonacci类的里里外外使用的都是int类型，但是其类型参数却是Integer。
 *  这个例子引出了JAVA泛型的一个局限性；基本类型无法作为参数类型。不过1.5具备了自动打包和自动拆包的功能，可以很方便的在基本类型和其相应的包装其类型之间转换
 *  ，通过这个例子中Fibonacci类对int的使用，我们已经看到了这种效果
 *
 *  如果还想更进一步，编写一个实现了Iterable的Fibonacci生成器。我们的一个选择是重写这个类，令其实现Iterable接口。不过，你并不是总能拥有源代码的
 *  控制权，并且除非必须这么做，否则，我们也不愿意重写一个类。而且我们还有另一个选择，就是创建一个 适配器 来实现所需的接口。
 *
 *  有很多方法可以实现适配器。例如，可以通过继承来创建适配器类：
 */
class IterableFibonacci
        extends Fibonacci implements Iterable<Integer> {
    private int n;
    public IterableFibonacci(int count) { n = count; }
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            public boolean hasNext() { return n > 0; }
            public Integer next() {
                n--;
                return IterableFibonacci.this.next();
            }
            public void remove() { // Not implemented
                throw new UnsupportedOperationException();
            }
        };
    }
    public static void main(String[] args) {
        for(int i : new IterableFibonacci(18))
            System.out.print(i + " ");
    }
} /* Output:
1 1 2 3 5 8 13 21 34 55 89 144 233 377 610 987 1597 2584
*///:~
/**
 * @Description: 如果要在循环语句中使用IterableFibonacci，必须向 IterableFibonacci的构造器提供一个边界值，然后hasNext()方法才能知道何时应该返回false。
 */
