package com.wby.thread.manythread.Chaptor13字符串.node6正则表达式.child8正则表达式与IO;//: strings/JGrep.java
// A very simple version of the "grep" program.
// {Args: JGrep.java "\\b[Ssct]\\w+"}

import com.wby.thread.manythread.net.mindview.util.TextFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:  下面的例子演示了如何应用正则表达式在一个文件中进行搜索匹配操作。
 *  JGrep.java灵感来源于Unix上的grep，他有两个参数：文件名以及要匹配的正则表达式，输出的是有匹配的部分以及匹配部分在行中的位置
 */
public class JGrep {
  public static void main(String[] args) throws Exception {
    if(args.length < 2) {
      System.out.println("Usage: java JGrep file regex");
      System.exit(0);
    }
    Pattern p = Pattern.compile(args[1]);
    // Iterate through the lines of the input file:
    int index = 0;
    Matcher m = p.matcher("");
    for(String line : new TextFile(args[0])) {
      m.reset(line);
      while(m.find())
        System.out.println(index++ + ": " +
          m.group() + ": " + m.start());
    }
  }
} /* Output: (Sample)
0: strings: 4
1: simple: 10
2: the: 28
3: Ssct: 26
4: class: 7
5: static: 9
6: String: 26
7: throws: 41
8: System: 6
9: System: 6
10: compile: 24
11: through: 15
12: the: 23
13: the: 36
14: String: 8
15: System: 8
16: start: 31
*///:~
/**
* @Description: 在循环外创建一个Matcher对象，然后通过reset方法每次为Matcher对象加载一行输入，最后用find方法搜索结果。
*/
