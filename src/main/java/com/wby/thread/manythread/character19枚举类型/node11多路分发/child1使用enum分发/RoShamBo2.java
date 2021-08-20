//: enumerated/RoShamBo2.java
// Switching one enum on another.
package com.wby.thread.manythread.character19枚举类型.node11多路分发.child1使用enum分发;

import com.wby.thread.manythread.character19枚举类型.node11多路分发.Outcome;
import com.wby.thread.manythread.character19枚举类型.node6随机选取.Enums;

import static com.wby.thread.manythread.character19枚举类型.node11多路分发.Outcome.*;

/**
 * 直接将RoShamBo1.java翻译为基干enum的版本是有间问题的，因为enum实例不是类型，不能将enum实例作为参数的类型，所以无法重载eval（O方法。
 * 不过，还有很多方式可以实现多路分发，并从enum中获益。
 *
 * 一种方式是使用构造器来初始化每个enum实例，并以"一组"结果作为参数。这二者放在一块，形成了类似查询表的结构∶
 */
public enum RoShamBo2 implements Competitor<RoShamBo2> {
  PAPER(DRAW, LOSE, WIN),
  SCISSORS(WIN, DRAW, LOSE),
  ROCK(LOSE, WIN, DRAW);
  private Outcome vPAPER, vSCISSORS, vROCK;
  RoShamBo2(Outcome paper,Outcome scissors,Outcome rock) {
    this.vPAPER = paper;
    this.vSCISSORS = scissors;
    this.vROCK = rock;
  }
  public Outcome compete(RoShamBo2 it) {
    switch(it) {
      default:
      case PAPER: return vPAPER;
      case SCISSORS: return vSCISSORS;
      case ROCK: return vROCK;
    }
  }
  public static void main(String[] args) {
    RoShamBo.play(RoShamBo2.class, 20);
  }
} /* Output:
ROCK vs. ROCK: DRAW
SCISSORS vs. ROCK: LOSE
SCISSORS vs. ROCK: LOSE
SCISSORS vs. ROCK: LOSE
PAPER vs. SCISSORS: LOSE
PAPER vs. PAPER: DRAW
PAPER vs. SCISSORS: LOSE
ROCK vs. SCISSORS: WIN
SCISSORS vs. SCISSORS: DRAW
ROCK vs. SCISSORS: WIN
SCISSORS vs. PAPER: WIN
SCISSORS vs. PAPER: WIN
ROCK vs. PAPER: LOSE
ROCK vs. SCISSORS: WIN
SCISSORS vs. ROCK: LOSE
PAPER vs. SCISSORS: LOSE
SCISSORS vs. PAPER: WIN
SCISSORS vs. PAPER: WIN
SCISSORS vs. PAPER: WIN
SCISSORS vs. PAPER: WIN
*///:~
/**
 * 在compete（）方法中，一旦两种类型都被确定了，那么唯一的操作就是返回结果Outcome。然而，你可能还需要调用其他的方法，（例如）甚至是调用在构造器中指定的某个命令对象上的方法。
 *
 * RoShamBo2.java比之前的例子短小得多，而且更直接，更易于理解。注意，我们仍然是使用两路分发来判定两个对象的类型。在RoShamBo1.java中，两次分发都是通过实际的方法调用实现，
 * 而在这个例子中，只有第一次分发是实际的方法调用。第二个分发使用的是switch，不过这样做是安全的，因为enum限制了switch语句的选择分支。
 *
 * 在代码中，enum被单独抽取出来，因此它可以应用在其他例子中。首先，Competitor接口定义了一种类型，该类型的对象可以与另一个Competitor相竞争∶
 *
 */
interface Competitorr<T extends Competitor<T>> {
  Outcome compete(T competitor);
} ///:~

/**
 * 然后，我们定义两个static方法（static可以避免显式地指明参数类型）。第一个是match（）方法，它会为一个Competitor对象调用compete（）方法，并与另一个Competitor对象作比较。
 * 在这个例子中，我们看到，match（方法的参数需要是Competitor<T>类型。但是在playO方法中，类型参数必须同时是Enum<T>类型（因为它将在Enums.random（）中使用）
 * 和Competitor<T>类型（因为它将被传递给match（）方法）∶
 */
class RoShamBoo {
  public static <T extends Competitor<T>>
  void match(T a, T b) {
    System.out.println(
            a + " vs. " + b + ": " +  a.compete(b));
  }
  public static <T extends Enum<T> & Competitor<T>>
  void play(Class<T> rsbClass, int size) {
    for(int i = 0; i < size; i++)
      match(
              Enums.random(rsbClass),Enums.random(rsbClass));
  }
} ///:~
/**
 * playO方法没有将类型参数T作为返回值类型，因此，似乎我们应该在Class<T>中使用通配符来代替上面的参数声明。然而，通配符不能扩展多个基类，所以我们必须采用以上的表达式。
 */
