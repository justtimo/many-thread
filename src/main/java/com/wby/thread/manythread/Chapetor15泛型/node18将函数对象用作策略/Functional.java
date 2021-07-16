package com.wby.thread.manythread.Chapetor15泛型.node18将函数对象用作策略;//: generics/Functional.java

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
/**
* @Description:  最后一个例子通过使用前一节描述的适配器方式创建了真正泛化的代码。这个例子开始时是一种尝试，要创建一个元素序列的总和，这些元素可以
 * 是任何可以计算总和的类型，但是后来这个例子使用 功能型 编程风格，演化成了可以执行通用的操作。
 *
 * 如果值查看尝试添加对象的过程，就会看到这是在多个类中的公共操作，但是这个操作没有在任何我们可以指定的基类中表示---有时甚至可以使用“+”操作符，而其他时间
 * 可以使用某种add方法。
 * 这是在试图编写泛化代码的时候通常会碰到的情况，因为你想将这些代码应用于多个类上---特别是，像本例一样，作用多个已经存在且我们不能“修正”的类上。
 * 即使你可以将这种情况窄化到Number的子类，这个超类也不包括任何有关“可添加性”的东西。
 *
 * 解决方案是使用 策略 设计模式，这种设计模式可以产生更优雅的代码，因为他将“变化的事物”完全隔离到一个 函数对象 中。(有时会看到将它成为“仿函数”，作者使用“函数对象”而不是“仿函数”，因为“仿函数”在数学中有具体而不同的意义)
 * 函数对象就是在某种程度上行为像函数的对象---一般，会有一个相关的方法(在支持操作符重载的语言中，可以创建对这个方法的调用，而这个调用看起来就和普通
 * 的方法调用一样)。
 * 函数对象的价值在于，与普通方法不同，他们可以传递出去，并且还可以拥有在多个调用之间持久化的状态。
 * 当然，可以用鳄梨中的任何方法来实现与此类似的操作，但是(与使用任何设计模式一样)函数对象主要是由其目的来区别。
 * 这里的目的就是要创建某种事物，使他的行为像是一个可以传递出去的单个方法一样，这样，他就和策略设计模式耦合了，有时甚至无法区分
 *
 * 尽管使用了大量的设计模式，但是他们之间的界限是模糊的：创建执行适配操作的函数对象，而他们将被传递到用作策略的方法中。
 *
 * 通过这种方式，我添加了最初着手创建的各种类型的泛型方法，以及其他的泛型方法。下面是产生的结果：
*/

// Different types of function objects:
interface Combiner<T> { T combine(T x, T y); }

interface UnaryFunction<R,T> { R function(T x); }

interface Collector<T> extends UnaryFunction<T,T> {
  T result(); // Extract result of collecting parameter
}

interface UnaryPredicate<T> { boolean test(T x); }

public class Functional {
  // Calls the Combiner object on each element to combine
  // it with a running result, which is finally returned:
  public static <T> T
  reduce(Iterable<T> seq, Combiner<T> combiner) {
    Iterator<T> it = seq.iterator();
    if(it.hasNext()) {
      T result = it.next();
      while(it.hasNext())
        result = combiner.combine(result, it.next());
      return result;
    }
    // If seq is the empty list:
    return null; // Or throw exception
  }
  // Take a function object and call it on each object in
  // the list, ignoring the return value. The function
  // object may act as a collecting parameter, so it is
  // returned at the end.
  public static <T> Collector<T>
  forEach(Iterable<T> seq, Collector<T> func) {
    for(T t : seq)
      func.function(t);
    return func;
  }
  // Creates a list of results by calling a
  // function object for each object in the list:
  public static <R,T> List<R>
  transform(Iterable<T> seq, UnaryFunction<R,T> func) {
    List<R> result = new ArrayList<R>();
    for(T t : seq)
      result.add(func.function(t));
    return result;
  }
  // Applies a unary predicate to each item in a sequence,
  // and returns a list of items that produced "true":
  public static <T> List<T>
  filter(Iterable<T> seq, UnaryPredicate<T> pred) {
    List<T> result = new ArrayList<T>();
    for(T t : seq)
      if(pred.test(t))
        result.add(t);
    return result;
  }
  // To use the above generic methods, we need to create
  // function objects to adapt to our particular needs:
  static class IntegerAdder implements Combiner<Integer> {
    public Integer combine(Integer x, Integer y) {
      return x + y;
    }
  }
  static class
  IntegerSubtracter implements Combiner<Integer> {
    public Integer combine(Integer x, Integer y) {
      return x - y;
    }
  }
  static class
  BigDecimalAdder implements Combiner<BigDecimal> {
    public BigDecimal combine(BigDecimal x, BigDecimal y) {
      return x.add(y);
    }
  }
  static class
  BigIntegerAdder implements Combiner<BigInteger> {
    public BigInteger combine(BigInteger x, BigInteger y) {
      return x.add(y);
    }
  }
  static class
  AtomicLongAdder implements Combiner<AtomicLong> {
    public AtomicLong combine(AtomicLong x, AtomicLong y) {
      // Not clear whether this is meaningful:
      return new AtomicLong(x.addAndGet(y.get()));
    }
  }
  // We can even make a UnaryFunction with an "ulp"
  // (Units in the last place):
  static class BigDecimalUlp
  implements UnaryFunction<BigDecimal,BigDecimal> {
    public BigDecimal function(BigDecimal x) {
      return x.ulp();
    }
  }
  static class GreaterThan<T extends Comparable<T>>
  implements UnaryPredicate<T> {
    private T bound;
    public GreaterThan(T bound) { this.bound = bound; }
    public boolean test(T x) {
      return x.compareTo(bound) > 0;
    }
  }
  static class MultiplyingIntegerCollector
  implements Collector<Integer> {
    private Integer val = 1;
    public Integer function(Integer x) {
      val *= x;
      return val;
    }
    public Integer result() { return val; }
  }
  public static void main(String[] args) {
    // Generics, varargs & boxing working together:
    List<Integer> li = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
    Integer result = reduce(li, new IntegerAdder());
    print(result);

    result = reduce(li, new IntegerSubtracter());
    print(result);

    print(filter(li, new GreaterThan<Integer>(4)));

    print(forEach(li,
      new MultiplyingIntegerCollector()).result());

    print(forEach(filter(li, new GreaterThan<Integer>(4)),
      new MultiplyingIntegerCollector()).result());

    MathContext mc = new MathContext(7);
    List<BigDecimal> lbd = Arrays.asList(
      new BigDecimal(1.1, mc), new BigDecimal(2.2, mc),
      new BigDecimal(3.3, mc), new BigDecimal(4.4, mc));
    BigDecimal rbd = reduce(lbd, new BigDecimalAdder());
    print(rbd);

    print(filter(lbd,
      new GreaterThan<BigDecimal>(new BigDecimal(3))));

    // Use the prime-generation facility of BigInteger:
    List<BigInteger> lbi = new ArrayList<BigInteger>();
    BigInteger bi = BigInteger.valueOf(11);
    for(int i = 0; i < 11; i++) {
      lbi.add(bi);
      bi = bi.nextProbablePrime();
    }
    print(lbi);

    BigInteger rbi = reduce(lbi, new BigIntegerAdder());
    print(rbi);
    // The sum of this list of primes is also prime:
    print(rbi.isProbablePrime(5));

    List<AtomicLong> lal = Arrays.asList(
      new AtomicLong(11), new AtomicLong(47),
      new AtomicLong(74), new AtomicLong(133));
    AtomicLong ral = reduce(lal, new AtomicLongAdder());
    print(ral);

    print(transform(lbd,new BigDecimalUlp()));
  }
} /* Output:
28
-26
[5, 6, 7]
5040
210
11.000000
[3.300000, 4.400000]
[11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47]
311
true
265
[0.000001, 0.000001, 0.000001, 0.000001]
*///:~
/**
* @Description: 我是从为不同类型的函数对象定义接口开始的，这些接口都是按需创建的，因为我为每个接口都开发了不同的方法，并发现了每个接口的需求。
 * Combiner抽象掉了将两个对象添加到一起的具体细节，并且只是声明他们在某种程度上被结合在一起。因此，IntegerSubtracter和IntegerAdder可以是Combiner类型。
 *
 * UnaryFunction接受单一的参数，并产生一个结果；这个参数和结果不需要是相同的类型。
 * Collector被用作“收集参数”，并且当你完成时，可以从中抽取结果。
 * UnaryPredicate将产生一个boolean类型的结果。还可以创建其他类型的函数对象，但是这些足够说明问题了。
 *
 * Functional类包含大量泛型方法，他们可以将函数对象应用于序列。
 * reduce将Combiner中的函数应用于序列中的每个元素，以产生单一的结果。
 *
 * foreach方法接受一个Collector，并将其函数应用于每个元素，但同时会忽略每次函数调用的结果。这只能被称为是副作用(这不是“功能型”编程风格，但仍旧是有用的)，
 * 或者我们可以让Collector维护内部状态，从而变成一个收集参数，就像在本例中看到的那样。
 *
 * transform通过在序列中的每个对象上调用UnaryFunction，并捕获调用结果，来产生一个列表。
 *
 * 最后，filter将UnaryPredicate应用到序列中的每个对象上，并将那些返回true的对象存储到一个List中。
 *
 * 可以定义附加的泛型函数，例如，C++ STL就具有很多这类函数。在诸如JGA这样的开源库中，这个问题也解决了。
 *
 * C++中，潜在类型机制将在你调用函数时负责协调各个操作；但java中，我们需要编写函数对象将泛型方法适配为我们特定的需求。
 * 因此，这个类展示了函数对象的各种不同的实现。例如，IntegerSubtracter和IntegerAdder通过为他们特定的类型调用恰当的方法，从而解决了相同的问题，即
 * 添加两个对象。因此，这是适配器模式和策略模式的结合。
 *
 * 在main中，你可以看到，在每个方法调用中，都会传递一个序列和适当的函数对象。还有大量的、可能会相当复杂的表达式，例如：
 *    foreach(filter(li,new GreaterThan(4))),
 *      new MultiplyingIntegerCollector()).result()
 * 这将通过选取li中大于4的所有元素而产生一个列表，然后将 MultiplyingIntegerCollector应用于所产生的列表，并抽取result()。我们不会解释剩余代码的细节，
 * 通过通读他们就可以了解他们的作用。
 *
 *
*/
