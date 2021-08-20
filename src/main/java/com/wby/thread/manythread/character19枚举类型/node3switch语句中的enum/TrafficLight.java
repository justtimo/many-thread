package com.wby.thread.manythread.character19枚举类型.node3switch语句中的enum;//: enumerated/TrafficLight.java
// Enums in switch statements.


import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
 * 在switch中使用enum，是enum提供的一项非常便利的功能。一般来说，在switch中只能使用整数值，而枚举实例天生就具备整数值的次序，并且可以通过ordinal（）
 * 方法取得其次序（显然编译器帮我们做了类似的工作），因此我们可以在switch语句中使用enum。
 *
 * 虽然一般情况下我们必须使用enum类型来修饰一个enum实例，但是在case语句中却不必如此。下面的例子使用enum构造了一个小型状态机∶
 */
// Define an enum type:
enum Signal { GREEN, YELLOW, RED, }

public class TrafficLight {
  Signal color = Signal.RED;
  public void change() {
    switch(color) {
      // Note that you don't have to say Signal.RED
      // in the case statement:
      case RED:    color = Signal.GREEN;
                   break;
      case GREEN:  color = Signal.YELLOW;
                   break;
      case YELLOW: color = Signal.RED;
                   break;
    }
  }
  public String toString() {
    return "The traffic light is " + color;
  }
  public static void main(String[] args) {
    TrafficLight t = new TrafficLight();
    for(int i = 0; i < 7; i++) {
      print(t);
      t.change();
    }
  }
} /* Output:
The traffic light is RED
The traffic light is GREEN
The traffic light is YELLOW
The traffic light is RED
The traffic light is GREEN
The traffic light is YELLOW
The traffic light is RED
*///:~
/**
 * 编译器并没有抱怨switch中没有default语句，但这并不是因为每一个Signal都有对应的case语句。如果你注释掉其中的某个case语句，编译器同样不会抱怨什么。
 * 这意味着，你必须确保自己覆盖了所有的分支。但是，如果在case语句中调用return，那么编译器就会抱怨缺少default语句了。这与是否覆盖了enum的所有实例无关。
 */
