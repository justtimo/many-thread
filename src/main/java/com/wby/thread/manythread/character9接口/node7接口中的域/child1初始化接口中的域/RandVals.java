package com.wby.thread.manythread.character9接口.node7接口中的域.child1初始化接口中的域;//: interfaces/RandVals.java
// Initializing interface fields with
// non-constant initializers.

import java.util.Random;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
 * 在接口中定义的域不能是"空final"，但是可以被非常量表达式初始化。例如∶
 */
public interface RandVals {
  Random RAND = new Random(47);
  int RANDOM_INT = RAND.nextInt(10);
  long RANDOM_LONG = RAND.nextLong() * 10;
  float RANDOM_FLOAT = RAND.nextLong() * 10;
  double RANDOM_DOUBLE = RAND.nextDouble() * 10;
} ///:~
/**
 * 既然域是static的，它们就可以在类第一次被加载时初始化，这发生在任何域首次被访问时。这里给出了一个简单的测试∶
 */
class TestRandVals {
  public static void main(String[] args) {
    print(RandVals.RANDOM_INT);
    print(RandVals.RANDOM_LONG);
    print(RandVals.RANDOM_FLOAT);
    print(RandVals.RANDOM_DOUBLE);
  }
} /* Output:
8
-32032247016559954
-8.5939291E18
5.779976127815049
*///:~
/**
 * 当然，这些域不是接口的一部分，它们的值被存储在该接口的静态存储区域内。
 */
