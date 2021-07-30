package com.wby.thread.manythread.Character18JAVAIO系统.node3添加属性和有用的接口;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/30 12:02
 * @Description: 装饰器在第15章引入。Java I/O类库需要多种不同功能的组合，这正是使用装饰器模式的理由所在(很难说这就是一个很好的设计选择，尤其是与其他程序设计语言中的简单I/O类库相比较。
 * 但它的确是如此选择的一个恰当理由。)。这也是Java I/O类库里存在filter（过滤器）类的原因所在抽象类flter是所有装饰器类的基类。
 * 装饰器必须具有和它所装饰的对象相同的接口，但它也可以扩展接口，而这种情况只发生在个别filter类中。
 *
 * 但是，装饰器模式也有一个缺点∶在编写程序时，它给我们提供了相当多的灵活性（因为我们可以很容易地混合和匹配属性），但是它同时也增加了代码的复杂性。
 * Java I/O类库操作不便的原因在于;我们必须创建许多类——"核心"I/O类型加上所有的装饰器，才能得到我们所希望的单个I/O对象。
 *
 * FilterInputStream和FilterOutputStream是用来提供装饰器类接口以控制特定输入流（InputStream）和输出流（OutputStream）的两个类，它们的名字并不是很直观。
 * FilterInput- Stream和FilterOutputStream分别自I/O类库中的基类InputStream和OutputStream派生而来，这两个类是装饰器的必要条件（以便能为所有正在被修饰的对象提供通用接口）。
 */
public class Text {
}
