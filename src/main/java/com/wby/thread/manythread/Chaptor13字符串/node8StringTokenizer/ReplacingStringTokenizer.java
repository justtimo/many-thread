package com.wby.thread.manythread.Chaptor13字符串.node8StringTokenizer;//: strings/ReplacingStringTokenizer.java

import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @Description:  在引入正则表达式和Scanner类之前，分割字符串唯一方法是使用StringTokenizer。
 */
public class ReplacingStringTokenizer {
  public static void main(String[] args) {
    String input = "But I'm not dead yet! I feel happy!";
    StringTokenizer stoke = new StringTokenizer(input);
    while(stoke.hasMoreElements())
      System.out.print(stoke.nextToken() + " ");
    System.out.println();
    System.out.println(Arrays.toString(input.split(" ")));
    Scanner scanner = new Scanner(input);
    while(scanner.hasNext())
      System.out.print(scanner.next() + " ");
  }
} /* Output:
But I'm not dead yet! I feel happy!
[But, I'm, not, dead, yet!, I, feel, happy!]
But I'm not dead yet! I feel happy!
*///:~
/**
* @Description: 使用正则表达式或Scanner对象，我们能够以更复杂的方式分割字符串，这对于StringTokenizer就很困难了。
 * 基本上，StringTokenizer已经废弃不用了。
*/
