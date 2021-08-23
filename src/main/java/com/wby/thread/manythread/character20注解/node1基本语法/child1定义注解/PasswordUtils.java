package com.wby.thread.manythread.character20注解.node1基本语法.child1定义注解;

import java.util.List;

/**
 * 注意，id和description类似方法定义。由于编译器会对id进行类型检查，因此将用例文档的追踪数据库与源代码相关联是可靠的。description元素有一个default值，
 * 如果在注解某个方法时没有给出description的值，则该注解的处理器就会使用此元素的默认值。
 *
 * 在下面的类中，有三个方法被注解为用例∶
 */
public class PasswordUtils {
    @UseCase(id = 47, description =
            "Passwords must contain at least one numeric")
    public boolean validatePassword(String password) {
        return (password.matches("\\w*\\d\\w*"));
    }
    @UseCase(id = 48)
    public String encryptPassword(String password) {
        return new StringBuilder(password).reverse().toString();
    }
    @UseCase(id = 49, description =
            "New passwords can't equal previously used ones")
    public boolean checkForNewPassword(
            List<String> prevPasswords, String password) {
        return !prevPasswords.contains(password);
    }
} ///:~
/**
 * 注解的元素在使用时表现为名-值对的形式，并需要置于@UseCase声明之后的括号内。在 encryptPassword（）方法的注解中，并没有给出description元素的值，因此，在UseCase的注解处理器分析处理这个类时会使用该元素的默认值。
 * 你应该能够想象得到如何使用这套工具来"勾勒"出将要建造的系统，然后在建造的过程中逐渐实现系统的各项功能。
 */
