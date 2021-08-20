//: enumerated/Outcome.java
package com.wby.thread.manythread.character19枚举类型.node11多路分发;

import java.util.Random;

/**
 * 当你要处理多种交互类型时，程序可能会变得相当杂乱。举例来说，如果一个系统要分析和执行数学表达式。我们可能会声明Number.plus（Number）、Number.multiple（Number）等等，
 * 其中Number是各种数字对象的超类。然而，当你声明a.plus（b）时，你并不知道a或b的确切类型，那你如何能让它们正确地交互呢?
 *
 * 你可能从未思考过这个问题的答案。Java只支持单路分发。也就是说，如果要执行的操作包含了不止一个类型未知的对象时，那么Java的动态绑定机制只能处理其中一个的类型。这就无法解决我们上面提到的问题。
 * 所以，你必须自己来判定其他的类型，从而实现自己的动态绑定行为。
 *
 * 解决上面问题的办法就是多路分发（在那个例子中，只有两个分发，一般称之为两路分发）。多态只能发生在方法调用时，所以，如果你想使用两路分发，那么就必须有两个方法调用;
 * 第一个方法调用决定第一个未知类型，第二个方法调用决定第二个未知的类型。要利用多路分发，程序员必须为每一个类型提供一个实际的方法调用，如果你要处理两个不同的类型体系，
 * 就需要为每个类型体系执行一个方法调用。一般而言，程序员需要有设定好的某种配置，以便一个方法调用能够引出更多的方法调用，从而能够在这个过程中处理多种类型。为了达到这种效果，
 * 我们需要与多个方法一同工作∶因为每个分发都需要一个方法调用。在下面的例子中（实现了"石头、剪刀、布"游戏，也称为RoShamBo）对应的方法是compete（）和evalO，二者都是同一个类型的成员，
 * 它们可以产生三种Outcome实例中的一个作为结果
 */
public enum Outcome { WIN, LOSE, DRAW } ///:~

interface Item {
    Outcome compete(Item it);
    Outcome eval(Paper p);
    Outcome eval(Scissors s);
    Outcome eval(Rock r);
}

class Paper implements Item {
    public Outcome compete(Item it) { return it.eval(this); }
    public Outcome eval(Paper p) { return Outcome.DRAW; }
    public Outcome eval(Scissors s) { return Outcome.WIN; }
    public Outcome eval(Rock r) { return Outcome.LOSE; }
    public String toString() { return "Paper"; }
}

class Scissors implements Item {
    public Outcome compete(Item it) { return it.eval(this); }
    public Outcome eval(Paper p) { return Outcome.LOSE; }
    public Outcome eval(Scissors s) { return Outcome.DRAW; }
    public Outcome eval(Rock r) { return Outcome.WIN; }
    public String toString() { return "Scissors"; }
}

class Rock implements Item {
    public Outcome compete(Item it) { return it.eval(this); }
    public Outcome eval(Paper p) { return Outcome.WIN; }
    public Outcome eval(Scissors s) { return Outcome.LOSE; }
    public Outcome eval(Rock r) { return Outcome.DRAW; }
    public String toString() { return "Rock"; }
}

class RoShamBo1 {
    static final int SIZE = 20;
    private static Random rand = new Random(47);
    public static Item newItem() {
        switch(rand.nextInt(3)) {
            default:
            case 0: return new Scissors();
            case 1: return new Paper();
            case 2: return new Rock();
        }
    }
    public static void match(Item a, Item b) {
        System.out.println(
                a + " vs. " + b + ": " +  a.compete(b));
    }
    public static void main(String[] args) {
        for(int i = 0; i < SIZE; i++)
            match(newItem(), newItem());
    }
} /* Output:
Rock vs. Rock: DRAW
Paper vs. Rock: WIN
Paper vs. Rock: WIN
Paper vs. Rock: WIN
Scissors vs. Paper: WIN
Scissors vs. Scissors: DRAW
Scissors vs. Paper: WIN
Rock vs. Paper: LOSE
Paper vs. Paper: DRAW
Rock vs. Paper: LOSE
Paper vs. Scissors: LOSE
Paper vs. Scissors: LOSE
Rock vs. Scissors: WIN
Rock vs. Paper: LOSE
Paper vs. Rock: WIN
Scissors vs. Paper: WIN
Paper vs. Scissors: LOSE
Paper vs. Scissors: LOSE
Paper vs. Scissors: LOSE
Paper vs. Scissors: LOSE
*///:~
/**
 * Item是这几种类型的接口，将会被用作多路分发。RoShamBo1.match（）有两个Item参数，通过调用Item.compete（）方法开始两路分发。要判定a的类型，分发机制会在a的实际类型的 competeO内部起到分发的作用。
 * compete（O方法通过调用eval0来为另一个类型实现第二次分法。将自身（this）作为参数调用evalO，能够调用重载过的evalO方法，这能够保留第一次分发的类型信息。
 * 当第二次分发完成时，你就能够知道两个Item对象的具体类型了。
 *
 * 要配置好多路分发需要很多的工序，不过要记住，它的好处在于方法调用时的优雅的语法，这避免了在一个方法中判定多个对象的类型的丑陋代码，你只需说。"嘿。你们两个。
 * 我不在平你们是什么类型，请你们自己交流!"不过，在使用多路分发前，请先明确，这种优雅的代码对你确实有重要的意义。
 */
