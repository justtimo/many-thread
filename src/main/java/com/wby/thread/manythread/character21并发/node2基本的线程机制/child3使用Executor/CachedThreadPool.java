package com.wby.thread.manythread.character21并发.node2基本的线程机制.child3使用Executor;//: concurrency/CachedThreadPool.java

import com.wby.thread.manythread.character21并发.node2基本的线程机制.child1定义任务.LiftOff;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Java SE5的iava.util，concurrent包中的执行器 （Executor）将为你管理Thread对象。从而简化了并发编程。Executor在客户端和任务执行之间提供了一个间接层;
 * 与客户端直接执行任务不同，这个中介对象将执行任务。Executor允许你管理异步任务的执行，而无须显式地管理线程的生命周期。Executor在Java SE5/6中是启动任务的优选方法。
 *
 * 我们可以使用Executor来代替在MoreBasicThreads.java中显示地创建Thread对象。LiftOff对象知道如何运行具体的任务，与命令设计模式一样，它暴露 了要执行的单一方法。
 * ExecutorService（具有服务生命周期的Executor，例如关闭）知道如何构建恰当的上下文来执行Runnable对象。在下面的示例中，CachedThreadPool将为每个任务都创建一个线程。
 * 注意， ExecutorService对象是使用静态的Executor方法创建的，这个方法可以确定其Executor类型∶
 */
public class CachedThreadPool {
  public static void main(String[] args) {
    ExecutorService exec = Executors.newCachedThreadPool();
    for(int i = 0; i < 5; i++)
      exec.execute(new LiftOff());
    exec.shutdown();
  }
} /* Output: (Sample)
#0(9), #0(8), #1(9), #2(9), #3(9), #4(9), #0(7), #1(8), #2(8), #3(8), #4(8), #0(6), #1(7), #2(7), #3(7), #4(7), #0(5), #1(6), #2(6), #3(6), #4(6), #0(4), #1(5), #2(5), #3(5), #4(5), #0(3), #1(4), #2(4), #3(4), #4(4), #0(2), #1(3), #2(3), #3(3), #4(3), #0(1), #1(2), #2(2), #3(2), #4(2), #0(Liftoff!), #1(1), #2(1), #3(1), #4(1), #1(Liftoff!), #2(Liftoff!), #3(Liftoff!), #4(Liftoff!),
*///:~
/**
 * 非常常见的情况是，单个的Executor被用来创建和管理系统中所有的任务。
 * 对shutdownO方法的调用可以防止新任务被提交给这个Executor，当前线程（在本例中，即驱动main（）的线程）将继续运行在shutdown（）被调用之前提交的所有任务。
 * 这个程序将在Executor中的所有任务完成之后尽快退出。
 *
 * 你可以很容易地将前面示例中的CachedThreadPool替换为不同类型的Executor。 FixedThreadPool使用了有限的线程集来执行所提交的任务∶
 */
class FixedThreadPool {
  public static void main(String[] args) {
    // Constructor argument is number of threads:
    ExecutorService exec = Executors.newFixedThreadPool(5);
    for(int i = 0; i < 5; i++)
      exec.execute(new LiftOff());
    exec.shutdown();
  }
} /* Output: (Sample)
#0(9), #0(8), #1(9), #2(9), #3(9), #4(9), #0(7), #1(8), #2(8), #3(8), #4(8), #0(6), #1(7), #2(7), #3(7), #4(7), #0(5), #1(6), #2(6), #3(6), #4(6), #0(4), #1(5), #2(5), #3(5), #4(5), #0(3), #1(4), #2(4), #3(4), #4(4), #0(2), #1(3), #2(3), #3(3), #4(3), #0(1), #1(2), #2(2), #3(2), #4(2), #0(Liftoff!), #1(1), #2(1), #3(1), #4(1), #1(Liftoff!), #2(Liftoff!), #3(Liftoff!), #4(Liftoff!),
*///:~
/**
 * 有了FixedThreadPool，你就可以一次性预先执行代价高昂的线程分配，因而也就可以限制线程的数量了。这可以节省时间，因为你不用为每个任务都固定地付出创建线程的开销。
 * 在事件驱动的系统中，需要线程的事件处理器，通过直接从池中获取线程，也可以如你所愿地尽快得到服务。你不会滥用可获得的资源，因为FixedThreadPool使用的Thread对象的数量是有界的。
 *
 * 注意，在任何线程池中，现有线程在可能的情况下，都会被自动复用。
 *
 * 尽管本书将使用CachedThreadPool，但是也应该考虑在产生线程的代码中使用 FixedThreadPool。CachedThreadPool在程序执行过程中通常会创建与所需数量相同的线程，
 * 然后在它回收旧线程时停止创建新线程，因此它是合理的Executor的首选。只有当这种方式会引发问题时，你才需要切换到FixedThreadPool。
 *
 * SingleThreadExecutor就像是线程数量为1的FixedThreadPool(它还提供了一种重要的并发保证，其他线程不会（即没有两个线程会）被并发调用。这会改变任务的加锁需求)。
 * 这对于你希望在另一个线程中连续运行的任何事物（长期存活的任务）来说，都是很有用的，例如监听进入的套接字连接的任务。它对于希望在线程中运行的短任务也同样很方便，例如，更新本地或远程 日志的小任务，或者是事件分发线程。
 *
 * 如果向SingleThreadExecutor提交了多个任务，那么这些任务将排队，每个任务都会在下一个任务开始之前运行结束，所有的任务将使用相同的线程。
 * 在下面的示例中，你可以看到每个任务都是按照它们被提交的顺序，并且是在下一个任务开始之前完成的。因此，SingleThread- Executor会序列化所有提交给它的任务，并会维护它自己（隐藏）的悬挂任务队列。
 */
class SingleThreadExecutor {
  public static void main(String[] args) {
    ExecutorService exec =
            Executors.newSingleThreadExecutor();
    for(int i = 0; i < 5; i++)
      exec.execute(new LiftOff());
    exec.shutdown();
  }
} /* Output:
#0(9), #0(8), #0(7), #0(6), #0(5), #0(4), #0(3), #0(2), #0(1), #0(Liftoff!), #1(9), #1(8), #1(7), #1(6), #1(5), #1(4), #1(3), #1(2), #1(1), #1(Liftoff!), #2(9), #2(8), #2(7), #2(6), #2(5), #2(4), #2(3), #2(2), #2(1), #2(Liftoff!), #3(9), #3(8), #3(7), #3(6), #3(5), #3(4), #3(3), #3(2), #3(1), #3(Liftoff!), #4(9), #4(8), #4(7), #4(6), #4(5), #4(4), #4(3), #4(2), #4(1), #4(Liftoff!),
*///:~
/**
 * 作为另一个示例，假设你有大量的线程，那它们运行的任务将使用文件系统。你可以用SingleThreadExecutor来运行这些线程，以确保任意时刻在任何线程中都只有唯一的任务在运行。
 * 在这种方式中，你不需要在共享资源上处理同步（同时不会过度使用文件系统）。有时更好的解决方案是在资源上同步（你将在本章稍后学习），但是SingleThreadExecutor可以让你省去只是为了维持某些事物的原型而进行的各种协调努力。
 * 通过序列化任务，你可以消除对序列化对象的需求。
 *
 * 练习3∶（1）使用本节展示的各种不同类型的执行器重复练习1。
 * 练习4∶ （1）使用本节展示的各种不同类型的执行器重复练习2。
 */
