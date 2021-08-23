package com.wby.thread.manythread.character21并发.node2基本的线程机制.child2Thread类;//: concurrency/BasicThreads.java
// The most basic use of the Thread class.

import com.wby.thread.manythread.character21并发.node2基本的线程机制.child1定义任务.LiftOff;

/**
 * 将Runnable对象转变为工作任务的传统方式是把它提交给一个Thread构造器，下面的示例展示了如何使用Thread来驱动LiftOff对象;
 */
public class BasicThreads {
  public static void main(String[] args) {
    Thread t = new Thread(new LiftOff());
    t.start();
    System.out.println("Waiting for LiftOff");
  }
} /* Output: (90% match)
Waiting for LiftOff
#0(9), #0(8), #0(7), #0(6), #0(5), #0(4), #0(3), #0(2), #0(1), #0(Liftoff!),
*///:~
/**
 * Thread构造器只需要一个Runnable对象。调用Thread对象的start（）方法为该线程执行必需的初始化操作，然后调用Runnable的run0方法，以便在这个新线程中启动该任务。
 * 尽管startO看起来是产生了一个对长期运行方法的调用，但是从输出中可以看到，startO迅速地返回了，因为Waiting for LiftoOff消息在倒计时完成之前就出现了。
 * 实际上，你产生的是对LiftOff.runO的方法调用，并且这个方法还没有完成，但是因为LiftOff。runO是由不同的线程执行的，
 * 因此你仍旧可以执行mainO线程中的其他操作（这种能力并不局限于main（）线程，任何线程都可以启动另一个线程）。因此，程序会同时运云行两个方法，mainO和LiftOffrunO是程序中与其他线程"同时执行的代码。
 *
 * 你可以很容易地添加更多的线程去驱动更多的任务。下面，你可以看到所有任务彼此之间是如何互相呼应的(在本例中，单一线程（main））在创建所有的LiftOff线程。但是，如果多个线程在创建LiftOf线程，那么就有
 * 可能会有多个LiftOf拥有相同的id。在本章稍后你会了解到这是为什么。)∶
 */
class MoreBasicThreads {
  public static void main(String[] args) {
    for(int i = 0; i < 5; i++)
      new Thread(new LiftOff()).start();
    System.out.println("Waiting for LiftOff");
  }
} /* Output: (Sample)
Waiting for LiftOff
#0(9), #1(9), #2(9), #3(9), #4(9), #0(8), #1(8), #2(8), #3(8), #4(8), #0(7), #1(7), #2(7), #3(7), #4(7), #0(6), #1(6), #2(6), #3(6), #4(6), #0(5), #1(5), #2(5), #3(5), #4(5), #0(4), #1(4), #2(4), #3(4), #4(4), #0(3), #1(3), #2(3), #3(3), #4(3), #0(2), #1(2), #2(2), #3(2), #4(2), #0(1), #1(1), #2(1), #3(1), #4(1), #0(Liftoff!), #1(Liftoff!), #2(Liftoff!), #3(Liftoff!), #4(Liftoff!),
*///:~
/**
 * 输出说明不同任务的执行在线程被换进换出时混在了一起。这种交换是由线程调度器自动控制的。如果在你的机器上有多个处理器，线程调度器将会在这些处理器之间默默地分发线程9。
 *
 * 这个程序一次运行的结果可能与另一次运行的结果不同，因为线程调度机制是非确定性的。事实上，你可以看到，在某个版本的JDK与下个版本之间，这个简单程序的输出会产生巨大的差异。
 * 例如，较早的JDK不会频繁对时间切片，因此线程1可能会首先循环到尽头，然后线程2会经历其所有循环，等等。这实际上与调用一个例程去同时执行所有的循环一样，只是启动所有线程的代价要更加高昂。
 * 较晚的JDK看起来会产生更好的时间切片行为，因此每个线程看起来都会获得更加正规的服务。通常，Sun并为提及这些种类的JDK的行为变化，因此你不能依赖于任何线程行为的一致性。
 * 最好的方式是在编写使用线程的代码时，尽可能地保守。
 *
 * 当main（）创建Thread对象时，它并没有捕获任何对这些对象的引用。在使用普通对象时，这对于垃圾回收来说是一场公平的游戏，但是在使用Thread时，情况就不同了。
 * 每个Thread都"注册" 了它自己，因此确实有一个对它的引用，而且在它的任务退出其run（）并死亡之前，垃圾回收器无法清除它。你可以从输出中看到，这些任务确实运行到了结束，
 * 因此，一个线程会创建一个单独的执行线程，在对startO的调用完成之后，它仍旧会继续存在。
 *
 * 练习1∶（2）实现一个Runnable。在runO）内部打印一个消息，然后调用yieldO。重复这个操作三次，然后从run（）中返回。在构造器中放置一条启动消息，并且放置一条在任务终止时的关闭消息。使用线程创建大量的这种任务并驱动它们。
 * 练习2;（2）遵循generic/Fibonacci.java的形式，创建一个任务，它可以产生由n个斐波纳契数字组成的序列，其中n是通过任务的构造器而提供的。使用线程创建大量的这种任务并驱动它们。
 */
