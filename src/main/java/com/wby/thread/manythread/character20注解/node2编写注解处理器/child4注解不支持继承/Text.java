package com.wby.thread.manythread.character20注解.node2编写注解处理器.child4注解不支持继承;

/**
 * 不能使用关键字extends来继承某个@interface。这真是一个遗憾。如果可以定义一个@TableColumn注解（参考前面的建议），同时在其中嵌套一个@SQLType类型的注解，那么这将成为一个优雅的设计。
 * 按照这种方式，程序员可以继承@SQLType，从而创建出各种SQL类型，例如@SQLInteger和@SQLString等。如果注解允许继承的话，这将大大减少打字的工作量，并且使语法更整洁。
 * 在Java未来的版本中，似乎没有任何关于让注解支持继承的提案，所以，在当前状况下，上例中的解决方案可能已经是最佳方法了。
 */
public class Text {
}
