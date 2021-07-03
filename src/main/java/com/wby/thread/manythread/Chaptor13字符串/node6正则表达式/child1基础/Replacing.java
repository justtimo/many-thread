package com.wby.thread.manythread.Chaptor13字符串.node6正则表达式.child1基础;//: strings/Replacing.java
import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description: String类自带的最后一个正则表达式工具是“替换”。
 *  你可以只替换正则表达式第一个匹配的子串，或是替换所有匹配的地方
 *
 *  第一个表达式：以f开头，后面跟一个或多个字母，并且只替换第一个匹配的部分，所以found被替换为了located
 *  第二个表达式：匹配的是三个单词中的任意一个，并且替换匹配的所有部分
 *
 *  String之外的正则表达式还有更强大的替换工具，例如，可以通过方法调用执行替换。而且，如果正则表达式不是只使用一次的话，非String对象的正则表达式明显具备更加的性能
*/
public class Replacing {
  static String s = Splitting.knights;
  public static void main(String[] args) {
    print(s.replaceFirst("f\\w+", "located"));
    print(s.replaceAll("shrubbery|tree|herring","banana"));
  }
} /* Output:
Then, when you have located the shrubbery, you must cut down the mightiest tree in the forest... with... a herring!
Then, when you have found the banana, you must cut down the mightiest banana in the forest... with... a banana!
*///:~
