package com.wby.thread.manythread.Character18JAVAIO系统.node1File类.child1目录列表器;//: io/DirList.java
// Display a directory listing using regular expressions.
// {Args: "D.*\.java"}

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
* @Description: 假设我们想查看一个目录列表，可以用两种方法来使用File对象。如果我们调用不带参数的 listO方法，便可以获得此File对象包含的全部列表。
 * 然而，如果我们想获得一个受限列表，例如，想得到所有扩展名为，java的文件，那么我们就要用到"目录过滤器"，这个类会告诉我们怎样显示符合条件的File对象。
 *
 * 下面是一个示例，注意，通过使用java.utils.Arrays.sort）和String.CASE_INSENSITIVE. ORDERComparator，可以很容易地对结果进行排序（按字母顺序）。
*/
public class DirList {
  public static void main(String[] args) {
    File path = new File(".");
    String[] list;
    if(args.length == 0)
      list = path.list();
    else
      list = path.list(new DirFilter(args[0]));
    Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
    for(String dirItem : list)
      System.out.println(dirItem);
  }
}

class DirFilter implements FilenameFilter {
  private Pattern pattern;
  public DirFilter(String regex) {
    pattern = Pattern.compile(regex);
  }
  public boolean accept(File dir, String name) {
    return pattern.matcher(name).matches();
  }
} /* Output:
DirectoryDemo.java
DirList.java
DirList2.java
DirList3.java
*///:~
/**
* @Description: 这里，DirFilter类"实现"了FilenameFilter接口。请注意FilenameFilter接口是多么的简单;
 *    public interface FilenameFi1ter {
 *        boolean accept(File dir,String name);
 *    }
 *
 * DirFilter这个类存在的唯一原因就是将acceptO）方法。创建这个类的目的在于把accept（）方法提供给listO使用，使listO可以回调acceptO，进而以决定哪些文件包含在列表中。
 * 因此，这种结构也常常称为回调。更具体地说，这是一个策略模式的例子，因为list（O实现了基本的功能，而且按照 FilenameFilter的形式提供了这个策略，以便完善list（O）在提供服务时所需的算法。
 * 因为 list（）接受FilenameFilter对象作为参数，这意味着我们可以传递实现了FilenameFilter接口的任何类的对象，用以选择 （甚至在运行时）listO方法的行为方式。策略的目的就是提供了代码行为的灵活性。
 *
 * acceptO方法必须接受一个代表某个特定文件所在目录的File对象，以及包含了那个文件名的一个String。记住一点∶ list（）方法会为此目录对象下的每个文件名调用acceptO），
 * 来判断该文件是否包含在内;判断结果由acceptO返回的布尔值表示。
 *
 * acceptO会使用一个正则表达式的matcher对象，来查看此正则表达式regex是否匹配这个文件的名字。通过使用acceptO，list（方法会返回一个数组。
*/
/**
* @Description: 匿名内部类
 *  这个例子很适合用一个匿名内部类（第8章介绍过）进行改写。首先创建一个flterO方法，它会返回一个指向FilenameFilter的引用;
*/
class DirList2 {
  public static FilenameFilter filter(final String regex) {
    // Creation of anonymous inner class:
    return new FilenameFilter() {
      private Pattern pattern = Pattern.compile(regex);
      public boolean accept(File dir, String name) {
        return pattern.matcher(name).matches();
      }
    }; // End of anonymous inner class
  }
  public static void main(String[] args) {
    File path = new File(".");
    String[] list;
    if(args.length == 0)
      list = path.list();
    else
      list = path.list(filter(args[0]));
    Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
    for(String dirItem : list)
      System.out.println(dirItem);
  }
} /* Output:
DirectoryDemo.java
DirList.java
DirList2.java
DirList3.java
*///:~
/**
* @Description: 注意，传向flterO）的参数必须是final的。这在匿名内部类中是必需的，这样它才能够使用来自该类范围之外的对象。
 *
 * 这个设计有所改进，因为现在FilenameFilter类紧密地和DirList2绑定在一起。然而，我们可以进一步修改该方法，定义一个作为list（）参数的匿名内部类;这样一来程序会变得更小;
*/
class DirList3 {
  public static void main(final String[] args) {
    File path = new File(".");
    String[] list;
    if(args.length == 0)
      list = path.list();
    else
      list = path.list(new FilenameFilter() {
        private Pattern pattern = Pattern.compile(args[0]);
        public boolean accept(File dir, String name) {
          return pattern.matcher(name).matches();
        }
      });
    Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
    for(String dirItem : list)
      System.out.println(dirItem);
  }
} /* Output:
DirectoryDemo.java
DirList.java
DirList2.java
DirList3.java
*///:~
/**
* @Description: 既然匿名内部类直接使用args【0】，那么传递给mainO方法的参数现在就是final的。
 *
 * 这个例子展示了匿名内部类怎样通过创建特定的。—次性的类来解决问题。此方法的—个优点献就是将解决特定问题的代码隔离、聚拢于一点。而另-一方面，这种方法却不易阅读，因此要谨慎使用。
*/
