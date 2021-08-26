//: polymorphism/PrivateOverride.java
// Trying to override a private method.
package com.wby.thread.manythread.character8多态;
import static com.wby.thread.manythread.net.mindview.util.Print.print;


public class PrivateOverride {
  private void f() { print("private f()"); }
  public static void main(String[] args) {
    PrivateOverride po = new Derived();
    po.f();
  }
}

class Derived extends PrivateOverride {
  public void f() { print("public f()"); }
} /* Output:
private f()
*///:~
