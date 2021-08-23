package com.wby.thread.manythread.character21并发.node3共享受限资源.child1不正确地访问资源;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/8/23 16:01
 * @Description:
 */
public class EvenChecker implements Runnable {
    private IntGenerator generator;
    private final int id;
    public EvenChecker(IntGenerator g, int ident) {
        generator = g;
        id = ident;
    }
    public void run() {
        while(!generator.isCanceled()) {
            int val = generator.next();
            if(val % 2 != 0) {
                System.out.println(val + " not even!");
                generator.cancel(); // Cancels all EvenCheckers
            }
        }
    }
    // Test any type of IntGenerator:
    public static void test(IntGenerator gp, int count) {
        System.out.println("Press Control-C to exit");
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < count; i++)
            exec.execute(new EvenChecker(gp, i));
        exec.shutdown();
    }
    // Default value for count:
    public static void test(IntGenerator gp) {
        test(gp, 10);
    }
} ///:~
/**
 * 注意，在本例中可以被撤销的类不是Runnable，而所有依赖于IntGenerator对象的 EvenChecker任务将测试它，以查看它是否已经被撤销，正如你在runO）中所见。
 * 通过这种方式，共享公共资源（IntGenerator）的任务可以观察该资源的终止信号。这可以消除所谓竞争条件，即两个或更多的任务竞争响应某个条件，因此产生冲突或不一致结果的情况。
 * 你必须仔细考虑并防范并发系统失败的所有可能途径，例如，一个任务不能依赖于另一个任务，因为任务关闭的顺序无法得到保证。这里，通过使任务依赖于非任务对象，我们可以消除潜在的竞争条件。
 *
 * testO方法通过启动大量使用相同的IntGenerator的EvenChecker，设置并执行对任何类型的 IntGenerator的测试。如果IntGenerator引发失败，那么testO将报告它并返回，否则，你必须按下Control-C来终止它。
 *
 * EvenChecker任务总是读取和测试从与其相关的IntGenerator返回的值。注意，如果 generator.isCanceled（为true，则runO将返回，这将告知EvenChecker.testO）中的Executor该任务完成了。
 * 任何EvenChecker任务都可以在与其相关联的IntGenerator上调用cancelO，这将导致所有其他使用该IntGenerator的EvenChecker得体地关闭。在后面各节中，你将看到Java包含的用于线程终止的各种更通用的机制。
 *
 * 我们看到的第一个IntGenerator有一个可以产生一系列偶数值的nextO方法∶
 */
class EvenGenerator extends IntGenerator {
    private int currentEvenValue = 0;
    public int next() {
        ++currentEvenValue; // Danger point here!
        ++currentEvenValue;
        return currentEvenValue;
    }
    public static void main(String[] args) {
        EvenChecker.test(new EvenGenerator());
    }
} /* Output: (Sample)
Press Control-C to exit
89476993 not even!
89476993 not even!
*///:~
/**
 * 一个任务有可能在另一个任务执行第一个对currentEvenValue的递增操作之后，但是没有执行第二个操作之前，调用nextO方法（即，代码中被注释为"Danger point here!"的地方）。
 * 这将使这个值处于"不恰当"的状态。为了证明这是可能发生的，EvenChecker.testO创建了—一组 EvenChecker对象，以连续地读取并输出同一个EvenGenerator，并测试检查每个数值是否都是偶数。
 * 如果不是，就会报告错误，而程序也将关闭。
 *
 * 这个程序最终将失败，因为各个EvenChecker任务在EvenGenerator处于"不恰当的"状态时，仍能够访问其中的信，息。但是，根据你使用的特定的操作系统和其他实现细节，
 * 直到 EvenCenerator完成多次循环之前，这个问题都不会被探测到。如果你希望更快地发现失败，可以尝试着将对vieldO）的调用放置到第一个和第二个递增操作之间。
 * 这只是并发程序的部分间题——如果失败的概率非常低，那么即使存在缺陷，它们也可能看起来是正确的。
 *
 * 有一点很重要，那就是要注意到递增程序自身也需要多个步骤，并且在递增过程中任务可能会被线程机制挂起——也就是说，在Java中，递增不是原子性的操作。因此，如果不保护任务，即使单一的递增也不是安全的。
 */

