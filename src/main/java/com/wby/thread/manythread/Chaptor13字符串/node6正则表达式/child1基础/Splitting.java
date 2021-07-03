package com.wby.thread.manythread.Chaptor13字符串.node6正则表达式.child1基础;//: strings/Splitting.java

import java.util.Arrays;

/**
* @Description: String类还自带了一个非常有用的正则表达式工具-split方法，功能是：将字符串从正则表达式匹配的地方切开
 *
 *  第一个split：只使用了普通的字符作为正则表达式，其中不包含任何特殊字符，因此只是按照空格来进行划分
 *  第二个split和第三个split：都用到了\W，他的意思是非单词字符(如果W小写，\w则表示一个单词字符)
 *    通过第二个方法输出可以看到，他把标点符号删除了。
 *  第三个split：表示“字母n后面跟着一个或多个非单词字符”。
 *
 *  可以看到，在原始字符串中，与正则表达式匹配的部分，在最终结果中都不存在了
 *
 *  split方法还有一个重载版本，它允许你限制字符串分割的次数。
*/
public class Splitting {
  public static String knights =
    "Then, when you have found the shrubbery, you must " +
    "cut down the mightiest tree in the forest... " +
    "with... a herring!";
  public static void split(String regex) {
    System.out.println(
      Arrays.toString(knights.split(regex)));
  }
  public static void main(String[] args) {
    split(" "); // Doesn't have to contain regex chars
    split("\\W+"); // Non-word characters
    split("n\\W+"); // 'n' followed by non-word characters
  }
} /* Output:
[Then,, when, you, have, found, the, shrubbery,, you, must, cut, down, the, mightiest, tree, in, the, forest..., with..., a, herring!]
[Then, when, you, have, found, the, shrubbery, you, must, cut, down, the, mightiest, tree, in, the, forest, with, a, herring]
[The, whe, you have found the shrubbery, you must cut dow, the mightiest tree i, the forest... with... a herring!]
*///:~
