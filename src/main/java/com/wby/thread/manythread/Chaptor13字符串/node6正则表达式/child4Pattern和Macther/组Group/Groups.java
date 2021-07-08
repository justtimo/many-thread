package com.wby.thread.manythread.Chaptor13字符串.node6正则表达式.child4Pattern和Macther.组Group;//: strings/Groups.java


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
import static com.wby.thread.manythread.net.mindview.util.Print.printnb;

/**
* @Description: 组是用括号划分的正则表达式。
 *  可以根据某个组编号来引用某个组，组号为0表示整个表达式，组号1表示第一个被括号括起来的组，以此类推。
 *  因此这个表达式A(B(C))D，有三个组，组0是ABCD，组1是BC，组2是C
 *
 *  Matcher对象提供了一系列方法用以获取组相关的信息：
 *    groupCount方法返回该匹配器的模式中的分组数目，第0组不包括在内。
 *    group方法返回前一次匹配操作（例如find方法）的第0组（整个匹配）
 *    group(int i)返回前一次匹配操作期间指定的组号，如果匹配成功，但是指定的组没有匹配输入字符串的任何部分，则会返回null。
 *    start(int group)方法返回前一次匹配操作中寻找到的组的起始索引。
 *    end方法返回前一次匹配操作中寻找到的组的最后一个字符索引加一的值
*/
public class Groups {
  static public final String POEM =
    "Twas brillig, and the slithy toves\n" +
    "Did gyre and gimble in the wabe.\n" +
    "All mimsy were the borogoves,\n" +
    "And the mome raths outgrabe.\n\n" +
    "Beware the Jabberwock, my son,\n" +
    "The jaws that bite, the claws that catch.\n" +
    "Beware the Jubjub bird, and shun\n" +
    "The frumious Bandersnatch.";
  public static void main(String[] args) {
    Matcher m =
      Pattern.compile("(?m)(\\S+)\\s+((\\S+)\\s+(\\S+))$")
        .matcher(POEM);
    while(m.find()) {
      for(int j = 0; j <= m.groupCount(); j++)
        printnb("[" + m.group(j) + "]");
      print();
    }
  }
} /* Output:
[the slithy toves][the][slithy toves][slithy][toves]
[in the wabe.][in][the wabe.][the][wabe.]
[were the borogoves,][were][the borogoves,][the][borogoves,]
[mome raths outgrabe.][mome][raths outgrabe.][raths][outgrabe.]
[Jabberwock, my son,][Jabberwock,][my son,][my][son,]
[claws that catch.][claws][that catch.][that][catch.]
[bird, and shun][bird,][and shun][and][shun]
[The frumious Bandersnatch.][The][frumious Bandersnatch.][frumious][Bandersnatch.]
*///:~
/**
* @Description: 可以看到这个正则表达式模式有许多圆括号分组，由任意数目的非空格字符(\S+)以及随后的任意数目的空格字符(\s+)所组成。
 * 目的是捕获每行的最后三个词，每行最后以$结束。
 * 不过正常情况下是将$与整个输入序列的末端相匹配。所以我们一定要告知正则表达式注意输入序列中的换行符。这可以由序列开头的模式标记(?m)来完成
*/
