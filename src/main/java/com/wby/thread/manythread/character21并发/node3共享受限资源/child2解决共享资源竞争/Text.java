package com.wby.thread.manythread.character21并发.node3共享受限资源.child2解决共享资源竞争;

import com.wby.thread.manythread.character21并发.node3共享受限资源.child1不正确地访问资源.EvenChecker;
import com.wby.thread.manythread.character21并发.node3共享受限资源.child1不正确地访问资源.IntGenerator;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 前面的示例展示了使用线程时的一个基本问题;你永远都不知道—一个线程何时在运行。想象—下。你坐在桌边手拿叉子，正要去叉盘子中的最后一片食物，当你的叉子就要够着它时，
 * 这片食物突然消失了，，因为你的线程被挂起了，而另一个餐者进入并吃掉了它。这正是在你编写并发程序时需要处理的问题。对于并发工作，你需要某种方式来防止两个任务访问相同的资源，至少在关键阶段不能出现这种情况。
 *
 * 防止这种冲突的方法就是当资源被一个任务使用时，在其上加锁。第一个访问某项资源的任务必须锁定这项资源，使其他任务在其被解锁之前，就无法访问它了，而在其被解锁之时，另一个任务就可以锁定并使用它，
 * 以此类推。如果汽车前排座位是受限资源，那么大喊着"冲呀!""的孩子就会（在这次旅途过程中）获取其上的锁。
 *
 * 基本上所有的并发模式在解决线程冲突问题的时候，都是采用序列化访问共享资源的方案。这意味着在给定时刻只允许一个任务访问共享资源。通常这是通过在代码前面加上一条锁语句，
 * 来实现的，这就使得在一段时间内只有一个任务可以运行这段代码。因为锁语句产生了一种互相排斥的效果，所以这种机制常常称为互斥量（mutex）。
 *
 * 考虑一下屋子里的浴室;多个人（即多个由线程驱动的任务）都希望能单独使用浴室（即共享资源）。为了使用浴室，一个人先敲门，看看是否能使用。如果没人的话，他就进入浴室并锁上门。
 * 这时其他人要使用浴室的话，就会被"阻挡"，所以他们要在浴室门口等待，直到浴室可以使用。
 *
 * 当浴室使用完毕，就该把浴室给其他人使用了（别的任务就可以访问资源了），这个比喻就有点不太准确了。事实上，人们并没有排队，我们也不能确定谁将是下一个使用浴室的人，
 * 因为线程调度机制并不是确定性的。实际情况是;等待使用浴室的∶人们簇拥在浴室门口。当锁住浴室门的那个人打开锁准备离开的时候，离门最近的那个人可能进入浴室。
 * 如前所述，可以通过yield（O）和setPriorityO）来给线程调度器提供建议，但这些建议未必会有多大效果，这取决于你的具体平台和JVM实现。
 *
 * Java以提供关键字synchronized的形式，为防止资源冲突提供了内置支持。当任务要执行被 synchronized关键字保护的代码片段的时候，它将检查锁是否可用，然后获取锁，执行代码，释放锁。
 *
 * 共享资源一般是以对象形式存在的内存片段，但也可以是文件、输入/输出端口，或者是打印机。要控制对共享资源的访问，得先把它包装进一个对象。然后把所有要访问这个资源的方法标记为synchronized。
 * 如果某个任务处于一个对标记为synchronized的方法的调用中，那么在这个线程从该方法返回之前，其他所有要调用类中任何标记为synchronized方法的线程都会被阻塞。
 *
 * 在生成偶数的代码中，你已经看到了，你应该将类的数据成员都声明为private的，而且只能通过方法来访问这些数据;所以可以把方法标记为synchronized来防止资源冲突。下面是声明 Synchronized方法的方式∶
 *      synchronized void f(){........}
 *      synchronized void g(){.........}
 * 所有对象都自动含有单一的锁（也称为监视器）。当在对象上调用其任意synchronized方法的时候，此对象都被加锁，这时该对象上的其他synchronized方法只有等到前一个方法调用完毕并释放了锁之后才能被调用。
 * 对于前面的方法，如果某个任务对对象调用了fO，对于同一个对象而言，就只能等到fO调用结束并释放了锁之后，其他任务才能调用f（O和gO。
 * 所以，对于某个特定对象来说，其所有synchronized方法共享同—一个锁，这可以被用来防止多个任务同时访问被编码为对象内存。
 *
 * 注意，在使用并发时，将域设置为private是非常重要的，否则，synchronized关键字就不能防止其他任务直接访问域，这样就会产生冲突。
 *
 * 一个任务可以多次获得对象的锁。如果一个方法在同一个对象上调用了第二个方法，后者又调用了同一对象上的另一个方法，就会发生这种情况。JVM负责跟踪对象被加锁的次数。
 * 如果一个对象被解锁（即锁被完全释放），其计数变为0。在任务第一次给对象加锁的时候，计数变为1。每当这个相同的任务在这个对象上获得锁时，计数都会递增。
 * 显然，只有首先获得了锁的任务才能允许继续获取多个锁。每当任务离开一个synchronized方法，计数递减，当计数为零的时候，锁被完全释放，此时别的任务就可以使用此资源。
 *
 * 针对每个类，也有一个锁（作为类的Class对象的一部分）;所以synchronized static方法可以在类的范围内防止对static数据的并发访问。
 *
 * 你应该什么时候同步呢? 可以运用Brian的同步规则:
 *      如果你正在写一个变量，它可能接下来将被另一个线程读取，或者正在读取一个上一次已经被另一个线程写过的变量，那么你必须使用同步，并且，读写线程都必须用相同的监视器锁同步。
 *
 * 如果在你的类中有超过一个方法在处理临界数据，那么你必须同步所有相关的方法。如果只同步一个方法，那么其他方法将会随意地忽略这个对象锁，并可以在无任何惩罚的情况下被调用。
 * 这是很重要的一点∶ 每个访问临界共享资源的方法都必须被同步，否则它们就不会正确地工作。
 */
public class Text {
}
/**
 * 同步控制EvenGenerator
 * 通过在EvenGeneratorjava中加入synchronized关键字，可以防止不希望的线程访问;
 */
class
SynchronizedEvenGenerator extends IntGenerator {
    private int currentEvenValue = 0;
    public synchronized int next() {
        ++currentEvenValue;
        Thread.yield(); // Cause failure faster
        ++currentEvenValue;
        return currentEvenValue;
    }
    public static void main(String[] args) {
        EvenChecker.test(new SynchronizedEvenGenerator());
    }
} ///:~
/**
 * 对Thread.yieldO的调用被插入到了两个递增操作之间，以提高在currentEvenValue是奇数状态时上下文切换的可能性。因为互斥可以防止多个任务同时进入临界区，所以这不会产生任何失败。
 * 但是如果失败将会发生，调用yieldO是一种促使其发生的有效方式。
 *
 * 第一个进入nextO的任务将获得锁，任何其他试图获取锁的任务都将从其开始尝试之时被阻塞，直至第一个任务释放锁。通过这种方式，任何时刻只有一个任务可以通过由互斥量看护的代码。
 *
 * 练习11∶（3）创建一个类，它包含两个数据域和一个操作这些域的方法，其操作过程是多步骤的。这样在该方法执行过程中，这些域将处于"不正确的状态"（根据你设定的某些定义）。
 *      添加读取这些域的方法，创建多个线程去调用各种方法，并展示处于"不正确状态的"数据是可视的。使用synchronized关键字修复这个问题。
 */

/**
 * 使用显式的Lock对象
 *
 * Java SE5的java.util.concurrent类库还包含有定义在java.util.concurrent.locks中的显式的互斥机制。Lock对象必须被显式地创建、锁定和释放。
 * 因此，它与内建的锁形式相比，代码缺乏优雅性。但是，对于解决某些类型的问题来说，它更加灵活。下面用显式的Lock重写的是 SyschronizedEventGeneratorjava:
 */
class MutexEvenGenerator extends IntGenerator {
    private int currentEvenValue = 0;
    private Lock lock = new ReentrantLock();
    public int next() {
        lock.lock();
        try {
            ++currentEvenValue;
            Thread.yield(); // Cause failure faster
            ++currentEvenValue;
            return currentEvenValue;
        } finally {
            lock.unlock();
        }
    }
    public static void main(String[] args) {
        EvenChecker.test(new MutexEvenGenerator());
    }
} ///:~
/**
 * MutexEvenGenerator添加了一个被互斥调用的锁，并使用lock）和unlock（）方法在next（）内部创建了临界资源。当你在使用Lock对象时，将这里所示的惯用法内部化是很重要的;
 * 紧接着的对lockO的调用，你必须放置在finally子句中带有unlockO的try-finallv语句中。注意，return语句必须在try子句中出现，以确保unlock）不会过早发生，从而将数据暴露给了第二个任务。
 *
 * 尽管try-finally所需的代码比synchronized关键字要多，但是这也代表了显式的Lock对象的优点之一。如果在使用synchronized关键字时，某些事物失败了，那么就会抛出一个异常。
 * 但是你没有机会去做任何清理工作，以维护系统使其处于良好状态。有了显式的Lock对象，你就可以使用finally子句将系统维护在正确的状态了。
 *
 * 大体上，当你使用synchronized关键字时，需要写的代码量更少，并且用户错误出现的可能性也会降低，因此通常只有在解决特殊问题时，才使用显式的Lock对象。
 * 例如，用synchronized关键字不能尝试着获取锁且最终获取锁会失败，或者尝试着获取锁一段时间，然后放弃它，要实现这些，你必须使用concurrent类库∶
 */
class AttemptLocking {
    private ReentrantLock lock = new ReentrantLock();
    public void untimed() {
        boolean captured = lock.tryLock();
        try {
            System.out.println("tryLock(): " + captured);
        } finally {
            if(captured)
                lock.unlock();
        }
    }
    public void timed() {
        boolean captured = false;
        try {
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println("tryLock(2, TimeUnit.SECONDS): " +
                    captured);
        } finally {
            if(captured)
                lock.unlock();
        }
    }
    public static void main(String[] args) {
        final AttemptLocking al = new AttemptLocking();
        al.untimed(); // True -- lock is available
        al.timed();   // True -- lock is available
        // Now create a separate task to grab the lock:
        new Thread() {
            { setDaemon(true); }
            public void run() {
                al.lock.lock();
                System.out.println("acquired");
            }
        }.start();
        Thread.yield(); // Give the 2nd task a chance
        al.untimed(); // False -- lock grabbed by task
        al.timed();   // False -- lock grabbed by task
    }
} /* Output:
tryLock(): true
tryLock(2, TimeUnit.SECONDS): true
acquired
tryLock(): false
tryLock(2, TimeUnit.SECONDS): false
*///:~
/**
 * ReentrantLock允许你尝试着获取但最终未获取锁，这样如果其他人已经获取了这个锁，那你就可以决定离开去执行其他一些事情，而不是等待直至这个锁被释放，就像在untimedO方法中所看到的。
 * 在timedO中，做出了尝试去获取锁，该尝试可以在2秒之后失败（注意，使用了 Java SE5的TimeUnit类来指定时间单位）。在mainO中，作为匿名类而创建了一个单独的Thread，它将获取锁，这使得untimed（）和timedO方法对某些事物将产生竞争。
 *
 * 显式的Lock对象在加锁和释放锁方面，相对于内建的synchronized锁来说，还赋予了你更细粒度的控制力。这对于实现专有同步结构是很有用的，例如用于遍历链接列表中的节点的节节传递的加锁机制（也称为锁耦合），
 * 这种遍历代码必须在释放当前节点的锁之前捕获下一个节点的锁。
 */






























