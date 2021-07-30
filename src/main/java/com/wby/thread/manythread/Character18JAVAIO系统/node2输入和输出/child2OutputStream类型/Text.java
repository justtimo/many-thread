package com.wby.thread.manythread.Character18JAVAIO系统.node2输入和输出.child2OutputStream类型;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/30 11:55
 * @Description: 如表18-2所示，该类别的类决定了输出所要去往的目标∶字节数组（但不是String，不过你当然可以用字节数组自己创建）、文件或管道。
 *
 * 另外，FilterOutputStream为"装饰器"类提供了一个基类，"装饰器"类把属性或者有用的接口与输出流连接了起来，这些稍后会讨论。
 *
 *                                                  表18-2 OutputStream类型
 * 类                                                  功 能                                                       构造器参数
 *                                                                                                                如何使用
 *
 * ByteArrayOutputStream                            在内存中创建缓冲区。所有送往"流" .的数据都要放置在此缓冲区                缓冲区初始化尺寸（可选的）用于指定数据的目的地∶
 *                                                                                                                  将其与 Filter- OutputStream 对象相连以提供有用接口
 *
 * FileOutputStream                                用于将信息写至文件                                                 字符串，表示文件名、文件或File- Descriptor 对象。
 *                                                                                                                 指定数据的目的地∶将其与Filter- OutputStream
 *                                                                                                                  对象相连以提供有用接口
 *
 * PipedOutputStream                              任何写入其中的信息都会自动作为相关PipedInputStream的输出。               PipedInputStream
 *                                                 实现"管道化"概配念。                                                指定用于多线程的数据的目的地∶将其与FilterOutputStream
 *                                                                                                                  对象相连以提供有用接口
 *
 * FilterOutputStream                             抽象类，作为"装饰器"的接口。
 *                                                其中，"装饰器"为其他 OutputStream 提供有用功能。                         见表 18-4
 *                                                 见表18-4                                                           见表18-4
 */
public class Text {
}
