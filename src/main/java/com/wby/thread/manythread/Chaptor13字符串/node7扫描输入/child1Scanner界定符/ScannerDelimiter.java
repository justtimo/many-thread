package com.wby.thread.manythread.Chaptor13字符串.node7扫描输入.child1Scanner界定符;//: strings/ScannerDelimiter.java

import java.util.Scanner;

/**
 * @Description:  默认情况下，Scanner根据空白字符对输入进行分词，但是你可以使用正则表达式指定自己所需要的界定符
 */
public class ScannerDelimiter {
  public static void main(String[] args) {
    Scanner scanner = new Scanner("12, 42, 78, 99, 42");
    scanner.useDelimiter("\\s*,\\s*");
    while(scanner.hasNextInt())
      System.out.println(scanner.nextInt());
  }
} /* Output:
12
42
78
99
42
*///:~
/**
* @Description: 这个例子中使用逗号(包括逗号前后任意的空白字符)作为定界符，同样的可以使用逗号读取分隔的文件。
 * 使用useDelimiter方法设置定界符，同时还有一个Delimiter方法用来返回当前正在作为定界符使用的Pattern对象。
*/
