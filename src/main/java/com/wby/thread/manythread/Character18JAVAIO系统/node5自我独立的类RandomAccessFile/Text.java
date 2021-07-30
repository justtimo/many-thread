package com.wby.thread.manythread.Character18JAVAIO系统.node5自我独立的类RandomAccessFile;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/30 14:57
 * @Description: RandomAccessFile适用于由大小已知的记录组成的文件，所以我们可以使用seekO将记录从一处转移到另一处，然后读取或者修改记录。
 * 文件中记录的大小不一定都相同，只要我们能够确定那些记录有多大以及它们在文件中的位置即可。
 *
 * 最初，我们可能难以相信RandomAccessFile不是InputStream或者OutputStream继承层次结构中的一部分。除了实现了DataInput和DataOutput接口（DataInputStream
 * 和DataOutputStream也实现了这两个接口）之外，它和这两个继承层次结构没有任何关联。它甚至不使用 InputStream和OutputStream类中已有的任何功能。
 * 它是一个完全独立的类，从头开始编写其所有的方法（大多数都是本地的）。这么做是因为RandomAccessFile拥有和别的I/O类型本质不同的行为，因为我们可以在一个文件内向前和向后移动。
 * 在任何情况下，它都是自我独立的，直接从 Object派生而来。
 *
 * 从本质 上来说，RandomAccessFile的工作方式类似干把DataInputStream和DataOutStream组合起来使用，还添加了一些方法。其中方法
 * getFilePointer（）用于查找当前所处的文件位置，
 * seek（）用于在文件内移至新的位置，
 * length（）用于判断文件的最大尺寸。
 * 另外，其构造器还需要第二个参数（和C中的fopenO）相同）用来指示我们只是"随机读"（r）还是"既读又写"（rw）。它并不支持只写文件，
 * 这表明RandomAccessFile若是从DatalInputStream继承而来也可能会运行得很好。
 *
 * 只有RandonAccessFile支持搜寻方法，并且只适用于文件。BufferedInputStream却能允许标注（mark））位置（其值存储于内部某个简单变量内）和重新设定位置（resetO），
 * 但这些功能很有限，不是非常有用。
 *
 * 在JDK 1.4中，RandomAccessFile的大多数功能（但不是全部）由nio存储映射文件所取代，本章稍后会讲述。
 */
public class Text {
}
