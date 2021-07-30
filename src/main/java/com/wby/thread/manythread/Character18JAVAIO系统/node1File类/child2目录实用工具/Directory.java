//: net/mindview/util/Directory.java
// Produce a sequence of File objects that match a
// regular expression in either a local directory,
// or by walking a directory tree.
package com.wby.thread.manythread.Character18JAVAIO系统.node1File类.child2目录实用工具;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

import static com.wby.thread.manythread.net.mindview.util.Print.print;

/**
* @Description:  程序设计中一项常见的任务就是在文件集上执行操作，这些文件要么在本地目录中，要么遍布于整个目录树中。如果有一种工具能够为你产生这个文件集，那么它会非常有用。
 *
 * 下面的实用工具类就可以通过使用local（）方法产生由本地目录中的文件构成的File对象数组，或者通过使用walkO方法产生给定目录下的由整个目录树中所有文件构成的List<File>
 *     （File对象比文件名更有用，因为File对象包含更多的信息）。这些文件是基于你提供的正则表达式而被选中的∶
*/
public final class Directory {
  public static File[]
  local(File dir, final String regex) {
    return dir.listFiles(new FilenameFilter() {
      private Pattern pattern = Pattern.compile(regex);
      public boolean accept(File dir, String name) {
        return pattern.matcher(
          new File(name).getName()).matches();
      }
    });
  }
  public static File[]
  local(String path, final String regex) { // Overloaded
    return local(new File(path), regex);
  }
  // A two-tuple for returning a pair of objects:
  public static class TreeInfo implements Iterable<File> {
    public List<File> files = new ArrayList<File>();
    public List<File> dirs = new ArrayList<File>();
    // The default iterable element is the file list:
    public Iterator<File> iterator() {
      return files.iterator();
    }
    void addAll(TreeInfo other) {
      files.addAll(other.files);
      dirs.addAll(other.dirs);
    }
    public String toString() {
      return "dirs: " + PPrint.pformat(dirs) +
        "\n\nfiles: " + PPrint.pformat(files);
    }
  }
  public static TreeInfo
  walk(String start, String regex) { // Begin recursion
    return recurseDirs(new File(start), regex);
  }
  public static TreeInfo
  walk(File start, String regex) { // Overloaded
    return recurseDirs(start, regex);
  }
  public static TreeInfo walk(File start) { // Everything
    return recurseDirs(start, ".*");
  }
  public static TreeInfo walk(String start) {
    return recurseDirs(new File(start), ".*");
  }
  static TreeInfo recurseDirs(File startDir, String regex){
    TreeInfo result = new TreeInfo();
    for(File item : startDir.listFiles()) {
      if(item.isDirectory()) {
        result.dirs.add(item);
        result.addAll(recurseDirs(item, regex));
      } else // Regular file
        if(item.getName().matches(regex))
          result.files.add(item);
    }
    return result;
  }
  // Simple validation test:
  public static void main(String[] args) {
    if(args.length == 0)
      System.out.println(walk("."));
    else
      for(String arg : args)
       System.out.println(walk(arg));
  }
} ///:~
/**
* @Description: localO方法使用被称为listFileO）的File.list（的变体来产生File数组。可以看到，它还使用了 FilenameFilter。如果需要List而不是数组，你可以使用Arrays.asListO自己对结果进行转换。
 * walk（）方法将开始目录的名字转换为File对象，然后调用recurseDirsO，该方法将递归地遍历目录，并在每次递归中都收集更多的信息。为了区分普通文件和目录，
 * 返回值实际上是一个对象"元组"——-—个List持有所有普通文件，另一个持有目录。
 *
 * 这里，所有的域都被有意识地设置成了public，因为TreeInfo的使命只是将对象收集起来——如果你只是返回List，那么就不需要将其设置为private，因为你只是返回一个对象对，不需要将它们设置为private。
 * 注意， TreeInfo实现了Iterable<File>，它将产生文件，使你拥有在文件列表上的"默认迭代"，而你可以通过声明".dirs"来指定目录。
 *
 * TreeInfo.toString（方法使用了一个"灵巧打印机"类，以使输出更容易浏览。容器默认的 toString（）方法会在单个行中打印容器中的所有元素，对于大型集合来说，这会变得难以阅读，
 * 因此你可能希望使用可替换的格式化机制。下面是一个可以添加新行并缩排所有元素的工具∶
*/
class PPrint {
  public static String pformat(Collection<?> c) {
    if(c.size() == 0) return "[]";
    StringBuilder result = new StringBuilder("[");
    for(Object elem : c) {
      if(c.size() != 1)
        result.append("\n  ");
      result.append(elem);
    }
    if(c.size() != 1)
      result.append("\n");
    result.append("]");
    return result.toString();
  }
  public static void pprint(Collection<?> c) {
    System.out.println(pformat(c));
  }
  public static void pprint(Object[] c) {
    System.out.println(pformat(Arrays.asList(c)));
  }
} ///:~
/**
* @Description: pformatO方法可以从Collection中产生格式化的String，而pprintO方法使用pformatO）来执行其任务。注意，没有任何元素和只有一个元素这两种特例进行了不后同的处理。
 * 上面还有—个用于数组的pprintO版本。
 *
 * Directory实用工具放在了net.mindview.util包中，以使其可以更容易地被获得。下面的例子说明了你可以如何使用它的样本∶
*/
class DirectoryDemo {
  public static void main(String[] args) {
    // All directories:
    PPrint.pprint(Directory.walk(".").dirs);
    // All files beginning with 'T'
    for(File file : Directory.local(".", "T.*"))
      print(file);
    print("----------------------");
    // All Java files beginning with 'T':
    for(File file : Directory.walk(".", "T.*\\.java"))
      print(file);
    print("======================");
    // Class files containing "Z" or "z":
    for(File file : Directory.walk(".",".*[Zz].*\\.class"))
      print(file);
  }
} /* Output: (Sample)
[.\xfiles]
.\TestEOF.class
.\TestEOF.java
.\TransferTo.class
.\TransferTo.java
----------------------
.\TestEOF.java
.\TransferTo.java
.\xfiles\ThawAlien.java
======================
.\FreezeAlien.class
.\GZIPcompress.class
.\ZipCompress.class
*///:~
/**
* @Description: 你可能需要更新一下在第13章中学习到的有关正则表达式的知识，以理解在localO）和walk）中的第二个参数。
 *
 * 我们可以更进一步，创建一个工具，它可以在目录中穿行，并且根据Strategy对象来处理这些目录中的文件（这是策略设计模式的另一个示例）;
*/
class ProcessFiles {
  public interface Strategy {
    void process(File file);
  }
  private Strategy strategy;
  private String ext;
  public ProcessFiles(Strategy strategy, String ext) {
    this.strategy = strategy;
    this.ext = ext;
  }
  public void start(String[] args) {
    try {
      if(args.length == 0)
        processDirectoryTree(new File("."));
      else
        for(String arg : args) {
          File fileArg = new File(arg);
          if(fileArg.isDirectory())
            processDirectoryTree(fileArg);
          else {
            // Allow user to leave off extension:
            if(!arg.endsWith("." + ext))
              arg += "." + ext;
            strategy.process(
                    new File(arg).getCanonicalFile());
          }
        }
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }
  public void
  processDirectoryTree(File root) throws IOException {
    for(File file : Directory.walk(
            root.getAbsolutePath(), ".*\\." + ext))
      strategy.process(file.getCanonicalFile());
  }
  // Demonstration of how to use it:
  public static void main(String[] args) {
    new ProcessFiles(new ProcessFiles.Strategy() {
      public void process(File file) {
        System.out.println(file);
      }
    }, "java").start(args);
  }
} /* (Execute to see output) *///:~
/**
* @Description: Strategy接口内嵌在ProcessFiles中，使得如果你希望实现它，就必须实现ProcessFiles. Strategy，它为读者提供了更多的上下文信息。
 * ProcessFiles执行了查找具有特定扩展名（传递给构造器的ext参数）的文件所需的全部工作，并且当它找到匹配的文件时，将直接把文件传递给Strategy对象（也是传递给构造器的参数）。
 *
 * 如果你没有提供任何参数，那么ProcessFiles就假设你希望遍历当前目录下的所有目录。你也可以指定特定的文件，带不带扩展名都可以（如果必需的话，它会添加上扩展名），或者指定一个或多个目录。
 *
 * 在mainO中，你看到了如何使用这个工具的基本示例，它可以根据你提供的命令行来打印所有的Java源代码文件的名字。
*/
