//: interfaces/Months.java
// Using interfaces to create groups of constants.
package com.wby.thread.manythread.character9接口.node7接口中的域;

/**
 * 因为你放入接口中的任何域都自动是static和final的，所以接口就成为了一种很便捷的用来创建常量组的工具。
 * 在Java SE5之前，这是产生与C或C++中的enum（枚举类型）具有相同效果的类型的唯一途径。因此在Java SE5之前的代码中你会看到下面这样的代码∶
 */
public interface Months {
  int
    JANUARY = 1, FEBRUARY = 2, MARCH = 3,
    APRIL = 4, MAY = 5, JUNE = 6, JULY = 7,
    AUGUST = 8, SEPTEMBER = 9, OCTOBER = 10,
    NOVEMBER = 11, DECEMBER = 12;
} ///:~
/**
 * 请注意，Java中标识具有常量初始化值的static final时，会使用大写字母的风格（在一个标识符中用下划线来分隔多个单词）。接口中的域自动是public的，所以没有显式地指明这一点。
 *
 * 有了Java SE5，你就可以使用更加强大而灵活的enum关键字，因此，使用接口来群组常量已经显得没什么意义了。
 * 但是，当你阅读遗留的代码时，在许多情况下你可能还是会碰到这种旧的习惯用法（www.MindView.net上关于本书的补充材料中，包含有关在Java SE5之前使用接口来生成枚举类型的方式的完整描述）。
 * 在第19章中可以看到更多的关于使用enum的细节说明。
 */
