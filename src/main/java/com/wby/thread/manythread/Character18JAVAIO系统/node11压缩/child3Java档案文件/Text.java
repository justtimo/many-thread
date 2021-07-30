package com.wby.thread.manythread.Character18JAVAIO系统.node11压缩.child3Java档案文件;

/**
 * @Auther: LangWeiXian
 * @Date: 2021/7/30 16:32
 * @Description: Zip格式也被应用于JAR（Java ARchive， Java档案文件）文件格式中。这种文件格式就像Zip一样，可以将一组文件压缩到单个压缩文件中。
 * 同Java中其他任何东西一样，JAR文件也是跨平台的，所以不必担心跨平台的问题。声音和图像文件可以像类文件一样被包含在其中。
 *
 * JAR文件非常有用，尤其是在涉及因特网应用的时候。如果不采用JAR文件，Web浏览器在下载构成一个应用的所有文件时必须重复多次请求Web服务器，而且所有这些文件都是未经压缩的。
 * 如果将所有这些文件合并到一个JAR文件中，只需向远程服务器发出一次请求即可。同时，由于采用了压缩技术，可以使传输时间更短。另外，出于安全的考虑，JAR文件中的每个条目都可以加上数字化签名。
 *
 * 一个JAR文件由一组压缩文件构成，同时还有一张描述了所有这些文件的"文件清单"（可自行创建文件清单，也可以由jar程序自动生成）。在JDK文档中，可以找到与JAR文件清单相关的更多资料。
 * Sun的JDK自带的jar程序可根据我们的选择自动压缩文件。可以用命令行的形式调用它，如下所示∶
 *      jar [options] destination [manifest] inputfile(s)
 * 其中options只是一个字母集合（不必输入任何"-"或其他任何标识符）。以下这些选项字符在 Unix和Linux系统中的tar文件中也具有相同的意义。具体意义如下所示∶
 *
 *
 * 如果压缩到JAR文件的众多文件中包含某个子目录，那么该子目录会被自动添加到JAR文件中，且包括该子目录的所有子目录，路径信息也会被保留。
 * 以下是一些调用jar的典型方法。下面的命令创建了一个名为myJarFile-jar的JAR文件，该文件包含了当前目录中的所有类文件，以及自动产生的清单文件∶
 *              jar cf myJarFile.jar *.class
 * 下面的命令与前例类似，但添加了一个名为myManiestFile.mf的用户自建清单文件∶
 *              jar cmf myJarFile.jar myNanifestF1ile.mf*.class下面的命令会产生myJarFile.jar内所有文件的一个目录表∶
 *              jar tf my JarFile.jar
 * 下面的命令添加"v"（详尽）标志，可以提供有关myJarFilejar中的文件的更详细的信息;
 *              jar tvf myJarFile.jar
 * 假定audio、classes和image是子目录，下面的命令将所有子目录合并到文件myApp.jar中，其中也包括了"v"标志。当jar程序运行时，该标志可以提供更详细的信息∶
 *              jar cvf myApp.jar audio classes image
 * 如果用0（零）选项创建一个JAR文件，那么该文件就可放入类路径变量（CLASSPATH）中∶ CLASSPATH="1ib1.jar;lib2.jar;"
 * 然后Java就可以在lib1.jar和lib2.jar中搜索目标类文件了。
 *
 * jar工具的功能没有zip工具那么强大。例如，不能够对已有的JAR文件进行添加或更新文件的操作，只能从头创建一个JAR文件。同时，也不能将文件移动至一个JAR文件，并在移动后将它们删除。
 * 然而，在一种平台上创建的JAR文件可以被在其他任何平台上的jar工具透明地阅读（这个问题有时会困扰zip工具）。
 *
 * 读者将会在第22章看到，JAR文件也被用来为JavaBeans打包。
 */
public class Text {
}
