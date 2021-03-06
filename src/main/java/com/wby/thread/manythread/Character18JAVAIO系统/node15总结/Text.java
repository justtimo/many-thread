package com.wby.thread.manythread.Character18JAVAIO系统.node15总结;

/**
 * Java I/O流类库的确能满足我们的基本需求;我们可以通过控制台、文件、内存块，甚至因特网进行读写。通过继承，我们可以创建新类型的输入和输出对象。并且通过重新定 义 toStringO方法，
 * 我们甚其至可以对流接受的对象类型进行简单扩充。当我们向—个期望收到字符串的方法传送一个对象时，会自动调用toStringO方法（这是Java有限的自动类型转换功能）。
 *
 * 在I/O流类库的文档和设计中，仍留有一些没有解决的问题。例如。当我们打开一个文件用于输出时，我们可以指定一旦试图覆盖该文件就抛出一个异常——有的编程系统允许我们自行指定想要打开的输出文件，
 * 只要它尚不存在。在Java中，我们似乎应该使用一个File对象来判断某个文件是否存在，因为如果我们以FileOutputStream或者FileWriter打开，那么它肯定会被覆盖。
 *
 * I/O流类库使我们喜忧参半。它确实能做许多事情，而且具有可移植性。但是如果我们没有理解"装饰器"模式，那么这种设计就不是很直觉，因此，在学习和传授它的过程中，需要额外的开销。
 * 而且它并不完善;例如，我应该不必去写像TextFile这样的应用（新的Java SE5的 PrintWriter向正确的方向迈进了一步，但是它只是一个部分的解决方案）。在Java SE5中有一个巨大的改进;
 * 他们最终添加了输出格式化，而事实上其他所有语言的I/O包都提供这种支持。
 *
 * 一旦我们理解了装饰器模式，并开始在某些情况下使用该类库以利用其提供的灵活性，那么你就开始从这个设计中受益了。到那个时候，为此额外多写几行代码的开销应该不至于使人觉得太麻烦。
 */
public class Text {
}
