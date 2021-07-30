package com.wby.thread.manythread.Character18JAVAIO系统.node4Reader和Writer.child1数据的来源和去处;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/30 14:47
 * @Description: 几乎所有原始的Java I/O流类都有相应的Reader和Writer类来提供天然的Unicode操作。然而在某些场合，面向字节的InputStream和OutputStream才是正确的解决方案;
 * 特别是， java.util.zip类库就是面向字节的而不是面向字符的。因此，最明智的做法是尽量尝试使用 Reader 和Writer，一旦程序代码无法成功编译，我们就会发现自己不得不使用面向字节的类库。
 *
 *
 * 下面的表展示了在两个继承层次结构中，信息的来源和去处（即数据物理上来自哪里及去向哪里）之间的对应关系∶
 *
 * 来源与去处∶ Java 1.0类 .                                       相应的 Java 1.1 类
 * InmpuStrearm                                                 Render
 *                                                              适配器∶InputStreamReader
 *
 * OutputStream                                                 Writer
 *                                                              适配器∶ OutputStreamWriter
 *
 * FilenpuStream                                                FileReader
 *
 * FileOutputStream                                             FieWriter
 *
 * StringBufferInputStrean（已弃用）                             StringReader
 *
 * （无相应的类）                                                StringWriter
 *
 * ByteArrayInputStream                                         CharArrayReader
 *
 * ByteArrayOutputStream                                        ChararrayWriter
 *
 * PipedIlputStream                                             PipedReader
 *
 * PipedOutputStream                                            PipedWriter
 *
 * 大体 上，我们会发现，这两个不同的继承层次结构中的接口即使不能说完全相同，但也是非常相似。
 **
 *  *
 */
public class Text {
}
