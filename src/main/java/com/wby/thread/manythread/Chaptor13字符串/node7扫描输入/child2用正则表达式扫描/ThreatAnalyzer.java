package com.wby.thread.manythread.Chaptor13字符串.node7扫描输入.child2用正则表达式扫描;//: strings/ThreatAnalyzer.java

import java.util.Scanner;
import java.util.regex.MatchResult;

/**
 * @Description:  除了能够扫描基本类型之外，你还可以自定义正则表达式进行扫描，这在扫描复杂数据时非常有用。
 * 下例将扫描一个防火墙日志文件中记录的威胁数据
 */
public class ThreatAnalyzer {
  static String threatData =
    "58.27.82.161@02/10/2005\n" +
    "204.45.234.40@02/11/2005\n" +
    "58.27.82.161@02/11/2005\n" +
    "58.27.82.161@02/12/2005\n" +
    "58.27.82.161@02/12/2005\n" +
    "[Next log section with different data format]";
  public static void main(String[] args) {
    Scanner scanner = new Scanner(threatData);
    String pattern = "(\\d+[.]\\d+[.]\\d+[.]\\d+)@" +
      "(\\d{2}/\\d{2}/\\d{4})";
    while(scanner.hasNext(pattern)) {
      scanner.next(pattern);
      MatchResult match = scanner.match();
      String ip = match.group(1);
      String date = match.group(2);
      System.out.format("Threat on %s from %s\n", date,ip);
    }
  }
} /* Output:
Threat on 02/10/2005 from 58.27.82.161
Threat on 02/11/2005 from 204.45.234.40
Threat on 02/11/2005 from 58.27.82.161
Threat on 02/12/2005 from 58.27.82.161
Threat on 02/12/2005 from 58.27.82.161
*///:~
/**
* @Description: 当next方法配合指定的正则表达式使用时，将找到下一个匹配该模式的部分，调用match方法就可以获取匹配结果。
 * 如上所示，他的工作方式与之前看到的正则表达式匹配相似。
 *
 * 注意：它仅仅针对下一个输入分词进行匹配，如果正则表达式包含定界符，那么永远都不可能匹配成功
*/
