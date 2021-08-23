package com.wby.thread.manythread.character21并发.node2基本的线程机制.child1定义任务;//: concurrency/LiftOff.java
// Demonstration of the Runnable interface.

/**
 * 线程可以驱动任务，因此你需要一种描述任务的方式，这可以由Runnable接口来提供。要想定义任务，只需实现Runnable接口并编写runO方法，使得该任务可以执行你的命令。
 * 例如，下面的LiftOff任务将显示发射之前的倒计时;
 */
public class LiftOff implements Runnable {
  protected int countDown = 10; // Default
  private static int taskCount = 0;
  private final int id = taskCount++;
  public LiftOff() {}
  public LiftOff(int countDown) {
    this.countDown = countDown;
  }
  public String status() {
    return "#" + id + "(" +
      (countDown > 0 ? countDown : "Liftoff!") + "), ";
  }
  public void run() {
    while(countDown-- > 0) {
      System.out.print(status());
      Thread.yield();
    }
  }
} ///:~
/**
 * 标识符id可以用来区分任务的多个实例，它是final的，因为它一旦被初始化之后就不希望被修改。
 *
 * 任务的run（）方法通常总会有某种形式的循环，使得任务一直运行下去直到不再需要，所以要设定跳出循环的条件（有一种选择是直接从runO返回）。
 * 通常，runO被写成无限循环的形式，这就意味着，除非有某个条件使得runO终止，否则它将永远运行下去（在本章后面将会看到如何安全地终止线程）。
 *
 * 在runO中对静态方法Thread.yieldO的调用是对线程调度器（Java线程机制的一部分，可以将CPU从一个线程转移给另一个线程）的一种建议，它在声明∶"我已经执行完生命周期中最重要的部分了，
 * 此刻正是切换给其他任务执行一段时间的大好时机。"这完全是选择性的，但是这里使用它是因为它会在这些示例中产生更加有趣的输出;你更有可能会看到任务换进换出的证据。
 *
 * 在下面的实例中，这个任务的run（）不是由单独的线程驱动的，它是在main（）中直接调用的（实际上，这里仍旧使用了线程，即总是分配给mainO）的那个线程）∶
 */
class MainThread {
  public static void main(String[] args) {
    LiftOff launch = new LiftOff();
    launch.run();
  }
} /* Output:
#0(9), #0(8), #0(7), #0(6), #0(5), #0(4), #0(3), #0(2), #0(1), #0(Liftoff!),
*///:~
/**
 * 当从Runnable导出一个类时，它必须具有run（）方法，但是这个方法并无特殊之处——它不会产生任何内在的线程能力。
 * 要实现线程行为，你必须显式地将一个任务附着到线程上。
 */
