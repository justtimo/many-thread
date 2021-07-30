package com.wby.thread.manythread.Character18JAVAIO系统.node3添加属性和有用的接口.child2通过FilterOutPutStream向OutputStream写入;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/30 14:41
 * @Description: 与DataInputStream对应的是DataOutputStream，它可以将各种基本数据类型以及String对象格式化输出到"流"中;这样以来，任何机器上的任何DataInputStream都能够读取它们。
 * 所有方法都以"wirte"开头，例如writeByteO、writeFloatO等等。
 *
 * PrintStream最初的目的便是为了以可视化格式打印所有的基本数据类型以及String对象。这和DataOutputStream不同，后者的目的是将数据元素置入"流"中，使DataInputStream能够可移植地重构它们。
 *
 * PrintStream内有两个重要的方法∶print（）和println（）。对它们进行了重载，以便可打印出各种数据类型。printO和printlnO之间的差异是，后者在操作完毕后会添加一个换行符。
 *
 * PrintStream可能会有些问题，因为它捕捉了所有的IOExceptions （因此，我们必须使用 checkErrorO自行测试错误状态，如果出现错误它返回true）。
 * 另外，PrintStream也未完全国际化，不能以平台无关的方式处理换行动作（这些问题在printWriter中得到了解决，这在后面讲述）。
 *
 * BufferedOutputStream是一个修改过的OutputStream，它对数据流使用缓冲技术;因此当每次向流写入时，不必每次都进行实际的物理写动作。所以在进行输出时，我们可能更经常的是使用它。
 *
 * FilterOutputStream的类型及功能如表18-4所示。
 *
 * 表18-4 FilterOutputStream类型
 * 类                                                      功 能                                                           构造器参数如何使用
 * DataOutputStream                                        与DataInputStream 搭配使用，因此可以按照可移植方式                    OutputStream
 *                                                          向流中写入基本类型数据 it，char， long等）                           包含用于写入基本类型数据的全部接口
 *
 * PrintStrearm                                             用于产生格式化输出。其中DataOut- putStream                          OutputStream，
 *                                                          处理数据的存储，PrintfStream处理显示                                可以用 boolean值指示是否在每次换行时清空缓冲区（可选的）
 *                                                                                                                          应该是对OutputStream 对象的"final"封装。可能会经常使用到它
 *
 * BufferedOutputStream                                     使用它以避免每次发送数据时都要进行实际的写操作。                        OutputStrvam， 可以指定缓冲区大小（可选的）
 *                                                          代表"使用缓冲区"。可以调用 flush（ 清空缓冲区                         本质上并不提供接口，只不过是向进程中添加缓冲区所必需的。与接口对象搭配
 *
 *
 *
 *
 * Q2
 *
 *
 *
 */
public class Text {
}
