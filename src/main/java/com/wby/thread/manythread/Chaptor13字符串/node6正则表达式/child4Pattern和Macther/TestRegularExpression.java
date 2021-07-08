package com.wby.thread.manythread.Chaptor13字符串.node6正则表达式.child4Pattern和Macther;//: strings/TestRegularExpression.java
// Allows you to easily try out regular expressions.
// {Args: abcabcabcdefabc "abc+" "(abc)+" "(abc){2,}" }

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description: 一般来说，比起功能有限的String我们更愿意构造一个功能强大的正则表达式对象。
 * 使用Pattern.compile方法变异你的正则表达式即可。他会根据传入的String类型的正则表达式生成一个Pattern对象，然后将要检索的字符串传入matcher方法。
 * matcher方法会生成一个Matcher对象，他有很多功能，例如可以使用replaceAll将所有匹配的部分替换成你传入的参数。
 *
 * 下面的例子：第一个控制台参数是将来要搜索匹配的输入字符串，后面一个或多个参数都是正则表达式，他们将用来在第一个字符串中查找匹配
*/
public class TestRegularExpression {
  public static void main(String[] args) {
    if(args.length < 2) {
      print("Usage:\njava TestRegularExpression " +
        "characterSequence regularExpression+");
      System.exit(0);
    }
    print("Input: \"" + args[0] + "\"");
    for(String arg : args) {
      print("Regular expression: \"" + arg + "\"");
      Pattern p = Pattern.compile(arg);
      Matcher m = p.matcher(args[0]);
      while(m.find()) {
        print("Match \"" + m.group() + "\" at positions " +
          m.start() + "-" + (m.end() - 1));
      }
    }
  }
} /* Output:
Input: "abcabcabcdefabc"
Regular expression: "abcabcabcdefabc"
Match "abcabcabcdefabc" at positions 0-14
Regular expression: "abc+"
Match "abc" at positions 0-2
Match "abc" at positions 3-5
Match "abc" at positions 6-8
Match "abc" at positions 12-14
Regular expression: "(abc)+"
Match "abcabcabc" at positions 0-8
Match "abc" at positions 12-14
Regular expression: "(abc){2,}"
Match "abcabcabc" at positions 0-8
*///:~
/**
* @Description: 同时，Pattern类还提供了static方法：
 *  public static boolean matches(String regex, CharSequence input)
 *  该方法用来检查regex是否匹配整个CharSequence类型的input参数。
 *  编译后的Pattern对象还提供了split方法，。他从匹配了regex的地方分割输入字符串，返回分割后的子字符串String数组。
 *
 *  使用Matcher上的方法，。我们能够判断各种不同类型的匹配是否成功：
 *  lookingAt方法用来判断该字符串（不必是整个字符串）的始部分是否能够匹配模式。
 *
*/
