package com.wby.thread.manythread.Chaptor13字符串.node6正则表达式.child4Pattern和Macther.Pattern标记;//: strings/ReFlags.java

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Description:  Pattern类的compile方法还有另外一个版本，他接受一个参数，以便调整匹配的行为：
 *  Pattern compile(String regex, int flags)
 *  其中flag来自Pattern类的常量：具体参考书本304~305页
 *  这些标记中：括CASE_INSENSITIVE 、 MULTILINE 和COMMENTS(对声明或文档有用)特别有用。
 *
 *  请注意，你可以直接在正则表达式中使用大多数标记，只需要将上表中括号括起来的字符插入到正则表达式中，你希望他起作用的位置即可
 *  你还可以通过“或”(|)操作符组合多个标记功能能，见下例：
 */
public class ReFlags {
  public static void main(String[] args) {
    Pattern p =  Pattern.compile("^java",
      Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    Matcher m = p.matcher(
      "java has regex\nJava has regex\n" +
      "JAVA has pretty good regular expressions\n" +
      "Regular expressions are in Java");
    while(m.find())
      System.out.println(m.group());
  }
} /* Output:
java
Java
JAVA
*///:~
/**
* @Description: 这个例子中，将匹配所有以“java”，“Java”，“JAVA”等开头的行，并且是在设置了多行标记的状态下，对每一个行(从字符序列的第一个字符开始，到每一行的终结符)都进行匹配。
 * 注意，group方法只返回已匹配的部分
*/
