//: interfaces/classprocessor/Apply.java
package com.wby.thread.manythread.character9接口.node3完全解耦;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
/**
 * 只要一个方法操作的是类而非接口，那么你就只能使用这个类及其子类。如果你想要将这个方法应用于不在此继承结构中的某个类，那么你就会触霉头了。
 * 接口可以在很大程度上放宽这种限制，因此，它使得我们可以编写可复用性更好的代码。
 * <p>
 * 例如，假设有一个Processor类，它有一个name（O方法;另外还有一个processO方法，该方法接受输入参数，修改它的值，然后产生输出。
 * 这个类做为基类而被扩展，用来创建各种不同类型的Processor。在本例中，Processor的子类将修改String对象（注意，返回类型可以是协变类型，而非参数类型）;
 */
public class Apply {
  public static void process(Processor p, Object s) {
    print("Using Processor " + p.name());
    print(p.process(s));
  }
  public static String s =
    "Disagreement with beliefs is by definition incorrect";
  public static void main(String[] args) {
    process(new Upcase(), s);
    process(new Downcase(), s);
    process(new Splitter(), s);
  }
} /* Output:
Using Processor Upcase
DISAGREEMENT WITH BELIEFS IS BY DEFINITION INCORRECT
Using Processor Downcase
disagreement with beliefs is by definition incorrect
Using Processor Splitter
[Disagreement, with, beliefs, is, by, definition, incorrect]
*///:~
/**
 * Apply.processO方法可以接受任何类型的Processor，并将其应用到一个Object对象上，然后打印结果。像本例这样，创建一个能够根据所传递的参数对象的不同而具有不同行为的方法，被称为策略设计模式。
 * 这类方法包含所要执行的算法中固定不变的部分，而"策略"包含变化的部分。策略就是传递进去的参数对象，它包含要执行的代码。这里，Processor对象就是一个策略，在main）中可以看到有三种不同类型的策略应用到了String类型的s对象上。
 *
 * split（）方法是String类的一部分，它接受String类型的对象，并以传递进来的参数作为边界，将该String对象分隔开，然后返回一个数组String【】。它在这里被用来当作创建String数组的快捷方式。
 *
 * 现在假设我们发现了一组电子滤波器，它们看起来好像适用于Apply.process（O方法∶
 *  见Filter包
 */

/**
 * Filter与Processor具有相同的接口元素，但是因为它并非继承自Processor——因为Filter类的创建者压根不清楚你想要将它用作Processor——因此你不能将Filter用于Apply.processO方法，
 * 即便这样做可以正常运行。这里主要是因为Applyprocess（）方法和Processor之间的耦合过紧，已经超出了所需要的程度，这就使得应该复用Apply.processO的代码时，复用却被禁止了。
 * 另外还需要注意的是它们的输入和输出都是Waveform。
 *
 * 但是，如果Processor是一个接口，那么这些限制就会变得松动，使得你可以复用结构该接口的Apply.processO。下面是Processor和Apply的修改版本∶
 *    见复用代码1
 */



/**
 * 但是，你经常碰到的情况是你无法修改你想要使用的类。例如，在电子滤波器的例子中，类库是被发现而非被创建的。在这些情况下，可以使用适配器设计模式。适配器中的代码将接受你所拥有的接口，并产生你所需要的接口，就像下面这样∶
 *    见适配器模式
 */


/**
 * 在这种使用适配器的方式中，FilterAdapter的构造器接受你所拥有的接口Filter，然后生成具有你所需要的Processor接口的对象。你可能还注意到了，在FilterAdapter类中用到了代理。
 *
 * 将接口从具体实现中解耦使得接口可以应用于多种不同的具体实现，因此代码也就更具可复用性。
 */
