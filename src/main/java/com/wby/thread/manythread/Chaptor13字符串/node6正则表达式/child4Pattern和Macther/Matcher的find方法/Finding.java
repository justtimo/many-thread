package com.wby.thread.manythread.Chaptor13字符串.node6正则表达式.child4Pattern和Macther.Matcher的find方法;//: strings/Finding.java

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
import static com.wby.thread.manythread.net.mindview.util.Print.printnb;

/**
* @Description: find方法可以用来在CharSquence中查找多个匹配
*/
public class Finding {
  public static void main(String[] args) {
    Matcher m = Pattern.compile("\\w+")
      .matcher("Evening is full of the linnet's wings");
    while(m.find())
      printnb(m.group() + " ");
    print();
    int i = 0;
    while(m.find(i)) {
      printnb(m.group() + " ");
      i++;
    }
  }
} /* Output:
Evening is full of the linnet s wings
Evening vening ening ning ing ng g is is s full full ull ll l of of f the the he e linnet linnet innet nnet net et t s s wings wings ings ngs gs s
*///:~
/**
* @Description: 模式\\w+将字符串划分为单词。find方法向迭代器那样向前遍历输入的字符串。
 *  而第二个find能够接受一个整数作为参数，该参数表示字符在字符串中的位置，并以其作为搜索的起点。
 *  从结果过中可以看到，后一个版本的find方法能根据其参数的值，不断重新设定搜索的起始位置。
*/
