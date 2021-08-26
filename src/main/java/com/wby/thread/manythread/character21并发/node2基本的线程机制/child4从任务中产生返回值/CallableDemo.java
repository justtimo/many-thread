package com.wby.thread.manythread.character21并发.node2基本的线程机制.child4从任务中产生返回值;//: concurrency/CallableDemo.java

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Runnable是执行工作的独立任务，但是它不返回任何值。如果你希望任务在完成时能够返回一个值，那么可以实现Callable接口而不是Runnable接口。
 * 在Java SE5中引入的Callabel是一种具有类型参数的泛型，它的类型参数表示的是从方法callO（而不是run））中返回的值，并且必须使用ExecutorService.submitO方法调用它，下面是一个简单示例∶
 */
class TaskWithResult implements Callable<String> {
  private int id;
  public TaskWithResult(int id) {
    this.id = id;
  }
  public String call() {
    return "result of TaskWithResult " + id;
  }
}

public class CallableDemo {
  public static void main(String[] args) {
    ExecutorService exec = Executors.newCachedThreadPool();
    ArrayList<Future<String>> results =
      new ArrayList<Future<String>>();
    for(int i = 0; i < 10; i++)
      results.add(exec.submit(new TaskWithResult(i)));
    for(Future<String> fs : results)
      try {
        if (fs.isDone()){
          System.out.println("任务结束");
        }
        // get() blocks until completion:
        System.out.println(fs.get());
      } catch(InterruptedException e) {
        System.out.println(e);
        return;
      } catch(ExecutionException e) {
        System.out.println(e);
      } finally {
        exec.shutdown();
      }
  }
} /* Output:
result of TaskWithResult 0
result of TaskWithResult 1
result of TaskWithResult 2
result of TaskWithResult 3
result of TaskWithResult 4
result of TaskWithResult 5
result of TaskWithResult 6
result of TaskWithResult 7
result of TaskWithResult 8
result of TaskWithResult 9
*///:~
/**
 * submit（O方法会产生Future对象，它用Callable返回结果的特定类型进行了参数化。你可以用isDoneO方法来查询Future是否已经完成。当任务完成时，它具有一个结果，你可以调用getO）方法来获取该结果。
 * 你也可以不用isDoneO进行检查就直接调用get（O，在这种情况下，getO将阻塞，直至结果准备就绪。你还可以在试图调用getO来获取结果之前，先调用具有超时的get（），或者调用isDone（）来查看任务是否完成。
 *
 * 练习5∶（2）修改练习2，使得计算所有斐波纳契数字的数值总和的任务成为Callable。创建多个任务并显示结果。
 */
