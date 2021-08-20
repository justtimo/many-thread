package com.wby.thread.manythread.character19枚举类型.node2向enum中添加新方法;//: enumerated/OzWitch.java
// The witches in the land of Oz.

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
 * 除了不能继承自一个enum之外，我们基本上可以将enum看作一个常规的类。也就是说,我们可以向enum中添加方法。enum甚至可以有mainO方法。
 *
 * 一般来说，我们希望每个枚举实例能够返回对自身的描述，而不仅仅只是默认的toString（实现。这只能返回枚举实例的名字。为此，你可以提供—个构造器。
 * 专门负责处理这个额外的信息，然后添加一个方法，返回这个描述信息。看一看下面的示例∶
 */
public enum OzWitch {
  // Instances must be defined first, before methods:
  WEST("Miss Gulch, aka the Wicked Witch of the West"),
  NORTH("Glinda, the Good Witch of the North"),
  EAST("Wicked Witch of the East, wearer of the Ruby " +
    "Slippers, crushed by Dorothy's house"),
  SOUTH("Good by inference, but missing");
  private String description;
  // Constructor must be package or private access:
  private OzWitch(String description) {
    this.description = description;
  }
  public String getDescription() { return description; }
  public static void main(String[] args) {
    for(OzWitch witch : OzWitch.values())
      print(witch + ": " + witch.getDescription());
  }
} /* Output:
WEST: Miss Gulch, aka the Wicked Witch of the West
NORTH: Glinda, the Good Witch of the North
EAST: Wicked Witch of the East, wearer of the Ruby Slippers, crushed by Dorothy's house
SOUTH: Good by inference, but missing
*///:~
/**
 * 注意，如果你打算定义自己的方法，那么必须在enum实例序列的最后添加一个分号。同时， Java要求你必须先定义enum实例。如果在定义enum实例之前定义了任何方法或属性，那么在编译时就会得到错误信息。
 *
 * enum中的构造器与方法和普通的类没有区别，因为除了有少许限制之外，enum就是一个普通的类。所以，我们可以使用enum做许多事情（虽然，我们一般只使用普通的枚举类型）
 *
 * 在这个例子中，虽然我们有意识地将enum的构造器声明为private，但对于它的可访问性而言，其实并没有什么变化，因为（即使不声明为private）我们只能在enum定义的内部使用其构造器创建enum实例。
 * 一旦enum的定义结束，编译器就不允许我们再使用其构造器来创建任何实例了。
 */





















