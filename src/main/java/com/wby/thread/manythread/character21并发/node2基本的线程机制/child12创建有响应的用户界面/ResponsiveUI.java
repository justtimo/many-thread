package com.wby.thread.manythread.character21并发.node2基本的线程机制.child12创建有响应的用户界面;//: concurrency/ResponsiveUI.java
// User interface responsiveness.
// {RunByHand}

/**
 * 如前所述，使用线程的动机之一就是建立有响应的用户界面。尽管我们要到第22章才接触到图形用户界面，但下面还是给出了个基于控制台用户界面的简单教学示例。
 * 下面的例子有两个版本∶ 一个关注于运算，所以不能读取控制台输入;另一个把运算放在任务里单独运行，此时就可以在进行运算的同时监听控制台输入。
 */
class UnresponsiveUI {
  private volatile double d = 1;
  public UnresponsiveUI() throws Exception {
    while(d > 0)
      d = d + (Math.PI + Math.E) / d;
    System.in.read(); // Never gets here
  }
}

public class ResponsiveUI extends Thread {
  private static volatile double d = 1;
  public ResponsiveUI() {
    setDaemon(true);
    start();
  }
  public void run() {
    while(true) {
      d = d + (Math.PI + Math.E) / d;
    }
  }
  public static void main(String[] args) throws Exception {
    //! new UnresponsiveUI(); // Must kill this process
    new ResponsiveUI();
    System.in.read();
    System.out.println(d); // Shows progress
  }
} ///:~
/**
 * UnresponsiveUI在一个无限的while循环里执行运算，显然程序不可能到达读取控制台输入的那一行（编译器被欺骗了，相信while的条件使得程序能到达读取控制台输入的那一行）。
 * 如果把建立UnresponsiveUI的那一行的注释解除掉再运行程序，那么要终止它的话，就只能杀死这个进程。
 *
 * 要想让程序有响应，就得把计算程序放在run（O方法中，这样它就能让出处理器给别的程序。
 *
 * 当你按下"回车"键的时候，可以看到计算确实在作为后台程序运行，同时还在等待用户输入
 */
