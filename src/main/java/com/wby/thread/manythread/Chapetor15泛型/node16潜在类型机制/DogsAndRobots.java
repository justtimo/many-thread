package com.wby.thread.manythread.Chapetor15泛型.node16潜在类型机制;//: generics/DogsAndRobots.java
// No latent typing in Java


import com.wby.thread.manythread.charector14类型信息.node3类型转换前先做检查.pets.Dog;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

interface Performs {
  void speak();
  void sit();
} ///:~

class PerformingDog extends Dog implements Performs {
  public void speak() { print("Woof!"); }
  public void sit() { print("Sitting"); }
  public void reproduce() {}
}

class Communicate {
  public static <T extends Performs>
  void perform(T performer) {
    performer.speak();
    performer.sit();
  }
}

public class DogsAndRobots {
  public static void main(String[] args) {
    PerformingDog d = new PerformingDog();
    Robot r = new Robot();
    Communicate.perform(d);
    Communicate.perform(r);
  }
} /* Output:
Woof!
Sitting
Click!
Clank!
*///:~
/**
 * @Description:  但是要注意，perform不需要使用泛型来工作，他可以被简单地指定为接受一个Performs对象：
 */
class CommunicateSimply {
  static void perform(Performs performer) {
    performer.speak();
    performer.sit();
  }
}

class SimpleDogsAndRobots {
  public static void main(String[] args) {
    CommunicateSimply.perform(new PerformingDog());
    CommunicateSimply.perform(new Robot());
  }
} /* Output:
Woof!
Sitting
Click!
Clank!
*///:~
/**
* @Description: 本例中，泛型不是必须的，因为这些类已经被强制要求实现Performs接口
*/
