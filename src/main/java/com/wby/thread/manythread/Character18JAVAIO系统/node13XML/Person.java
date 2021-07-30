package com.wby.thread.manythread.Character18JAVAIO系统.node13XML;//: xml/Person.java
// Use the XOM library to write and read XML
// {Requires: nu.xom.Node; You must install
// the XOM library from http://www.xom.nu }

import nu.xom.*;

import java.io.BufferedOutputStream;
import java.io.*;
import java.util.*;
/**
 *  对象序列化的一个重要限制是它只是Java的解决方案; 只有Java程序才能反序列化这种对象。一种更具互操作性的解决方案是将数据转换为XML格式，这可以使其被各种各样的平台和语言使用。
 *
 * 因为XML十分流行，所以用它来编程时的各种选择不胜枚举，包括随JDK发布的 iavax，xml.*类库。我选择使用Elliotte Rusty Harold的开源XOM类库（可从www，xom.nu下载并获得文档），因为它看起来最简单，
 * 同时也是最直观的用Java产生和修改XML的方式。另外， XOM还强调了XML的正确性。
 *
 * 作为一个示例，假设有一个Person对象，它包含姓和名，你想将它们序列化到XML中。下面的Person类有一个getXMLO方法，它使用XOM来产生被转换为XML的Element对象的Person数据;还有一个构造器，
 * 接受Element并从中抽取恰当的Person数据（注意，XML示例都在它们自己的子目录中）∶
 */
public class Person {
  private String first, last;
  public Person(String first, String last) {
    this.first = first;
    this.last = last;
  }
  // Produce an XML Element from this Person object:
  public Element getXML() {
    Element person = new Element("person");
    Element firstName = new Element("first");
    firstName.appendChild(first);
    Element lastName = new Element("last");
    lastName.appendChild(last);
    person.appendChild(firstName);
    person.appendChild(lastName);
    return person;
  }
  // Constructor to restore a Person from an XML Element:
  public Person(Element person) {
    first= person.getFirstChildElement("first").getValue();
    last = person.getFirstChildElement("last").getValue();
  }
  public String toString() { return first + " " + last; }
  // Make it human-readable:
  public static void
  format(OutputStream os, Document doc) throws Exception {
    Serializer serializer= new Serializer(os,"ISO-8859-1");
    serializer.setIndent(4);
    serializer.setMaxLength(60);
    serializer.write(doc);
    serializer.flush();
  }
  public static void main(String[] args) throws Exception {
    List<Person> people = Arrays.asList(
      new Person("Dr. Bunsen", "Honeydew"),
      new Person("Gonzo", "The Great"),
      new Person("Phillip J.", "Fry"));
    System.out.println(people);
    Element root = new Element("people");
    for(Person p : people)
      root.appendChild(p.getXML());
    Document doc = new Document(root);
    format(System.out, doc);
    format(new BufferedOutputStream(new FileOutputStream(
      "People.xml")), doc);
  }
} /* Output:
[Dr. Bunsen Honeydew, Gonzo The Great, Phillip J. Fry]
<?xml version="1.0" encoding="ISO-8859-1"?>
<people>
    <person>
        <first>Dr. Bunsen</first>
        <last>Honeydew</last>
    </person>
    <person>
        <first>Gonzo</first>
        <last>The Great</last>
    </person>
    <person>
        <first>Phillip J.</first>
        <last>Fry</last>
    </person>
</people>
*///:~
/**
* XOM的方法都具有相当的自解释性，可以在XOM文档中找到它们。XOM还包含一个 Serializer类，你可以在formatO方法中看到它被用来将XML转换为更具可读性的格式。
 * 如果只调用toXML），那么所有东西都会混在一起，因此Serializer是一种便利工具。
 *
 * 从XML文件中反序列化Person对象也很简单∶
*/
class People extends ArrayList<Person> {
  public People(String fileName) throws Exception  {
    Document doc = new Builder().build(fileName);
    Elements elements =
            doc.getRootElement().getChildElements();
    for(int i = 0; i < elements.size(); i++)
      add(new Person(elements.get(i)));
  }
  public static void main(String[] args) throws Exception {
    People p = new People("People.xml");
    System.out.println(p);
  }
} /* Output:
[Dr. Bunsen Honeydew, Gonzo The Great, Phillip J. Fry]
*///:~
/**
*  People构造器使用XOM的Builder.buildO方法打开并读取一个文件，而getChildElementsO方法产生了一个Elements列表（不是标准的Java List，只是一个拥有sizeO和getO）方法的对象，
 *  因为Harold不想强制人们使用Java SE5，但是仍旧希望使用类型安全的容器）。在这个列表中的每个Element都表示一个Person对象，因此它可以传递给第二个Person构造器。
 *  注意，这要求你提前知道XML文件的确切结构，但是这经常会有些问题。如果文件结构与你预期的结构不匹配，那么XOM将抛出异常。
 *  对你来说，如果你缺乏有关将来的XML结构的信息，那么就有可能会编写更复杂的代码去探测XML文档，而不是只对其做出假设。
 *
 *  为了获取这些示例去编译它们，你必须将XOM发布包中的JAR文件放置到你的类路径中。
 *
 *  这里只给出了用Java和XOM类库进行XML编程的简介，更详细的信息可以浏览www.xom.nu。
*/
