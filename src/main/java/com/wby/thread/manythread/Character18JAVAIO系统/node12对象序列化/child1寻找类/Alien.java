package com.wby.thread.manythread.Character18JAVAIO系统.node12对象序列化.child1寻找类;//: io/Alien.java
// A serializable class.

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
* 读者或许会奇怪，将一个对象从它的序列化状态中恢复出来，有哪些工作是必须的呢?举个例子来说，假如我们将一个对象序列化，并通过网络将其作为文件传送给另一台计算机;
 * 那么，另一台计算机上的程序可以只利用该文件内容来还原这个对象吗?
 *
 * 回答这个问题的最好方法就是做一个实验。下面这个文件位于本章的子目录下∶
 *
 *
 * 而用于创建和序列化一个Alien对象的文件也位于相同的目录下∶FreezeAlien。java
*/
public class Alien implements Serializable {} ///:~

/**
* 这个程序不但能捕获和处理异常，而且将异常抛出到mainO方法之外，以便通过控制台产生报告。一旦该程序被编译和运行，它就会在c12目录下产生一个名为X.file的文件。以下代码位于一个名为xfiles的子目录下∶
*/
class ThawAlien {
    public static void main(String[] args) throws Exception {
        ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(new File("..", "X.file")));
        Object mystery = in.readObject();
        System.out.println(mystery.getClass());
    }
} /* Output:
class Alien
*///:~
/**
* @Description: 打开文件和读取mystery对象中的内容都需要Alien的Class对象，而Jaya虚拟机找不到Alien.class （除非它正好在类路径Classpath内，而本例却不在类路径之内）。
 * 这样就会得到一个名叫ClassNotFoundException的异常（同样，除非能够验证Alien存在，否则它等于消失）。必须保证Java虚拟机能找到相关的.class文件
*/
