package com.wby.thread.manythread.Chaptor13字符串.node6正则表达式.child7reset方法;//: strings/Resetting.java

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @Description:  reset方法可以将现有的Matcher对象应用于一个新的字符序列
 */
public class Resetting {
  public static void main(String[] args) throws Exception {
    Matcher m = Pattern.compile("[frb][aiu][gx]")
      .matcher("fix the rug with bags");
    while(m.find())
      System.out.print(m.group() + " ");
    System.out.println();
    m.reset("fix the rig with rags");
    while(m.find())
      System.out.print(m.group() + " ");
  }
} /* Output:
fix rug bag
fix rig rag
*///:~
/**
* @Description: 使用不带参数的reset方法可以将现有的Matcher对象重新设置到当前字符序列的起始位置
*/
