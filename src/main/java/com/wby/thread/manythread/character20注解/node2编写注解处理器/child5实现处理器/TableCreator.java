//: annotations/database/TableCreator.java
// Reflection-based annotation processor.
// {Args: annotations.database.Member}
package com.wby.thread.manythread.character20注解.node2编写注解处理器.child5实现处理器;

import com.wby.thread.manythread.character20注解.node2编写注解处理器.child3生成外部文件.Constraints;
import com.wby.thread.manythread.character20注解.node2编写注解处理器.child3生成外部文件.DBTable;
import com.wby.thread.manythread.character20注解.node2编写注解处理器.child3生成外部文件.SQLInteger;
import com.wby.thread.manythread.character20注解.node2编写注解处理器.child3生成外部文件.SQLString;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 下面是一个注解处理器的例子。它将读取—个类文件。检查其上的数据库注解。并生成用来创建数据库的SQL命令∶
 */
public class TableCreator {
  public static void main(String[] args) throws Exception {
    if(args.length < 1) {
      System.out.println("arguments: annotated classes");
      System.exit(0);
    }
    for(String className : args) {
      Class<?> cl = Class.forName(className);
      DBTable dbTable = cl.getAnnotation(DBTable.class);
      if(dbTable == null) {
        System.out.println(
          "No DBTable annotations in class " + className);
        continue;
      }
      String tableName = dbTable.name();
      // If the name is empty, use the Class name:
      if(tableName.length() < 1)
        tableName = cl.getName().toUpperCase();
      List<String> columnDefs = new ArrayList<String>();
      for(Field field : cl.getDeclaredFields()) {
        String columnName = null;
        Annotation[] anns = field.getDeclaredAnnotations();
        if(anns.length < 1)
          continue; // Not a db table column
        if(anns[0] instanceof SQLInteger) {
          SQLInteger sInt = (SQLInteger) anns[0];
          // Use field name if name not specified
          if(sInt.name().length() < 1)
            columnName = field.getName().toUpperCase();
          else
            columnName = sInt.name();
          columnDefs.add(columnName + " INT" +
            getConstraints(sInt.constraints()));
        }
        if(anns[0] instanceof SQLString) {
          SQLString sString = (SQLString) anns[0];
          // Use field name if name not specified.
          if(sString.name().length() < 1)
            columnName = field.getName().toUpperCase();
          else
            columnName = sString.name();
          columnDefs.add(columnName + " VARCHAR(" +
            sString.value() + ")" +
            getConstraints(sString.constraints()));
        }
        StringBuilder createCommand = new StringBuilder(
          "CREATE TABLE " + tableName + "(");
        for(String columnDef : columnDefs)
          createCommand.append("\n    " + columnDef + ",");
        // Remove trailing comma
        String tableCreate = createCommand.substring(
          0, createCommand.length() - 1) + ");";
        System.out.println("Table Creation SQL for " +
          className + " is :\n" + tableCreate);
      }
    }
  }
  private static String getConstraints(Constraints con) {
    String constraints = "";
    if(!con.allowNull())
      constraints += " NOT NULL";
    if(con.primaryKey())
      constraints += " PRIMARY KEY";
    if(con.unique())
      constraints += " UNIQUE";
    return constraints;
  }
} /* Output:
Table Creation SQL for annotations.database.Member is :
CREATE TABLE MEMBER(
    FIRSTNAME VARCHAR(30));
Table Creation SQL for annotations.database.Member is :
CREATE TABLE MEMBER(
    FIRSTNAME VARCHAR(30),
    LASTNAME VARCHAR(50));
Table Creation SQL for annotations.database.Member is :
CREATE TABLE MEMBER(
    FIRSTNAME VARCHAR(30),
    LASTNAME VARCHAR(50),
    AGE INT);
Table Creation SQL for annotations.database.Member is :
CREATE TABLE MEMBER(
    FIRSTNAME VARCHAR(30),
    LASTNAME VARCHAR(50),
    AGE INT,
    HANDLE VARCHAR(30) PRIMARY KEY);
*///:~
/**
 * main（）方法会处理命令行传入的每一个类名。使用forName（）方法加载每一个类，并使用 getAnnotation（DBTable.class）检查该类是否带有@DBTable注解。
 * 如果有，就将发现的表名保存下来。然后读取这个类的所有域。并用getDecaredAnnotationO进行检香。该方法返回—个句含一个域上的所有注解的数组。
 * 最后用instanceof操作符来判断这些注解是否是@SQLIntege或@SQLString类型，如果是的话，在对应的处理块中将构造出相应cloumn名的字符串片断。
 * 注意，由于注解没有继承机制，所以要获得近似多态的行为，使用getDeclaredAnnotationO是唯一的办法。
 *
 * 嵌套中的@Constraint注解被传递给getConstraints（方法，由它负责构造一个包含SOL约束的String对象。
 *
 * 需要提醒读者的是，上面演示的技巧对于真实的对象/关系映射而言，是很幼稚的。例如使用@DBTable类型的注解，程序员以参数的形式给出表的名字，如果程序员想要修改表的名字，
 * 这将迫使其必须重新编译Java代码。这可不是我们希望看到的结果。现在已经有了很多可用的 framework，可以将对象映射到关系数据库，并且，其中越来越多的framework已经开始利用注解了。
 */
/**
 * 练习1∶（2）为本节数据库的例子实现更多的SQL类型。
 * 作业9;修改数据库的例子，使其能够使用JDBC连接到一个真正的数据库，并与之交互。
 * 作业∶修改数据库的例子，令其生成XML构造文件，而不是SQL语句。
 */
