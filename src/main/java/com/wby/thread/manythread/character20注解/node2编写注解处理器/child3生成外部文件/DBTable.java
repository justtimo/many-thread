//: annotations/database/DBTable.java
package com.wby.thread.manythread.character20注解.node2编写注解处理器.child3生成外部文件;
import java.lang.annotation.*;

/**
 * 有些framework需要一些额外的信息才能与你的源代码协同工作，而这种情况最适合注解表现其价值了。像（EJB3之前）Enterprise JavaBean这样的技术，每一个Bean都需要大量的接口和部署来描述文件，
 * 而这些都属于"样板"文件。Web Service、自定义标签库以及对象/关系映射工具（例如Toplink和Hibernate）等，一般都需要XML描述文件，而这些描述文件脱离于源代码之外。
 * 因此，在定义了Java类之后，程序员还必须得忍受着沉闷，重复地提供某些信息，例如类名和包名等已经在原始的类文件中提供了的信息。每当程序员使用外部的描述文件时，他就拥有了同一个类的两个单独的信息源，
 * 这经常导致代码同步问题。同时，它也要求为项目工作的程序员，必须同时知道如何编写Java程序，以及如何编辑描述文件。
 *
 * 假设你希望提供一些基本的对象/关系映射功能，能够自动生成数据库表，用以存储 JavaBean对象。你可以选择使用XML描述文件，指明类的名字、每个成员以及数据库映射的相关信息。
 * 然而，如果使用注解的话，你可以将所有信息都保存在JavaBean源文件中。为此，我们需要一些新的注解，用以定义与Bean关联的数据库表的名字，以及与Bean属性关联的列的名字和SQL类型。
 *
 * 以下是一个注解的定义，它告诉注解处理器，你需要为我生成一个数据库表∶
 */
@Target(ElementType.TYPE) // Applies to classes only
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {
  public String name() default "";
} ///:~
/**
 * 在@Target注解中指定的每一个ElementType就是一个约束，它告诉编译器，这个自定义的注解只能应用于该类型。程序员可以只指定enum ElementType中的某一个值，
 * 或者以逗号分隔的形式指定多个值。如果想要将注解应用于所有的ElementType，那么可以省去@Target元注解，不过这并不常见。
 *
 * 注意，@DBTable有一个name（元素，该注解通过这个元素为处理器创建数据库表提供表的名字。
 * 接下来是为修饰JavaBean域准备的注解∶
 *    见Constraints  SQLString    SQLInteger
 */


/**
 * 注解处理器通过@Constraints注解提取出数据库表的元数据。虽然对于数据库所能提供的所有约束而言，@Constraints注解只表示了它的一个很小的子集，不过它所要表达的思想已经很清楚了。
 * primaryKey（）、allowNullO和unique（）元素明智地提供了默认值，从而在大多数情况下，使用该注解的程序员无需输入太多东西。
 *
 * 另外两个@interface定义的是SQL类型。如果希望这个framework更有价值的话，我们就应该为每种SQL类型都定义相应的注解。不过作为示例，两个类型足够了。
 *
 * 这些SQL类型具有name（O元素和constraintsO元素。后者利用了嵌套注解的功能，将column类型的数据库约束信息嵌入其中。注意constraints（）元素的默认值是@Constraints。
 * 由于在@Constraints注解类型之后，没有在括号中指明@Constraints中的元素的值，因此， constraints（）元素的默认值实际上就是一个所有元素都为默认值的@Constraits注解。
 * 如果要令嵌入的@Constraints注解中的uniqueO元素为true，并以此作为constraintsO元素的默认值，则需要如下定义该元素∶
 */
@interface Uniqueness {
  Constraints constraints()
          default @Constraints(unique=true);
} ///:~
/**
 * 下面是一个简单的Bean定义，我们在其中应用了以上这些注解∶
 */
@DBTable(name = "MEMBER")
class Member {
  @SQLString(30) String firstName;
  @SQLString(50) String lastName;
  @SQLInteger Integer age;
  @SQLString(value = 30,
          constraints = @Constraints(primaryKey = true))
  String handle;
  static int memberCount;
  public String getHandle() { return handle; }
  public String getFirstName() { return firstName; }
  public String getLastName() { return lastName; }
  public String toString() { return handle; }
  public Integer getAge() { return age; }
} ///:~
/**
 * 类的注解@DBTable给定了值MEMBER，它将会用来作为表的名字。Bean的属性firstName和lastName，都被注解为@SOLString类型，并且其元素值分别为30和50。
 * 这些注解有两个有趣的地方∶第一，他们都使用了嵌入的@Constraints注解的默认值;第二，它们都使用了快捷方式。何谓快捷方式呢，如果程序员的注解中定义了名为value的元素，并且在应用该注解的时候，
 * 如果该元素是唯一需要赋值的一个元素，那么此时无需使用名-值对的这种语法，而只需在括号内给出value元素所需的值即可。这可以应用于任何合法类型的元素。
 * 当然了，这也限制了程序员必须将此元素命名为value，不过在上面的例子中，这不但使语义更清晰，而且这样的注解语句也更易于理解∶
 *    @SQLString(30
 *
 * 处理器将在创建表的时候使用该值设置SQL列的大小。
 *
 * 默认值的语法虽然很灵巧，但它很快就变得复杂起来。以handle域的注解为例，这是一个@SQLString注解，同时该域将成为表的主键，因此在嵌入的@Constraints注解中，必须对 primaryKey元素进行设定。
 * 这时事情就变得麻烦了。现在，你不得不使用很长的名-值对形式，重新写出元素名和@interface的名字。与此同时，由于有特殊命名的value元素已经不再是唯一需要赋值的元素了，
 * 所以你也不能再使用快捷方式为其赋值了。如你所见，最终的结果算不上清晰易懂。
 */
/**
 * 变通之道
 * 可以使用多种不同的方式来定义自己的注解，以实现上例中的功能。例如，你可以使用一个单一的注解类@TableColumn，它带有一个enum元素，该枚举类定义了STRING、INTEGER以及FLOAT等枚举实例。
 * 这就消除了每个SQL类型都需要一个@interface定义的负担，不过也使得以额外的信息修饰SQL类型的需求变得不可能，而这些额外的信息，例如长度或精度等，可能是非常有必要的需求。
 *
 * 我们也可以使用String元素来描述实际的SQL类型，比如VARCHAR（30）或INTEGER。这使得程序员可以修饰SQL类型。但是，它同时也将Java类型到SQL类型的映射绑在了一起，这可不是一个好的设计。
 * 我们可不希望更换数据库导致代码必须修改并重新编译。如果我们只需告诉注解处理器，我们正在使用的是什么"口味"的SQL，然后由处理器为我们处理SQL类型的细节，那将是一个优雅的设计。
 *
 * 第三种可行的方案是同时使用两个注解类型来注解一个域，@Constraints和相应的SQL类型（例如@SQLIntege）。这种方式可能会使代码有点乱，不过编译器允许程序员对一个目标同时使用多个注解。
 * 注意，使用多个注解的时候，同一个注解不能重复使用。
 */








