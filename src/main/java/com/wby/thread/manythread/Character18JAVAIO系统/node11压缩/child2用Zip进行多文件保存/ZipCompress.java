package com.wby.thread.manythread.Character18JAVAIO系统.node11压缩.child2用Zip进行多文件保存;//: io/ZipCompress.java
// Uses Zip compression to compress any
// number of files given on the command line.
// {Args: ZipCompress.java}

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
/**
 * 支持 Zip格式的Java 库更加全面。利用该库可以方便地保存多个文件，它甚至有一个独立的类，使得读取Zip文件更加方便。这个类库使用的是标准Zip格式，所以能与当前那些可通过因特网下载的压缩工具很好地协作。
 *
 * 下面这个例子具有与前例相同的形式，但它能根据需要来处理任意多个命令行参数。
 * 另外，它显示了用Checksum类来计算和校验文件的校验和的方法。
 * 一共有两种Checksum类型∶Adler32（它快一些）和CRC32（慢一些，但更准确）。
 */
public class ZipCompress {
  public static void main(String[] args)
  throws IOException {
    FileOutputStream f = new FileOutputStream("test.zip");
    CheckedOutputStream csum =
      new CheckedOutputStream(f, new Adler32());
     ZipOutputStream zos = new ZipOutputStream(csum);
     BufferedOutputStream out =
      new BufferedOutputStream(zos);
    zos.setComment("A test of Java Zipping");
    // No corresponding getComment(), though.
    for(String arg : args) {
      print("Writing file " + arg);
      BufferedReader in =
        new BufferedReader(new FileReader(arg));
      zos.putNextEntry(new ZipEntry(arg));
      int c;
      while((c = in.read()) != -1)
        out.write(c);
      in.close();
      out.flush();
    }
    out.close();
    // Checksum valid only after the file has been closed!
    print("Checksum: " + csum.getChecksum().getValue());
    // Now extract the files:
    print("Reading file");
    FileInputStream fi = new FileInputStream("test.zip");
    CheckedInputStream csumi =
      new CheckedInputStream(fi, new Adler32());
    ZipInputStream in2 = new ZipInputStream(csumi);
    BufferedInputStream bis = new BufferedInputStream(in2);
    ZipEntry ze;
    while((ze = in2.getNextEntry()) != null) {
      print("Reading file " + ze);
      int x;
      while((x = bis.read()) != -1)
        System.out.write(x);
    }
    if(args.length == 1)
    print("Checksum: " + csumi.getChecksum().getValue());
    bis.close();
    // Alternative way to open and read Zip files:
    ZipFile zf = new ZipFile("test.zip");
    Enumeration e = zf.entries();
    while(e.hasMoreElements()) {
      ZipEntry ze2 = (ZipEntry)e.nextElement();
      print("File: " + ze2);
      // ... and extract the data as before
    }
    /* if(args.length == 1) */
  }
} /* (Execute to see output) *///:~
/**
*  对于每一个要加入压缩档案的文件，都必须调用putNextEntry（），并将其传递给一个 ZipEntry对象。ZipEntry对象包含了一个功能很广泛的接口，允许你获取和设置Zip文件内该特定项上所有可利用的数据∶
 *  名字、压缩的和未压缩的文件大小、日期、CRC校验和、额外字段数据、注释、压缩方法以及它是否是一个目录入口等等。
 *
 *  然而，尽管Zip格式提供了设置密码的方法，但Java的Zip类库并不提供这方面的支持。虽然CheckedInputStream和CheckedOutputStream都支持Adler32和CRC32两种类型的校验和，
 *  但是ZipEntry类只有一个支持CRC的接口。虽然这是一个底层Zip格式的限制，但却限制了人们不能使用速度更快的Adler32。
 *
 * 为了能够解压缩文件，ZipInputStream提供了一个getNextEntry（O方法返回下一个ZipEntry（如果存在的话）。解压缩文件有一个更简便的方法——利用ZipFile对象读取文件。
 * 该对象有一个entriesO方法用来向ZipEntries返回一个Enumeration（枚举）。
 *
 * 为了读取校验和，必须拥有对与之相关联的Checksum对象的访问权限。在这里保留了指向 CheckedOutputStream和CheckedInputStream对象的引用。但是，也可以只保留一个指向 Checksum对象的引用。
 *
 * Zip流中有一个令人困惑的方法setCommentO。正如前面ZipCompress.java中所示，我们可以在写文件时写注释，但却没有任何方法恢复ZipInputStream内的注释。
 * 似乎只能通过ZipEntry，才能以逐条方式完全支持注释的获取。
 *
 * 当然，GZIP或Zip库的使用并不仅仅局限于文件——它可以压缩任何东西，包括需要通过网络发送的数据。
*/
