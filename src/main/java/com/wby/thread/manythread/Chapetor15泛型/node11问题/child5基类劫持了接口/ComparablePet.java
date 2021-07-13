package com.wby.thread.manythread.Chapetor15泛型.node11问题.child5基类劫持了接口;//: generics/ComparablePet.java

/**
* @Description: 假设你有一个Pet类，他可以与其他的Pet对象进行比较(实现了Comparable接口):
*/
public class ComparablePet
implements Comparable<ComparablePet> {
  public int compareTo(ComparablePet arg) { return 0; }
} ///:~
/**
* @Description:  对可以与ComparablePet的子类比较的类型进行窄化是有意义的，例如，一个Cat对象就只能与其他的Cat对象比较：
*/
//: generics/HijackedInterface.java
// {CompileTimeError} (Won't compile)

/*
class Cat extends ComparablePet implements Comparable<Cat>{
  // Error: Comparable cannot be inherited with
  // different arguments: <Cat> and <Pet>
  public int compareTo(Cat arg) { return 0; }
} ///:~*/
/**
* @Description: 遗憾的是，这不能工作。一旦为Comparable确定了ComparablePet参数，那么其他任何实现类都不能与ComparablePet之外的任何对象比较：
*/
//: generics/RestrictedComparablePets.java

class Hamster extends ComparablePet
        implements Comparable<ComparablePet> {
  public int compareTo(ComparablePet arg) { return 0; }
}

// Or just:

class Gecko extends ComparablePet {
  public int compareTo(ComparablePet arg) { return 0; }
} ///:~
/**
* @Description: Hamster说明再次实现ComparablePet中的相同接口是可能的，只要他们精确地相同，包括参数类型在内。但是，这只是与覆盖基类中的方法相同，就像在Gecko中看到的那样。
*/
