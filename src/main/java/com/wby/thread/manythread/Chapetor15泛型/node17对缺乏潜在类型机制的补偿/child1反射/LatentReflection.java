package com.wby.thread.manythread.Chapetor15泛型.node17对缺乏潜在类型机制的补偿.child1反射;//: generics/LatentReflection.java
// Using Reflection to produce latent typing.

import com.wby.thread.manythread.Chapetor15泛型.node16潜在类型机制.Robot;

import java.lang.reflect.Method;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description: 可以使用的一种方式是反射，下面的perform方法就是使用了潜在类型机制
*/
// Does not implement Performs:
class Mime {
  public void walkAgainstTheWind() {}
  public void sit() { print("Pretending to sit"); }
  public void pushInvisibleWalls() {}
  public String toString() { return "Mime"; }
}

// Does not implement Performs:
class SmartDog {
  public void speak() { print("Woof!"); }
  public void sit() { print("Sitting"); }
  public void reproduce() {}
}

class CommunicateReflectively {
  public static void perform(Object speaker) {
    Class<?> spkr = speaker.getClass();
    try {
      try {
        Method speak = spkr.getMethod("speak");
        speak.invoke(speaker);
      } catch(NoSuchMethodException e) {
        print(speaker + " cannot speak");
      }
      try {
        Method sit = spkr.getMethod("sit");
        sit.invoke(speaker);
      } catch(NoSuchMethodException e) {
        print(speaker + " cannot sit");
      }
    } catch(Exception e) {
      throw new RuntimeException(speaker.toString(), e);
    }
  }
}

public class LatentReflection {
  public static void main(String[] args) {
    CommunicateReflectively.perform(new SmartDog());
    CommunicateReflectively.perform(new Robot());
    CommunicateReflectively.perform(new Mime());
  }
} /* Output:
Woof!
Sitting
Click!
Clank!
Mime cannot speak
Pretending to sit
*///:~
/**
* @Description: 上例中，这些类完全是彼此分离的，没有任何公共基类(除了Object)或接口。通过反射， CommunicateReflectively.perform()
 *  能够动态的确定所需要的方法是否可用并调用它们。他设置能够处理Mime只具有一个必须的方法这一事实，并能够部分实现其目标
*/
