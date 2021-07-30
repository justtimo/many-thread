package com.wby.thread.manythread.Character18JAVAIO系统.node12对象序列化.child1寻找类;//: io/FreezeAlien.java
// Create a serialized output file.

import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class FreezeAlien {
  public static void main(String[] args) throws Exception {
    ObjectOutput out = new ObjectOutputStream(
      new FileOutputStream("X.file"));
    Alien quellek = new Alien();
    out.writeObject(quellek);
  }
} ///:~
