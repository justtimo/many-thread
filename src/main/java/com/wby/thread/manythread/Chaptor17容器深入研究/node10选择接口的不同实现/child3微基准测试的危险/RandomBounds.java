package com.wby.thread.manythread.Chaptor17容器深入研究.node10选择接口的不同实现.child3微基准测试的危险;//: containers/RandomBounds.java
// Does Math.random() produce 0.0 and 1.0?
// {RunByHand}

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description: 在编写所i谓的微基准测试时。你必须要当心。不能做大多的假设。并日要将你的测试窄化，以使得它们尽可能地只在感兴趣的事项 上花费精力。
 * 你还必须仔细地确保你的测试运行足够长的时间，以产生有意义的数据，并且要考虑到某些Java HotSpot技术只有在程序运行了—段时间之后才会踢爆问题（这对于短期运行的程序来说也很重要）。
 *
 * 根据计算机和所使用的JVM的不同，所产生的结果也会有所不同，因此你应该自己运行这些测试，以验证得到的结果与本书所示的结果是否相同。你不应该过于关心这些绝对数字，
 * 将其看作是一种容器类型与另一种之间的性能比较。
 *
 * 剖析器可以把性能分析工作做得比你好。Java提供了一个剖析器（查看http∶//MinView.net/ Books/BetterJava处的补充材料），另外还有很多第三方的自由/开源的和常用的剖析器可用。
 *
 * 有一个与Math.randomO相关的示例。它产生的是0到1的值吗?包括还是不包括1?用数学术语表示，就是它是（0.1）、【0，1】、（0，1】还是【0，1）?（方括号表示"包括"，而圆括号表示"不包括"。）下面的测试程序也许可以提供答案∶
*/
public class RandomBounds {
  static void usage() {
    print("Usage:");
    print("\tRandomBounds lower");
    print("\tRandomBounds upper");
    System.exit(1);
  }
  public static void main(String[] args) {
    if(args.length != 1) usage();
    if(args[0].equals("lower")) {
      while(Math.random() != 0.0)
        ; // Keep trying
      print("Produced 0.0!");
    }
    else if(args[0].equals("upper")) {
      while(Math.random() != 1.0)
        ; // Keep trying
      print("Produced 1.0!");
    }
    else
      usage();
  }
} ///:~
/**
* @Description: 为了运行这个程序，你需要键入下面两行命令行中的一行∶ java RandomBounds lower或
 * java RandomBounds upper
 *
 * 在这两种情况中，你都需要手工终止程序，因此看起来好像Math.randomO永远都不会产生 0.0或1.0。但是这正是这类试验产生误导之所在。如果你选取0到1之间大约262个不同的双精度小数，
 * 那么通过试验产生其中任何一个值的可能性也许都会超过计算机，甚至是试验员本身的生命周期。已证明0.0是包含在Math.randomO的输出中的，按照数学术语，即其范围是【0，1）。
 *
 * 因此，你必须仔细分析你的试验，并理解它们的局限性。
*/
