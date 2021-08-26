package com.wby.thread.manythread.character9接口.node2接口;

/**
 * interface关键字使抽象的概念更向前迈进了一步。abstract关键字允许人们在类中创建一个或多个没有任何定义的方法——提供了接口部分，但是没有提供任何相应的具体实现，这些实现是由此类的继承者创建的。
 * interface这个关键字产生—个完全抽象的类，它根本就没有提供任何具体实现。它允许创建者确定方法名、参数列表和返回类型，但是没有任何方法体。接口只提供了形式，而未提供任何具体实现。
 *
 * 一个接口表示;"所有实现了该特定接口的类看起来都像这样"。因此，任何使用某特定接口的代码都知道可以调用该接口的哪些方法，而且仅需知道这些。因此，接口被用来建立类与类之间的协议。（某些面向对象编程语言使用关键字protocol来完成这一功能。）
 *
 * 但是，interface不仅仅是一个极度抽象的类，因为它允许人们通过创建一个能够被向上转型为多种基类的类型，来实现某种类似多重继变种的特性。
 *
 * 要想创建一个接口，需要用interface关键字来替代class关键字。就像类一样，可以在interface关键字前面添加public关键字（但仅限于该接口在与其同名的文件中被定义）。
 * 如果不添加public关键字，则它只具有包访问权限，这样它就只能在同一个包内可用。接口也可以包含域，但是这些域隐式地是static和final的。
 *
 * 要让一个类遵循某个特定接口（或者是一组接口），需要使用implements关键字，它表示;"interface只是它的外貌，但是现在我要声明它是如何工作的。"除此之外，它看起来还很像线承。"乐器"示例的图说明了这一点∶
 *
 * 可以从Woodwind和Brass类中看到，一旦实现了某个接口，其实现就变成了一个普通的类，就可以按常规方式扩展它。
 *
 * 可以选择在接口中显式地将方法声明为public的，但即使你不这么做，它们也是public的。因此，当要实现一个接口时，在接口中被定义的方法必须被定义为是public的;
 * 否则，它们将只能得到默认的包访问权限，这样在方法被继承的过程中，其可访问权限就被降低了，这是Java编译器所不允许的。
 *
 * 读者可以在修改过的Instrument的例子中看到这一点。要注意的是，在接口中的每一个方法确实都 只是—个声明。这是编译器所允许的在接口中唯一能够存在的事物。
 * 此外，在 Instrument中没有任何方法被声明为是public的，但是它们自动就都是public的∶
 */
/*interface Instrument {
    // Compile-time constant:
    int VALUE = 5; // static & final
    // Cannot have method definitions:
    void play(Note n); // Automatically public
    void adjust();
}

class Wind implements Instrument {
    public void play(Note n) {
        print(this + ".play() " + n);
    }
    public String toString() { return "Wind"; }
    public void adjust() { print(this + ".adjust()"); }
}

class Percussion implements Instrument {
    public void play(Note n) {
        print(this + ".play() " + n);
    }
    public String toString() { return "Percussion"; }
    public void adjust() { print(this + ".adjust()"); }
}

class Stringed implements Instrument {
    public void play(Note n) {
        print(this + ".play() " + n);
    }
    public String toString() { return "Stringed"; }
    public void adjust() { print(this + ".adjust()"); }
}

class Brass extends Wind {
    public String toString() { return "Brass"; }
}

class Woodwind extends Wind {
    public String toString() { return "Woodwind"; }
}

public class Music5 {
    // Doesn't care about type, so new types
    // added to the system still work right:
    static void tune(Instrument i) {
        // ...
        i.play(Note.MIDDLE_C);
    }
    static void tuneAll(Instrument[] e) {
        for(Instrument i : e)
            tune(i);
    }
    public static void main(String[] args) {
        // Upcasting during addition to the array:
        Instrument[] orchestra = {
                new Wind(),
                new Percussion(),
                new Stringed(),
                new Brass(),
                new Woodwind()
        };
        tuneAll(orchestra);
    }
}*/ /* Output:
Wind.play() MIDDLE_C
Percussion.play() MIDDLE_C
Stringed.play() MIDDLE_C
Brass.play() MIDDLE_C
Woodwind.play() MIDDLE_C
*///:~

/**
 * 此实例的这个版本还有另外一处改动∶what（）方法已经被修改为toString（O方法，因为 toStringO的逻辑正是whatO要实现的逻辑。由于toStringO方法是根类Object的一部分，因此它不需要出现在接口中。
 *
 * 余下的代码其工作方式都是相同的。无论是将其向上转型为称为Instrument的普通类，还是称为Instrument的抽象类，或是称为Instrument的接口，都不会有问题。它的行为都是相同的。
 * 事实上，你可以在tune（）方法中看到，没有任何依据来证明Instrument是一个普通类、抽象类，还是一个接口。
 */
public class Text {
}
