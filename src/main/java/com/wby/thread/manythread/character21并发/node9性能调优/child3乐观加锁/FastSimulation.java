package com.wby.thread.manythread.character21并发.node9性能调优.child3乐观加锁;//: concurrency/FastSimulation.java

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
 * 尽管Atomic对象将执行像decrementAndGetO这样的原子性操作，但是某些Atomic类还允许你执行所谓的"乐观加锁"。这意味着当你执行某项计算时，实际上没有使用互斥，但是在这项计算完成，
 * 并且你准备更新这个Atomic对象时，你需要使用一个称为compareAndSetO的方法。你将旧值和新值一起提交给这个方法，如果旧值与它在Atomic对象中发现的值不一致，
 * 那么这个操作就失败———这意味着某个其他的任务已经于此操作执行期间修改了这个对象。记住，我们在正常情况下将使用互斥（synchronized或Lock）来防止多个任务同时修改一个对象，
 * 但是这里我们是"乐观的"，因为我们保持数据为未锁定状态，并希望没有任何其他任务插入修改它。所有这些又都是以性能的名义执行的——通过使用Atomic来替代synchronized或Lock，可以获得性能上的好处。
 *
 * 如果compareAndSet（O操作失败会发生什么?这正是棘手的地方，也是你在应用这项技术时的受限之处，即只能针对能够吻合这些需求的问题。如果compareAndSetO失败，那么就必须决定做些什么，
 * 这是一个非常重要的问题，因为如果不能执行某些恢复操作，那么你就不能使用这项技术，从而必须使用传统的互斥。你可能会重试这个操作，如果在第二次成功，那么万事大吉;或者可能会忽略这次失败，
 * 直接结束——在某些仿真中，如果数据点丢失，在重要的框架中，这就是最终需要做的事情（当然，你必须很好地理解你的模型，以了解情况是否确实如此）。
 *
 * 考虑一个假想的仿真，它由长度为30的100000个基因构成，这可能是某种类型的遗传算法的起源。假设伴随着遗传算法的每次"进化"，都会发生某些代价高昂的计算，因此你决定使用一台多处理器机器来分布这些任务以提高性能。
 * 另外，你将使用Atomic对象而不是Lock对象来防止互斥开销（当然，一开始，你使用synchronized关键字以最简单的方式编写了代码。一旦你运行该程序，发现它太慢了，并开始应用性能调优技术，而此时你也只能写出这样的解决方案）。
 * 因为你的模型的特性，使得如果在计算过程中产生冲突，那么发现冲突的任务将直接忽略它，并不会更新它的值。下面是这个示例的代码∶
*/
public class FastSimulation {
  static final int N_ELEMENTS = 100000;
  static final int N_GENES = 30;
  static final int N_EVOLVERS = 50;
  static final AtomicInteger[][] GRID =
    new AtomicInteger[N_ELEMENTS][N_GENES];
  static Random rand = new Random(47);
  static class Evolver implements Runnable {
    public void run() {
      while(!Thread.interrupted()) {
        // Randomly select an element to work on:
        int element = rand.nextInt(N_ELEMENTS);
        for(int i = 0; i < N_GENES; i++) {
          int previous = element - 1;
          if(previous < 0) previous = N_ELEMENTS - 1;
          int next = element + 1;
          if(next >= N_ELEMENTS) next = 0;
          int oldvalue = GRID[element][i].get();
          // Perform some kind of modeling calculation:
          int newvalue = oldvalue +
            GRID[previous][i].get() + GRID[next][i].get();
          newvalue /= 3; // Average the three values
          if(!GRID[element][i]
            .compareAndSet(oldvalue, newvalue)) {
            // Policy here to deal with failure. Here, we
            // just report it and ignore it; our model
            // will eventually deal with it.
            print("Old value changed from " + oldvalue);
          }
        }
      }
    }
  }
  public static void main(String[] args) throws Exception {
    ExecutorService exec = Executors.newCachedThreadPool();
    for(int i = 0; i < N_ELEMENTS; i++)
      for(int j = 0; j < N_GENES; j++)
        GRID[i][j] = new AtomicInteger(rand.nextInt(1000));
    for(int i = 0; i < N_EVOLVERS; i++)
      exec.execute(new Evolver());
    TimeUnit.SECONDS.sleep(5);
    exec.shutdownNow();
  }
} /* (Execute to see output) *///:~
/**
 * 所有元素都被置于数组内，这被认为有助于提高性能（这个假设将在一个练习中进行测试）。每个Evolver对象会用它前一个元素和后一个元素来平均它的值，如果在更新时失败，那么将直接打印这个值并继续执行。
 * 注意，在这个程序中没有出现任何互斥。
 *
 * 练习39∶（6）FastSimulation.java是否作出了合理的假设?试着将数组从普通的inti修改为 AtomicInteger，并使用Lock互斥。比较这两个版本的程序的差异。
 */
























