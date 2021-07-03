package com.wby.thread.manythread.Chaptor13字符串.node6正则表达式.child2创建正则表达式;//: strings/Rudolph.java

/**
* @Description: 作为演示，下面的每一个正则表达式都能成功匹配字符序列“Rudolph”
*/
public class Rudolph {
  public static void main(String[] args) {
    for(String pattern : new String[]{ "Rudolph",
      "[rR]udolph", "[rR][aeiou][a-z]ol.*", "R.*" })
      System.out.println("Rudolph".matches(pattern));
  }
} /* Output:
true
true
true
true
*///:~
