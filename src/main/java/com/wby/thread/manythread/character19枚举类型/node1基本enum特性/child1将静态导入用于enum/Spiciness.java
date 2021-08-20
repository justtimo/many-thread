package com.wby.thread.manythread.character19枚举类型.node1基本enum特性.child1将静态导入用于enum;

/**
 * 先看一看第5章中Burrito，java的另一个版本;
 */
public enum Spiciness {
    NOT, MILD, MEDIUM, HOT, FLAMING
} ///:~
class Burrito {
    Spiciness degree;
    public Burrito(Spiciness degree) { this.degree = degree;}
    public String toString() { return "Burrito is "+ degree;}
    public static void main(String[] args) {
        System.out.println(new Burrito(Spiciness.NOT));
        System.out.println(new Burrito(Spiciness.MEDIUM));
        System.out.println(new Burrito(Spiciness.HOT));
    }
} /* Output:
Burrito is NOT
Burrito is MEDIUM
Burrito is HOT
*///:~

/**
 * 使用static import能够将enum实例的标识符带入当前的命名空间，所以无需再用enum类型来修饰enum实例。这是一个好的想法吗?或者还是显式地修饰enum实例更好?这要看代码的复杂程度了。
 * 编译器可以确保你使用的是正确的类型，所以唯一需要担心的是，使用静态导入会不会导致你的代码令人难以理解。多数情况下，使用static import还是有好处的，不过，程序员还是应该对具体情况进行具体分析。
 *
 * 注意，在定义enum的同一个文件中，这种技巧无法使用，如果是在默认包中定义enum，这种技巧也无法使用（在Sun内部对这一点显然也有不同意见）。
 */
