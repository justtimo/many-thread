package com.wby.thread.manythread;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: LangWeiXian
 * @date: 2021/5/13 14:48
 * @Description: TODO 线程中断方法有三个。interrupt()中断、Thread.isInterrupted()是否中断、Thread.interrupted() 是否中断，并清除当前中断状态
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class ThreadTest {
    /**
    * @Author: LangWeiXian
    * @Params []
    * @return: void
    * @date: 2021/5/13 14:57
    * @Description: TODO 此方法虽然进行了中断，但是在t1中并没有做中断处理，所以即使t1线程被置上了中断状态，但是中断没有起作用
    */
    @Test
    public void test1() throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(" 当前线程是t1的线程： "+Thread.currentThread().getName());
                    Thread.yield();
                }

            }
        };
        t1.start();
        Thread.sleep(2000);
        System.out.println(" 当前线程是方法线程： "+Thread.currentThread().getName());
        t1.interrupt();
    }

    /**
    * @Author: LangWeiXian
    * @Params []
    * @return: void
    * @date: 2021/5/13 15:20
    * @Description: TODO sleep方法由于中断抛出了异常，此时，他会清除中断标记，如果不处理，在下一次循环时，就无法捕获这个中断。因此在异常处理中，再次设置标记
    */
    @Test
    public void test2 () throws InterruptedException {
        Thread t2=new Thread(){
            @Override
            public void run() {
                while (true){
                    if (Thread.currentThread().isInterrupted()){
                        System.out.println(" 当前线程是t2的线程： "+Thread.currentThread().getName());
                        break;
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.out.println("线程睡眠。。。");
                        e.printStackTrace();
                        //TODO 再次设置中断状态：保证后续代码的处理，保证数据一致性和完整性，因此再次中断自己，只有这样，48行代码的中断检查中，才能发现当前线程已经被中断了
                        Thread.currentThread().interrupt();
                        //后续代码
                    }
                }

            }
        };
        t2.start();
        System.out.println(" 当前线程是方法的线程： "+Thread.currentThread().getName());
        Thread.sleep(1000);
        t2.interrupt();
        System.out.println("方法结束");
    }

    public void test3(){
        Object ob = new Object();
        Thread t1=new Thread(){
            @Override
            public void run() {
                synchronized (ob){
                    System.out.println("t1 start : "+Thread.currentThread().getName());
                    try {
                        ob.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("t1 end : ");
                }

            }
        };
        Thread t2=new Thread(){
            @Override
            public void run() {
                synchronized (ob){
                    System.out.println("t2 start : "+Thread.currentThread().getName());
                    ob.notify();

                    System.out.println("t2 end : ");
                }

            }
        };


    }


}
