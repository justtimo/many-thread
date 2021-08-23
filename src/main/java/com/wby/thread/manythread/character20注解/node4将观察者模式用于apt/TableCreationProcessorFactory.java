//: annotations/database/TableCreationProcessorFactory.java
// The database example using Visitor.
// {Exec: apt -factory
// annotations.database.TableCreationProcessorFactory
// database/Member.java -s database}
package com.wby.thread.manythread.character20注解.node4将观察者模式用于apt;

/**
 * 上面的例子是一个相当简单的注解处理器，只需对一个注解进行分析，但我们仍然要做大量复杂的工作。因此，处理注解的真实过程可能会非常复杂。当我们有更多的注解和更多的处理器时，
 * 为了防止这种复杂性迅速攀升，mirror API提供了对访问者设计模式的支持。访问者是Gamma等人所著的《设计模式》e一书中的经典设计模式之一。你也可以在《Thinking in Patterns》中找到更详细的解释。
 *
 * 一个访问者会遍历某个数据结构或一个对象的集合，对其中的每一个对象执行一个操作。该数据结构无需有序，而你对每个对象执行的操作，都是特定于此对象的类型。这就将操作与对象解耦，也就是说，你可以添加新的操作，而无需向类的定义中添加方法。
 *
 * 这个技巧在处理注解时非常有用，因为一个Java类可以看作是一系列对象的集合，例如 TypeDeclaration对象、FieldDeclaration对象以及MethodDeclaration对象等。当你配合访问者模式使用apt工具时，需要提供一个Visitor类，
 * 它具有一个能够处理你要访问的各种声明的方法。然后，你就可以为方法、类以及域上的注解实现相应的处理行为。
 *
 * 下面仍然是SQL表生成器的例子，不过这次我们使用访问者模式来创建工厂和注解处理器∶
 */
/*public class TableCreationProcessorFactory
  implements AnnotationProcessorFactory {
  public AnnotationProcessor getProcessorFor(
    Set<AnnotationTypeDeclaration> atds,
    AnnotationProcessorEnvironment env) {
    return new TableCreationProcessor(env);
  }
  public Collection<String> supportedAnnotationTypes() {
    return Arrays.asList(
      "annotations.database.DBTable",
      "annotations.database.Constraints",
      "annotations.database.SQLString",
      "annotations.database.SQLInteger");
  }
  public Collection<String> supportedOptions() {
    return Collections.emptySet();
  }
  private static class TableCreationProcessor
    implements AnnotationProcessor {
    private final AnnotationProcessorEnvironment env;
    private String sql = "";
    public TableCreationProcessor(
      AnnotationProcessorEnvironment env) {
      this.env = env;
    }
    public void process() {
      for(TypeDeclaration typeDecl :
        env.getSpecifiedTypeDeclarations()) {
        typeDecl.accept(getDeclarationScanner(
          new TableCreationVisitor(), NO_OP));
        sql = sql.substring(0, sql.length() - 1) + ");";
        System.out.println("creation SQL is :\n" + sql);
        sql = "";
      }
    }
    private class TableCreationVisitor
      extends SimpleDeclarationVisitor {
      public void visitClassDeclaration(
        ClassDeclaration d) {
        DBTable dbTable = d.getAnnotation(DBTable.class);
        if(dbTable != null) {
          sql += "CREATE TABLE ";
          sql += (dbTable.name().length() < 1)
            ? d.getSimpleName().toUpperCase()
            : dbTable.name();
          sql += " (";
        }
      }
      public void visitFieldDeclaration(
        FieldDeclaration d) {
        String columnName = "";
        if(d.getAnnotation(SQLInteger.class) != null) {
          SQLInteger sInt = d.getAnnotation(
              SQLInteger.class);
          // Use field name if name not specified
          if(sInt.name().length() < 1)
            columnName = d.getSimpleName().toUpperCase();
          else
            columnName = sInt.name();
          sql += "\n    " + columnName + " INT" +
            getConstraints(sInt.constraints()) + ",";
        }
        if(d.getAnnotation(SQLString.class) != null) {
          SQLString sString = d.getAnnotation(
              SQLString.class);
          // Use field name if name not specified.
          if(sString.name().length() < 1)
            columnName = d.getSimpleName().toUpperCase();
          else
            columnName = sString.name();
          sql += "\n    " + columnName + " VARCHAR(" +
            sString.value() + ")" +
            getConstraints(sString.constraints()) + ",";
        }
      }
      private String getConstraints(Constraints con) {
        String constraints = "";
        if(!con.allowNull())
          constraints += " NOT NULL";
        if(con.primaryKey())
          constraints += " PRIMARY KEY";
        if(con.unique())
          constraints += " UNIQUE";
        return constraints;
      }
    }
  }
}*/ ///:~
/**
 * 这个程序输出的结果与前一个DBTable的例子完全相同。
 *
 * 在这个例子中，处理器与访问者都是内部类。注意，processO方法所做的只是添加了一个访问者类，并初始化了SQL字符串。
 *
 * getDeclarationScannerO方法的两个参数都是访问者;第一个是在访问每个声明前使用，第二个则是在访问之后使用。由于这个处理器只需要在访问前使用的访问者，
 * 所以第二个参数给的是 NO_OP。NO_OP是DeclarationVisitor接口中的static域，是一个什么也不做的Declaration-Visitor。TableCreationVisitor继承自SimpleDeclarationVisitor，
 * 它覆写了两个方法visitClase- Declaration（）和visitFieldDeclaration（）。SimpleDeclarationVisitor是一个适配器，实现了 DeclarationVisitor接口中的所有方法，
 * 因此，程序员只需将注意力放在自己需要的那些方法上。在visitClaseDeclarationO方法中，检查ClassDeclaration对象是否带有DBTable注解，如果存在的话，将初始化SQL语句的第一部分。
 * 在visitFieldDeclarationO方法中，将检查域声明上的注解，从域声明中提取信息的过程与本章前面的例子一样。
 *
 * 看起来这个例子使用的方式似乎更复杂，但是它确实是一种具备扩展能力的解决方案。当你的注解处理器的复杂性越来越高的时候，如果还按前面例子中的方式编写自己独立的处理器，那么很快你的处理器就将变得非常复杂。
 *
 * 练习3∶（2） 向TableCreationProcessorFactory.java中添加对更多的SQL类型的支持。
 */
