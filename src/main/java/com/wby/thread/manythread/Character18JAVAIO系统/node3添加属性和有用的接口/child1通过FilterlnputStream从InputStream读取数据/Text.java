package com.wby.thread.manythread.Character18JAVAIO系统.node3添加属性和有用的接口.child1通过FilterlnputStream从InputStream读取数据;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/30 14:35
 * @Description: FilterInputStream类能够完成两件完全不同的事情。其中，DataInputStream允许我们读取不同的基本类型数据以及String对象（所有方法都以"read"开头，
 * 例如readByteO、readFloat（等等）。搭配相应的DataOutputStream，我们就可以通过数据"流"将基本类型的数据从一个地方迁移到另一个地方。具体是哪些"地方"是由表18-1中的那些类决定的。
 *
 * 其他FilterInputstream类则在内部修改InputStream的行为方式;是否缓冲，是否保留它所读过的行 （允许我们查询行数或设置行数），以及是否把单—字符推回输入流等等。
 * 最后两个类看起来更像是为了创建一个编译器 （它们被添加进来可能是为了对"用Java构建编译器"实验提供支持），因此我们在一般编程中不会用到它们。
 *
 * 我们几乎每次都要对输入进行缓冲——不管我们正在连接的是什么I/O设备，所以，I/O类库把无缓冲输入（而不是缓冲输入）作为特殊情况（或只是方法调用）就显得更加合理了。 FilterInputStream的类型及功能如表18-3所示。
 *
 *
 *                                                          表18-3 FiltelnputStream类型
 * 类                                                            功 能
 * DatalnputStream                                              与DataOutputStream搭配使用，因此我们可以按照可移植方式从流读取基本数据类型（int，char，long等）           InmputStreamn
 *                                                                                                                                                           包含用于读取基本类型数据的全部接口
 *
 * BufferedInputStream                                          使用它可以防止每次读取时都得进行实际写操作。代表"使用缓冲区"                                             InputStream，可以指定缓冲区大小（可选的）
 *                                                                                                                                                              本质上不提供接口，只不过是向进程中添加
 *                                                                                                                                                              缓冲区所必需的。
 *                                                                                                                                                              与接口对象搭配 InputStream
 *
 * LineNumberInputStream                                        跟踪输入流中的行号;可调用getLine Number（）和setLineNumber（int）                                    InputStream
 *                                                                                                                                                              仅增加了行号，因此可能要与接口对象搭配使用
 *
 * PushbackInputStream                                          具有"能弹出一个字节的缓冲区"。因此可以将读到的最后一个字符回退                                            InputStream
 *                                                                                                                                                              通常作为编译器的扫描器，之所以包含在内是
 *                                                                                                                                                              因为Java编译器的需要，我们可能永远不会用到
 */
public class Text {
}
