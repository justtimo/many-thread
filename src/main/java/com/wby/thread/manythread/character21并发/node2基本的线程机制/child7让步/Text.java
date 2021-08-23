package com.wby.thread.manythread.character21并发.node2基本的线程机制.child7让步;

/**
 * 如果知道已经完成了在runO方法的循环的一次迭代过程中所需的工作，就可以给线程调度机制一个暗示∶你的工作已经做得差不多了，可以让别的线程使用CPU了。
 * 这个暗示将通过调用yieldO方法来作出（不过这只是一个暗示，没有任何机制保证它将会被采纳）。当调用yieldO时，你也是在建议具有相同优先级的其他线程可以运行。
 *
 * LiftOff，java使用yieldO）在各种不同的LiftOff任务之间产生分布良好的处理机制。尝试着注释掉LiftOff，runO中的Thread.yieldO，以查看区别。
 * 但是，大体上，对于任何重要的控制或在调整应用时，都不能依赖于yield（）。实际上，yieldO经常被误用。
 */
public class Text {
}
