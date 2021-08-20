package com.wby.thread.manythread.character19枚举类型.node10常量相关的方法;//: enumerated/ConstantSpecificMethod.java

import java.text.DateFormat;
import java.util.Date;
import java.util.EnumSet;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
import static com.wby.thread.manythread.net.mindview.util.Print.printnb;

/**
 * Java的enum有一个非常有趣的特性，即它允许程序员为enum实例编写方法，从而为每个 enum实例赋予各自不同的行为。要实现常量相关的方法，你需要为enum定义一个或多个abstract方法，
 * 然后为每个enum实例实现该抽象方法。参考下面的例子∶
 */
public enum ConstantSpecificMethod {
  DATE_TIME {
    String getInfo() {
      return
        DateFormat.getDateInstance().format(new Date());
    }
  },
  CLASSPATH {
    String getInfo() {
      return System.getenv("CLASSPATH");
    }
  },
  VERSION {
    String getInfo() {
      return System.getProperty("java.version");
    }
  };
  abstract String getInfo();
  public static void main(String[] args) {
    for(ConstantSpecificMethod csm : values())
      System.out.println(csm.getInfo());
  }
} /* (Execute to see output) *///:~
/**
 * 通过相应的enum实例，我们可以调用其上的方法。这通常也称为表驱动的代码（table- driven code，请注意它与前面提到的命令模式的相似之处）。
 *
 * 在面向对象的程序设计中，不同的行为与不同的类关联。而通过常量相关的方法，每个 enum实例可以具备自己独特的行为，这似乎说明每个enum实例就像一个独特的类。在上面的例子中，
 * enum实例似乎被当作其"超类"ConstantSpecificMethod来使用，在调用getInfoO方法时，体现出多态的行为。
 *
 * 然而，enum实例与类的相似之处也仅限于此了。我们并不能真的将enum实例作为一个类型来使用∶
 */
enum LikeClasses {
  WINKEN { void behavior() { print("Behavior1"); } },
  BLINKEN { void behavior() { print("Behavior2"); } },
  NOD { void behavior() { print("Behavior3"); } };
  abstract void behavior();
}

class NotClasses {
  // void f1(LikeClasses.WINKEN instance) {} // Nope
} /* Output:
Compiled from "NotClasses.java"
abstract class LikeClasses extends java.lang.Enum{
public static final LikeClasses WINKEN;

public static final LikeClasses BLINKEN;

public static final LikeClasses NOD;
...
*///:~
/**
 * 在方法f1O中，编译器不允许我们将—个enum实例当作class类型。如果我们分析一下编译器生成的代码，就知道这种行为也是很正常的。因为每个enum元素都是一个LikeClasses类型的 static final实例。
 *
 * 同时，由于它们是static实例，无法访问外部类的非static元素或方法，所以对于内部的 enum的实例而言，其行为与一般的内部类并不相同。
 *
 * 再看一个更有趣的关于洗车的例子。每个顾客在洗车时，都有一个选择菜单，每个选择对应一个不同的动作。可以将一个常量相关的方法关联到一个选择上，再使用一个EnumSet来保存客户的选择∶
 */
class CarWash {
  public enum Cycle {
    UNDERBODY {
      void action() { print("Spraying the underbody"); }
    },
    WHEELWASH {
      void action() { print("Washing the wheels"); }
    },
    PREWASH {
      void action() { print("Loosening the dirt"); }
    },
    BASIC {
      void action() { print("The basic wash"); }
    },
    HOTWAX {
      void action() { print("Applying hot wax"); }
    },
    RINSE {
      void action() { print("Rinsing"); }
    },
    BLOWDRY {
      void action() { print("Blowing dry"); }
    };
    abstract void action();
  }
  EnumSet<Cycle> cycles =
          EnumSet.of(Cycle.BASIC, Cycle.RINSE);
  public void add(Cycle cycle) { cycles.add(cycle); }
  public void washCar() {
    for(Cycle c : cycles)
      c.action();
  }
  public String toString() { return cycles.toString(); }
  public static void main(String[] args) {
    CarWash wash = new CarWash();
    print(wash);
    wash.washCar();
    // Order of addition is unimportant:
    wash.add(Cycle.BLOWDRY);
    wash.add(Cycle.BLOWDRY); // Duplicates ignored
    wash.add(Cycle.RINSE);
    wash.add(Cycle.HOTWAX);
    print(wash);
    wash.washCar();
  }
} /* Output:
[BASIC, RINSE]
The basic wash
Rinsing
[BASIC, HOTWAX, RINSE, BLOWDRY]
The basic wash
Applying hot wax
Rinsing
Blowing dry
*///:~
/**
 * 与使用匿名内部类相比较，定义常量相关方法的语法更高效、简洁。
 * 这个例子也展示了EnumSet了一些特性。因为它是一个集合，所以对于同一个元素而言，只能出现一次，因此对同一个参数重复地调用addO方法会被忽略掉（这是正确的行为，因为一个bit位开关只能"打开"一次）。
 * 同样地，向EnumSet添加enum实例的顺序并不重要，因为其输出的次序决定于enum实例定义时的次序。
 *
 * 除了实现abstract方法以外，程序员是否可以覆盖常量相关的方法呢?答案是肯定的，参考下面的程序∶
 */
enum OverrideConstantSpecific {
  NUT, BOLT,
  WASHER {
    void f() { print("Overridden method"); }
  };
  void f() { print("default behavior"); }
  public static void main(String[] args) {
    for(OverrideConstantSpecific ocs : values()) {
      printnb(ocs + ": ");
      ocs.f();
    }
  }
} /* Output:
NUT: default behavior
BOLT: default behavior
WASHER: Overridden method
*///:~
/**
 * 虽然enum有某些限制，但是一般而言，我们还是可以将其看作是类。
 */

