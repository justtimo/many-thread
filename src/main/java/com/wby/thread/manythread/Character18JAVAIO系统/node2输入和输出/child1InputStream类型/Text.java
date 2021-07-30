package com.wby.thread.manythread.Character18JAVAIO系统.node2输入和输出.child1InputStream类型;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/30 11:49
 * @Description: InputStream的作用是用来表示那些从不同数据源产生输入的类。如表18-1所示，这些数据源包括∶
 *
 * 1）字节数组。
 * 2） String对象。
 * 3） 文件。
 * 4）"管道"，工作方式与实际管道相似，即，从一端输入，从另一端输出。
 * 5）一个由其他种类的流组成的序列，以便我们可以将它们收集合并到一个流内。
 * 6）其他数据源，如Internet连接等。
 *
 * 每一种数据源都有相应的InputStream子类。
 * 另外，FilterInputStream也属于一种InputStream，为"装饰器"（decorator）类提供基类，
 * 其中，"装饰器"类可以把属性或有用的接口与输入流连接在一起。我们稍后再讨论它。
 *
 *                                                  表18-1 InputStream类型
 * 类                                            功 能                                           构造器参数
 *                                                                                              如何使用
 *
 * ByteArrayInputStream                         允许将内存的缓冲区当作InputStream使用                 缓冲区，字节将从中取出
 *                                                                                             作为一种数据源∶将其与FilterInputStream对象相连以提供有用接口
 *
 * StringBuffeInputStream                       将String转换成 InputStream                         字符串。底层实现实际使用StringBufer作为一种数据源∶
 *                                                                                                将其与 FilterInputStream对象相连以提供有用接口
 *
 * FilenputStream                               用于从文件中读取信息                                 字符串，表示文件名、文件或FileDescriptor对象
 *                                                                                                作为一种数据源∶将其与FiterInputStream对象相连以提供有用接口
 *
 * PipedInputStream                            产生用于写入相关PipedOutput Stream的数据。           PipedOutputStream
 *                                             实现"管道化"概念                                    作为多线程中数据源∶将其与FilterInput Stream 对象相连以提供有用接口
 *
 * SequenceInputStream                        将两个或多个InputStream对象转换成单一 InpuStream       两个InpufStream对象或一个容纳InputStream对象的容器Enumeration
 *                                                                                              作为一种数据源∶将其与FlterInputStream对象相连以提供有用接口见表 18-3见表18-3
 *
 * FilterInputStream                          抽象类，作为"装饰器"的接口。                              见表18-3
 *                                            其中，"装饰器"为其他的InputStream 类提供有用功能。           见表18-3
 *                                             见表18-3
 */
public class Text {
}
