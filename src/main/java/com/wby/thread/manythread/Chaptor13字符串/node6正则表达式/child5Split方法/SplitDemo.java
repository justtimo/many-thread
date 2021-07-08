package com.wby.thread.manythread.Chaptor13字符串.node6正则表达式.child5Split方法;//: strings/SplitDemo.java

import java.util.Arrays;
import java.util.regex.Pattern;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
 * @Description:  split方法将输入字符串段开成字符串数组，断开边界由正则表达式确定：
 * String[] split(CharSequence input)
 * String[] split(CharSequence input, int limit)
 */
public class SplitDemo {
  public static void main(String[] args) {
    String input =
      "This!!unusual use!!of exclamation!!points";
    print(Arrays.toString(
      Pattern.compile("!!").split(input)));
    // Only do the first three:
    print(Arrays.toString(
      Pattern.compile("!!").split(input, 3)));
  }
} /* Output:
[This, unusual use, of exclamation, points]
[This, unusual use, of exclamation!!points]
*///:~
/**
* @Description: 第二种形式的split方法可以限制将输入分割成字符串的数量
*/
