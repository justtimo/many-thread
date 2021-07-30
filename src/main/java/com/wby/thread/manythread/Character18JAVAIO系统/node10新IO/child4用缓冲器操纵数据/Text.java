package com.wby.thread.manythread.Character18JAVAIO系统.node10新IO.child4用缓冲器操纵数据;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/30 15:59
 * @Description: 下面的图阐明了nio类之间的关系，便于我们理解怎么移动和转换数据。例如，如果想把一个字节数组写到文件中去，那么就应该使用ByteBuffer.wrapO方法把字节数组包装起来，
 * 然后用getChannel（）方法在FileOutputStream上打开一个通道，接着将来自于ByteBuffer的数据写到 FileChannel中（如下页图所示）。
 *
 * 注意; ByteBuffer是将数据移进移出通道的唯一方式，并且我们只能创建一个独立的基本类型缓冲器，或者使用"as"方法从ByteBuffer中获得。也就是说，我们不能把基本类型的缓冲器转换成ByteBuffer。
 * 然而，由于我们可以经由视图缓冲器将基本类型数据移进移出ByteBuffer，所以这也就不是什么真正的限制了。
 */
public class Text {
}
