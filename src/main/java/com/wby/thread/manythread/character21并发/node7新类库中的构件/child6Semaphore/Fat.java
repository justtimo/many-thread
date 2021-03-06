package com.wby.thread.manythread.character21并发.node7新类库中的构件.child6Semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
import static com.wby.thread.manythread.net.mindview.util.Print.printnb;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/8/23 17:18
 * @Description:
 */
public class Fat {
    private volatile double d; // Prevent optimization
    private static int counter = 0;
    private final int id = counter++;
    public Fat() {
        // Expensive, interruptible operation:
        for(int i = 1; i < 10000; i++) {
            d += (Math.PI + Math.E) / (double)i;
        }
    }
    public void operation() { System.out.println(this); }
    public String toString() { return "Fat id: " + id; }
} ///:~
/**
 * 我们在池中管理这些对象，以限制这个构造器所造成的影响。我们可以创建一个任务，它将签出Fat对象，持有一段时间之后再将它们签入，以此来测试Pool这个类∶
 */
// A task to check a resource out of a pool:
class CheckoutTask<T> implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private Pool<T> pool;
    public CheckoutTask(Pool<T> pool) {
        this.pool = pool;
    }
    public void run() {
        try {
            T item = pool.checkOut();
            print(this + "checked out " + item);
            TimeUnit.SECONDS.sleep(1);
            print(this +"checking in " + item);
            pool.checkIn(item);
        } catch(InterruptedException e) {
            // Acceptable way to terminate
        }
    }
    public String toString() {
        return "CheckoutTask " + id + " ";
    }
}

class SemaphoreDemo {
    final static int SIZE = 25;
    public static void main(String[] args) throws Exception {
        final Pool<Fat> pool =
                new Pool<Fat>(Fat.class, SIZE);
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < SIZE; i++)
            exec.execute(new CheckoutTask<Fat>(pool));
        print("All CheckoutTasks created");
        List<Fat> list = new ArrayList<Fat>();
        for(int i = 0; i < SIZE; i++) {
            Fat f = pool.checkOut();
            printnb(i + ": main() thread checked out ");
            f.operation();
            list.add(f);
        }
        Future<?> blocked = exec.submit(new Runnable() {
            public void run() {
                try {
                    // Semaphore prevents additional checkout,
                    // so call is blocked:
                    pool.checkOut();
                } catch(InterruptedException e) {
                    print("checkOut() Interrupted");
                }
            }
        });
        TimeUnit.SECONDS.sleep(2);
        blocked.cancel(true); // Break out of blocked call
        print("Checking in objects in " + list);
        for(Fat f : list)
            pool.checkIn(f);
        for(Fat f : list)
            pool.checkIn(f); // Second checkIn ignored
        exec.shutdown();
    }
} /* (Execute to see output) *///:~
/**
 * 在main（）中，创建了一个持有Fat对象的Pool，而一组CheckoutTask则开始操练这个Pool。然后，mainO线程签出池中的Fat对象，但是并不签入它们。—日池中所有的对象都被签出.
 * Semaphore将不再允许执行任何签出操作。blocked的run（）方法因此会被阻塞，2秒钟之后， cancel（）方法被调用，以此来挣脱Future的束缚。注意，冗余的签入将被Pool忽略。
 *
 * 这个示例依赖于Pool的客户端严格地并愿意签入所持有的对象，当其工作时，这是最简单的解决方案。
 * 如果你无法总是可以依赖于此，《Thinking in Patterns》（在www.MindView.net处）深入探讨了对已经签出对象池的对象的管理方式。
 */

