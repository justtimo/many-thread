//: enumerated/Input.java
package com.wby.thread.manythread.character19枚举类型.node10常量相关的方法.child2使用enum的状态机;

import com.wby.thread.manythread.net.mindview.util.Generator;
import com.wby.thread.manythread.net.mindview.util.TextFile;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Random;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
 * 枚举类型非常适合用来创建状态机。一个状态机可以具有有限个特定的状态，它通常根据输入，从一个状态转移到下一个状态，不过也可能存在瞬时状态（transient states），
 * 而一旦任务执行结束，状态机就会立刻离开瞬时状态。
 *
 * 每个状态都具有某些可接受的输入，不同的输入会使状态机从当前状态转移到不同的新状态。由于enum对其实例有严格限制，非常适合用来表现不同的状态和输入。一般而言，每个状态都具有一些相关的输出。
 *
 * 自动售货机是一个很好的状态机的例子。首先，我们用一个enum定义各种输入∶
 */
public enum Input {
  NICKEL(5), DIME(10), QUARTER(25), DOLLAR(100),
  TOOTHPASTE(200), CHIPS(75), SODA(100), SOAP(50),
  ABORT_TRANSACTION {
    public int amount() { // Disallow
      throw new RuntimeException("ABORT.amount()");
    }
  },
  STOP { // This must be the last instance.
    public int amount() { // Disallow
      throw new RuntimeException("SHUT_DOWN.amount()");
    }
  };
  int value; // In cents
  Input(int value) { this.value = value; }
  Input() {}
  int amount() { return value; }; // In cents
  static Random rand = new Random(47);
  public static Input randomSelection() {
    // Don't include STOP:
    return values()[rand.nextInt(values().length - 1)];
  }
} ///:~
/**
 * 注意，除了两个特殊的Input实例之外，其他的Input都有相应的价格，因此在接口中定义了amountO方法。然而，对那两个特殊Input实例而言，调用amountO方法并不合适，所以如果程序员调用它们的amountO）
 * 方法就会有异常抛出（在接口内定义了一个方法，然后在你调用该方法的某个实现时就会抛出异常）。这似乎有点奇怪，但由于enum的限制，我们不得不采用这种方式。
 *
 * VendingMachine对输入的第一个反应是将其归类为Category enum中的某一个enum实例，这可以通过switch实现。下面的例子演示了enum是如何使代码变得更加清晰且易于管理的∶
 */
enum Category {
  MONEY(Input.NICKEL, Input.DIME, Input.QUARTER, Input.DOLLAR),
  ITEM_SELECTION(Input.TOOTHPASTE, Input.CHIPS, Input.SODA, Input.SOAP),
  QUIT_TRANSACTION(Input.ABORT_TRANSACTION),
  SHUT_DOWN(Input.STOP);
  private Input[] values;
  Category(Input... types) { values = types; }
  private static EnumMap<Input,Category> categories =
          new EnumMap<Input,Category>(Input.class);
  static {
    for(Category c : Category.class.getEnumConstants())
      for(Input type : c.values)
        categories.put(type, c);
  }
  public static Category categorize(Input input) {
    return categories.get(input);
  }
}

class VendingMachine {
  private static State state = State.RESTING;
  private static int amount = 0;
  private static Input selection = null;
  enum StateDuration { TRANSIENT } // Tagging enum
  enum State {
    RESTING {
      void next(Input input) {
        switch(Category.categorize(input)) {
          case MONEY:
            amount += input.amount();
            state = ADDING_MONEY;
            break;
          case SHUT_DOWN:
            state = TERMINAL;
          default:
        }
      }
    },
    ADDING_MONEY {
      void next(Input input) {
        switch(Category.categorize(input)) {
          case MONEY:
            amount += input.amount();
            break;
          case ITEM_SELECTION:
            selection = input;
            if(amount < selection.amount())
              print("Insufficient money for " + selection);
            else state = DISPENSING;
            break;
          case QUIT_TRANSACTION:
            state = GIVING_CHANGE;
            break;
          case SHUT_DOWN:
            state = TERMINAL;
          default:
        }
      }
    },
    DISPENSING(StateDuration.TRANSIENT) {
      void next() {
        print("here is your " + selection);
        amount -= selection.amount();
        state = GIVING_CHANGE;
      }
    },
    GIVING_CHANGE(StateDuration.TRANSIENT) {
      void next() {
        if(amount > 0) {
          print("Your change: " + amount);
          amount = 0;
        }
        state = RESTING;
      }
    },
    TERMINAL { void output() { print("Halted"); } };
    private boolean isTransient = false;
    State() {}
    State(StateDuration trans) { isTransient = true; }
    void next(Input input) {
      throw new RuntimeException("Only call " +
              "next(Input input) for non-transient states");
    }
    void next() {
      throw new RuntimeException("Only call next() for " +
              "StateDuration.TRANSIENT states");
    }
    void output() { print(amount); }
  }
  static void run(Generator<Input> gen) {
    while(state != State.TERMINAL) {
      state.next(gen.next());
      while(state.isTransient)
        state.next();
      state.output();
    }
  }
  public static void main(String[] args) {
    Generator<Input> gen = new RandomInputGenerator();
    if(args.length == 1)
      gen = new FileInputGenerator(args[0]);
    run(gen);
  }
}

// For a basic sanity check:
class RandomInputGenerator implements Generator<Input> {
  public Input next() { return Input.randomSelection(); }
}

// Create Inputs from a file of ';'-separated strings:
class FileInputGenerator implements Generator<Input> {
  private Iterator<String> input;
  public FileInputGenerator(String fileName) {
    input = new TextFile(fileName, ";").iterator();
  }
  public Input next() {
    if(!input.hasNext())
      return null;
    return Enum.valueOf(Input.class, input.next().trim());
  }
} /* Output:
25
50
75
here is your CHIPS
0
100
200
here is your TOOTHPASTE
0
25
35
Your change: 35
0
25
35
Insufficient money for SODA
35
60
70
75
Insufficient money for SODA
75
Your change: 75
0
Halted
*///:~
/**
 * 由于用switch语句从enum实例中进行选择是最常见的一种方式（请注意，为了使enum在 switch语句中的使用变得简单，我们是需要付出其他代价的），所以，我们经常遇到这样的问题∶将多个enum进行分类时，
 * "我们希望在什么enum中使用switch语句?"我们通过VendingMachine的例子来研究一下这个问题。对于每一个State，我们都需要在输入动作的基本分类中进行查找∶用户塞入钞票，选择了某个货物，操作被取消，以及机器停止。
 * 然而，在这些基本分类之下，我们又可以塞入不同类型的创票，可以选择不同的货物。Category enum将不同类型的Input进行分组，因而，可以使用categorizeO方法为switch语句生成恰当的Cateroy实例。
 * 并且，该方法使用的 EnumMap确保了在其中进行查询时的效率与安全。
 *
 * 如果读者仔细研究VendingMachine类，就会发现每种状态的不同之处，以及对于输入的不同响应，其中还有两个瞬时状态。在run（）方法中，状态机等待着下一个Input，
 * 并一直在各个状态中移动，直到它不再处于瞬时状态。
 *
 * 通过两种不同的Generator对象，我们可以用两种方式来测试VendingMachine。首先是 RandomInputGenerator，它会不停地生成各种输入，当然，除了SHUT_DOWN之外。通过长时间地运行RandomInputGenerator，
 * 可以起到健全测试（sanity test）的作用，能够确保该状态机不会进入一个错误状态。另一个是FileInputGenerator，使用文件以文本的方式来描述输入，然后将它们转换成enum实例，并创建对应的Input对象。
 * 上面的程序使用的正是如下的文本文件∶
 */
/**
 * 这种设计有一个缺陷，它要求enum State实例访问的VendingMachine属性必须声明为static，这意味着，你只能有一个VendingMachine实例。不过如果我们思考一下实际的（嵌入式Java）应用，
 * 这也许并不是一个大问题，因为在一台机器上，我们可能只有一个应用程序。
 *
 * 练习10;（7）修改class VendingMachine，在其中使用EnumMap，使其同时可以存在多个 VendingMachine实例。
 *
 * 练习11;（7） 如果是一个真的自动售货机，我们希望能够很容易地添加或改变售卖货品的种类，因此，用enum来表现Input时的缺陷使其并不实用（我们知道enum对实例有着特殊的限制，一旦声明结束就不能有任何改动）。
 * 修改VendingMachine，用一个class来表现售卖的货品，并基干—个文本文件，使用ArrayList来初始化它们（可以使用net.mindviewutilTextFile）。
 *
 * 作业9∶ 设计一个自动贩卖机，使其具备能够很容易地应用于所有国家的国际化的能力。
 */
