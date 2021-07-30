package com.wby.thread.manythread.Character18JAVAIO系统.node4Reader和Writer;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/30 14:46
 * @Description: Java 1.1对基本的I/O流类库进行了重大的修改。当我们初次看见Reader和Writer类时，可能会以为这是两个用来替代InputStream和OutputStreamt的类;但实际上并非如此。
 * 尽管一些原始的"流"类库不再被使用（如果使用它们，则会收到编译器的警告信息），但是InputStream和OutputStreamt在以面向字节形式的I/O中仍可以提供极有价值的功能，
 * Reader和Writer则提供兼容Unicode与面向字符的I/O功能。另外;
 * 1）Java 1.1向InputStream和OutputStreamt继承层次结构中添加了一些新类，所以显然这两个类是不会被取代的。
 * 2）有时我们必须把来自于"字节"层次结构中的类和"字符"层次结构中的类结合起来使用。
 * 为了实现这个目的，要用到"适配器"（adapter）类∶InputStreamReader可以把 InputStream转换为Reader，而OutputStreamWriter可以把OutputStream转换为Writer。
 *
 * 设计Reader和Writer继承层次结构主要是为了国际化。老的I/O流继承层次结构仅支持8位字节流，并且不能很好地处理16位的Unicode字符。
 * 由于Unicode用于字符国际化（Java本身的 char也是16位的Unicode），所以添加Reader和Writer继承层次结构就是为了在所有的I/O操作中都支持Unicode。
 * 另外，新类库的设计使得它的操作比旧类库更快。
 *
 * 一如本书惯例，我会尽力给出所有类的概观，但是我还要假定你会自行使用JDK文档查看细节，例如方法的详尽列表。
 */
public class Text {
}
