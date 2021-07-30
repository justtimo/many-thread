package com.wby.thread.manythread.Character18JAVAIO系统.node1File类.child3目录的检查及创建;//: io/MakeDirectories.java
// Demonstrates the use of the File class to
// create directories and manipulate files.
// {Args: MakeDirectoriesTest}

import java.io.File;

/**
* @Description:  File类不仅仅只代表存在的文件或目录。也可以用File对象来创建新的目录或尚不存在的整个目录路径。我们还可以查看文件的特性（如∶大小，最后修改日期，读/写），
 * 检查某个File对象代表的是一个文件还是一个目录，并可以删除文件。下面的示例展示了File类的一些其他方法。
*/
public class MakeDirectories {
  private static void usage() {
    System.err.println(
      "Usage:MakeDirectories path1 ...\n" +
      "Creates each path\n" +
      "Usage:MakeDirectories -d path1 ...\n" +
      "Deletes each path\n" +
      "Usage:MakeDirectories -r path1 path2\n" +
      "Renames from path1 to path2");
    System.exit(1);
  }
  private static void fileData(File f) {
    System.out.println(
      "Absolute path: " + f.getAbsolutePath() +
      "\n Can read: " + f.canRead() +
      "\n Can write: " + f.canWrite() +
      "\n getName: " + f.getName() +
      "\n getParent: " + f.getParent() +
      "\n getPath: " + f.getPath() +
      "\n length: " + f.length() +
      "\n lastModified: " + f.lastModified());
    if(f.isFile())
      System.out.println("It's a file");
    else if(f.isDirectory())
      System.out.println("It's a directory");
  }
  public static void main(String[] args) {
    if(args.length < 1) usage();
    if(args[0].equals("-r")) {
      if(args.length != 3) usage();
      File
        old = new File(args[1]),
        rname = new File(args[2]);
      old.renameTo(rname);
      fileData(old);
      fileData(rname);
      return; // Exit main
    }
    int count = 0;
    boolean del = false;
    if(args[0].equals("-d")) {
      count++;
      del = true;
    }
    count--;
    while(++count < args.length) {
      File f = new File(args[count]);
      if(f.exists()) {
        System.out.println(f + " exists");
        if(del) {
          System.out.println("deleting..." + f);
          f.delete();
        }
      }
      else { // Doesn't exist
        if(!del) {
          f.mkdirs();
          System.out.println("created " + f);
        }
      }
      fileData(f);
    }
  }
} /* Output: (80% match)
created MakeDirectoriesTest
Absolute path: d:\aaa-TIJ4\code\io\MakeDirectoriesTest
 Can read: true
 Can write: true
 getName: MakeDirectoriesTest
 getParent: null
 getPath: MakeDirectoriesTest
 length: 0
 lastModified: 1101690308831
It's a directory
*///:~
/**
* @Description: 在fileDataO中，可以看到用到了多种不同的文件特征查询方法来显示文件或目录路径的信息。main（O方法首先调用的是renameTo（），用来把一个文件重命名（或移动）
 * 到由参数所指示的另一个完全不同的新路径（也就是另一个File对象）下面。这同样适用于任意长度的文件目录。
 *
 * 实践上面的程序可以发现，我们可以产生任意复杂的目录路径，因为mkdirsO可以为我们做好这一切。
*/
