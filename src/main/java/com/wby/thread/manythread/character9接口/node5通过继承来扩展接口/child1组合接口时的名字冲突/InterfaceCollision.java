//: interfaces/InterfaceCollision.java
package com.wby.thread.manythread.character9接口.node5通过继承来扩展接口.child1组合接口时的名字冲突;

/**
 * 在实现多重继承时，可能会碰到一个小陷阱。在前面的例子中，CanFight和ActionCharacter都有一个相同的void fightO方法。
 * 这不是问题所在，因为该方法在二者中是相同的。相同的方法不会有什么问题，但是如果它们的签名或返回类型不同，又会怎么样呢?
 * 这有一个例子∶
 */
interface I1 { void f(); }

interface I2 { int f(int i); }

interface I3 { int f(); }

class C { public int f() { return 1; } }

class C2 implements I1, I2 {
  public void f() {}
  public int f(int i) { return 1; } // overloaded
}

class C3 extends C implements I2 {
  public int f(int i) { return 1; } // overloaded
}

class C4 extends C implements I3 {
  // Identical, no problem:
  public int f() { return 1; }
}

// Methods differ only by return type:
//! class C5 extends C implements I1 {}
//! interface I4 extends I1, I3 {} ///:~
/**
 * 此时困难来了，因为覆盖、实现和重载令人不快地搅在了一起，而且重载方法仅通过返回类型是区分不开的。当撤销最后两行的注释时，下列错误消息就说明了这一切∶
 * InterfaceCollision.java:23:f()in Ccannot implementf()in I; attempting to use incompatible return type found: int required: void
 * InterfaceCollision.java:24: Interfaces I3 and I1 are incompatible; both define f (), but with different return type
 *
 * 在打算组合的不同接口中使用相同的方法名通常会造成代码可读性的混乱，请尽量避免这种情况。
 *
 */
