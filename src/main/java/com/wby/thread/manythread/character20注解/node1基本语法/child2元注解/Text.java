package com.wby.thread.manythread.character20注解.node1基本语法.child2元注解;

/**
 * Java目前只内置了三种标准注解（前面介绍过），以及四种元注解。元注解专职负责注解其他的注解∶
 * @Target
 *      表示该注解可以用于什么地方。可能的ElementType参数包括∶
 *          CONSTRUCTOR∶构造器的声明
 *          FIELD∶域声明（包括enum实例）
 *          LOCAL_VARIABLE∶局部变量声明
 *          METHOD∶方法声明
 *          PACKAGE∶包声明
 *          PARAMETER∶参数声
 *          TYPE∶类、接口（包括注解类型）或enum声明
 * @Retention
 *      表示需要在什么级别保存该注解信息。可选的RetentionPolicy参数包括∶
 *          SOURCE∶注解将被编译器丢弃。
 *          CLASS∶注解在class文件中可用，但会被VM丢弃。
 *          RUNTIME∶VM将在运行期也保留注解，因此可以通过反射机制读取注解的信息。
 * @Documented
 *      将此注解包含在Javadoc中。
 * @Inherited
 *      允许子类继承父类中的注解。
 *
 * 大多数时候，程序员主要是定义自己的注解，并编写自己的处理器来处理它们。
 */
public class Text {
}
