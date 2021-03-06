package com.wby.thread.manythread.character21并发.node7新类库中的构件.child2CyclicBarrier;//: concurrency/HorseRace.java
// Using CyclicBarriers.

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
 * CyclicBarrier适用干这样的情况;你希望创建一组任务。它们并行地执行工作，然后在进行下一个步骤之前等待，直至所有任务都完成（看起来有些像joinO））。
 * 它使得所有的并行任务都将在栅栏处列队，因此可以一致地向前移动。这非常像CountDownLatch，只是CountDownLatch是只触发一次的事件，而CyclicBarrier可以多次重用。
 *
 * 从刚开始接触计算机时开始，我就对仿真着了迷，而并发是使仿真成为可能的一个关键因素。记得我最开始编写的一个程序就是一个仿真∶一个用BASIC编写的（由于文件名的限制而）命名为HOSRAC.BAS的赛马游戏。
 * 下面是那个程序的面向对象的多线程版本，其中使用了 CyclicBarrier;
 */
class Horse implements Runnable {
  private static int counter = 0;
  private final int id = counter++;
  private int strides = 0;
  private static Random rand = new Random(47);
  private static CyclicBarrier barrier;
  public Horse(CyclicBarrier b) { barrier = b; }
  public synchronized int getStrides() { return strides; }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        synchronized(this) {
          strides += rand.nextInt(3); // Produces 0, 1 or 2
        }
        barrier.await();
      }
    } catch(InterruptedException e) {
      // A legitimate way to exit
    } catch(BrokenBarrierException e) {
      // This one we want to know about
      throw new RuntimeException(e);
    }
  }
  public String toString() { return "Horse " + id + " "; }
  public String tracks() {
    StringBuilder s = new StringBuilder();
    for(int i = 0; i < getStrides(); i++)
      s.append("*");
    s.append(id);
    return s.toString();
  }
}

public class HorseRace {
  static final int FINISH_LINE = 75;
  private List<Horse> horses = new ArrayList<Horse>();
  private ExecutorService exec =
    Executors.newCachedThreadPool();
  private CyclicBarrier barrier;
  public HorseRace(int nHorses, final int pause) {
    barrier = new CyclicBarrier(nHorses, new Runnable() {
      public void run() {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < FINISH_LINE; i++)
          s.append("="); // The fence on the racetrack
        print(s);
        for(Horse horse : horses)
          print(horse.tracks());
        for(Horse horse : horses)
          if(horse.getStrides() >= FINISH_LINE) {
            print(horse + "won!");
            exec.shutdownNow();
            return;
          }
        try {
          TimeUnit.MILLISECONDS.sleep(pause);
        } catch(InterruptedException e) {
          print("barrier-action sleep interrupted");
        }
      }
    });
    for(int i = 0; i < nHorses; i++) {
      Horse horse = new Horse(barrier);
      horses.add(horse);
      exec.execute(horse);
    }
  }
  public static void main(String[] args) {
    int nHorses = 7;
    int pause = 200;
    if(args.length > 0) { // Optional argument
      int n = new Integer(args[0]);
      nHorses = n > 0 ? n : nHorses;
    }
    if(args.length > 1) { // Optional argument
      int p = new Integer(args[1]);
      pause = p > -1 ? p : pause;
    }
    new HorseRace(nHorses, pause);
  }
} /* (Execute to see output) *///:~
/**
 * 可以向CyclicBarrier提供一个"栅栏动作"，它是一个Runnable，当计数值到达0时自动执行——这是CyclicBarrier和CountDownLatch之间的另一个区别。这里，栅栏动作是作为匿名内部类创建的，它被提交给了CyclicBarrier的构造器。
 *
 * 我试图让每匹马都打E自已。但是之后的显示顺序取决干仟务管理器。CvclicBarrier使得每匹马都要执行为了向前移动所必需执行的所有工作，然后必须在栅栏处等待其他所有的马都准备完毕。
 * 当所有的马都向前移动时，CvclicBarrier将自动调用Runnable栅栏动作任务，按顺序显示马和终点线的位置。
 *
 * 一旦所有的任务都越过了栅栏，它就会自动地为下一回合比赛做好准备。
 * 为了展示这个非常简单的动画效果。你需要将控制台视窗的尺寸调整为小到只有马时，才会展示出来。
 */
