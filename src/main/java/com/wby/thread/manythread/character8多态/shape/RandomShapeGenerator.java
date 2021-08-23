//: polymorphism/shape/RandomShapeGenerator.java
// A "factory" that randomly creates shapes.
package com.wby.thread.manythread.character8多态.shape;

import java.util.Random;

public class RandomShapeGenerator {
  private Random rand = new Random(47);
  public Shape next() {
    switch(rand.nextInt(3)) {
      default:
      case 0: return new Circle();
      case 1: return new Square();
      case 2: return new Triangle();
    }
  }
} ///:~
