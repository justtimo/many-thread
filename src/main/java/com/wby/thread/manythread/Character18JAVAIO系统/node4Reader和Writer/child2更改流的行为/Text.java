package com.wby.thread.manythread.Character18JAVAIO系统.node4Reader和Writer.child2更改流的行为;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/30 14:53
 * @Description: 对干InputStream和OutputStream来说，我们会使用FilterInputStream和FilterOutputStream的装饰器子类来修改，"流"以满足特殊需要。
 * Reader和Writer的类继承层次结构继续沿用相同的思想——但是并不完全相同。
 *
 * 在下表中，相对于前一表格来说，左右之间的对应关系的近似程度更加粗略—些。造成这种差别的原因是因为类的组织形式不同;尽管BufferedOutputStream是FilterOutputStream的子类，
 * 但是BufferedWriter并不是FilterWriter的子类（尽管FilterWriter是抽象类，没有任何子类，把它放在那里也只是把它作为一个占位符，或仅仅让我们不会对它所在的地方产生疑惑）。然而，这些类的接口却十分相似。
 *
 *
 * 过滤器∶ Java 1.0 类
 * FiterInputStream
 *
 * FilterOutputStream
 *
 * BufferedInputStream
 *
 * BufferedOutputStream
 *
 * DatalnputStream
 *
 * PritStream
 *
 * FilterReader
 *
 * 有一点很清楚∶无论我们何时使用readLineO，都不应该使用DatalnputStream（这会遭到编译器的强烈反对），而应该使用BufferedReader。除了这一点，DatalInputStream仍是I/O类库的首选成员。
 *  为了更容易地过渡到使用PrintWriter，它提供了一个既能接受Writer对象又能接受任何 OutputStream对象的构造器。PrintWriter的格式化接口实际上与PrintStream相同。
 *
 *  在Java SE5中添加了PrintWriter构造器，以简化在将输出写入时的文件创建过程，你马上就会看到它。
 * 有一种PrintWriter构造器还有一个选项，就是"自动执行清空"选项。如果构造器设置此选项，则在每个Println（）执行之后，便会自动清空。
 */
public class Text {
}
