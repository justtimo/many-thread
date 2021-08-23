//: net/mindview/atunit/TestObjectCreate.java
// The @Unit @TestObjectCreate tag.
package com.wby.thread.manythread.character20注解.node5基于注解的单元测试.child3实现Unit;

import com.wby.thread.manythread.net.mindview.atunit.Test;
import com.wby.thread.manythread.net.mindview.atunit.TestObjectCleanup;
import com.wby.thread.manythread.net.mindview.util.BinaryFile;
import com.wby.thread.manythread.net.mindview.util.Directory;
import com.wby.thread.manythread.net.mindview.util.ProcessFiles;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wby.thread.manythread.net.mindview.util.Print.print;
import static com.wby.thread.manythread.net.mindview.util.Print.printnb;

/**
 * 首先，我们需要定义所有的注解类型。这些都是简单的标签，并且没有属性。@Test标签在本章开头已经定义过了，这里是其他所需的注解∶
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestObjectCreate {} ///:~
/**
 * 所有测试的保留属性必须是RUNTIME，因为@Unit系统必须在编译后的代码中查询这些注解。
 *
 * 要实现该系统，并运行测试，我们还需使用反射机制来抽取注解。下面这个程序通过注解中的信息，决定如何构造测试对象，并在测试对象上运行测试。
 * 正是由于注解的帮助，这个程序才如此短小而直接∶
 */
class AtUnit implements ProcessFiles.Strategy {
    static Class<?> testClass;
    static List<String> failedTests= new ArrayList<String>();
    static long testsRun = 0;
    static long failures = 0;
    public static void main(String[] args) throws Exception {
        ClassLoader.getSystemClassLoader()
                .setDefaultAssertionStatus(true); // Enable asserts
        new ProcessFiles(new AtUnit(), "class").start(args);
        if(failures == 0)
            print("OK (" + testsRun + " tests)");
        else {
            print("(" + testsRun + " tests)");
            print("\n>>> " + failures + " FAILURE" +
                    (failures > 1 ? "S" : "") + " <<<");
            for(String failed : failedTests)
                print("  " + failed);
        }
    }
    public void process(File cFile) {
        try {
            String cName = ClassNameFinder.thisClass(
                    BinaryFile.read(cFile));
            if(!cName.contains("."))
                return; // Ignore unpackaged classes
            testClass = Class.forName(cName);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
        TestMethods testMethods = new TestMethods();
        Method creator = null;
        Method cleanup = null;
        for(Method m : testClass.getDeclaredMethods()) {
            testMethods.addIfTestMethod(m);
            if(creator == null)
                creator = checkForCreatorMethod(m);
            if(cleanup == null)
                cleanup = checkForCleanupMethod(m);
        }
        if(testMethods.size() > 0) {
            if(creator == null)
                try {
                    if(!Modifier.isPublic(testClass
                            .getDeclaredConstructor().getModifiers())) {
                        print("Error: " + testClass +
                                " default constructor must be public");
                        System.exit(1);
                    }
                } catch(NoSuchMethodException e) {
                    // Synthesized default constructor; OK
                }
            print(testClass.getName());
        }
        for(Method m : testMethods) {
            printnb("  . " + m.getName() + " ");
            try {
                Object testObject = createTestObject(creator);
                boolean success = false;
                try {
                    if(m.getReturnType().equals(boolean.class))
                        success = (Boolean)m.invoke(testObject);
                    else {
                        m.invoke(testObject);
                        success = true; // If no assert fails
                    }
                } catch(InvocationTargetException e) {
                    // Actual exception is inside e:
                    print(e.getCause());
                }
                print(success ? "" : "(failed)");
                testsRun++;
                if(!success) {
                    failures++;
                    failedTests.add(testClass.getName() +
                            ": " + m.getName());
                }
                if(cleanup != null)
                    cleanup.invoke(testObject, testObject);
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    static class TestMethods extends ArrayList<Method> {
        void addIfTestMethod(Method m) {
            if(m.getAnnotation(Test.class) == null)
                return;
            if(!(m.getReturnType().equals(boolean.class) ||
                    m.getReturnType().equals(void.class)))
                throw new RuntimeException("@Test method" +
                        " must return boolean or void");
            m.setAccessible(true); // In case it's private, etc.
            add(m);
        }
    }
    private static Method checkForCreatorMethod(Method m) {
        if(m.getAnnotation(TestObjectCreate.class) == null)
            return null;
        if(!m.getReturnType().equals(testClass))
            throw new RuntimeException("@TestObjectCreate " +
                    "must return instance of Class to be tested");
        if((m.getModifiers() &
                java.lang.reflect.Modifier.STATIC) < 1)
            throw new RuntimeException("@TestObjectCreate " +
                    "must be static.");
        m.setAccessible(true);
        return m;
    }
    private static Method checkForCleanupMethod(Method m) {
        if(m.getAnnotation(TestObjectCleanup.class) == null)
            return null;
        if(!m.getReturnType().equals(void.class))
            throw new RuntimeException("@TestObjectCleanup " +
                    "must return void");
        if((m.getModifiers() &
                java.lang.reflect.Modifier.STATIC) < 1)
            throw new RuntimeException("@TestObjectCleanup " +
                    "must be static.");
        if(m.getParameterTypes().length == 0 ||
                m.getParameterTypes()[0] != testClass)
            throw new RuntimeException("@TestObjectCleanup " +
                    "must take an argument of the tested type.");
        m.setAccessible(true);
        return m;
    }
    private static Object createTestObject(Method creator) {
        if(creator != null) {
            try {
                return creator.invoke(testClass);
            } catch(Exception e) {
                throw new RuntimeException("Couldn't run " +
                        "@TestObject (creator) method.");
            }
        } else { // Use the default constructor:
            try {
                return testClass.newInstance();
            } catch(Exception e) {
                throw new RuntimeException("Couldn't create a " +
                        "test object. Try using a @TestObject method.");
            }
        }
    }
} ///:~
/**
 * AtUnit.，java使用了net.mindview.util中的ProcessFiles工具。这个类还实现了ProcessFiles Strategy接口，该接口包含processO方法。如此一来，
 * 便可以将一个AtUnit实例传给ProcessFiles的构造器。ProcessFiles构造器的第二个参数告诉ProcessFiles查找所有扩展名为class的文件。
 *
 * 如果你没有提供命令行参数，这个程序会遍历当前目录。你也可以为其提供多个参数，可以是类文件（带有或不带.class扩展名都可），或者是一些目录。由于@Unit将会自动找到可测试的类和方法，所以没有"套件"机制的必要。
 *
 * AtUnitjava必须要解决一个问题，就是当它找到类文件时，实际引用的类名 （含有包）并非一定就是类文件的名字。为了从中解读信息，我们必须分析该类文件，这很重要，因为这种名字不一致的情况确实可能出现9。
 * 所以，当找到一个.class文件时，第一件事情就是打开该文文件，读取其二进制数据，然后将其交给ClassNameFinder.thisClass（）。
 * 从这里开始，我们将进入"字节码工程"的领域，因为我们实际上是在分析一个类文件的内容∶
 */
class ClassNameFinder {
    public static String thisClass(byte[] classBytes) {
        Map<Integer,Integer> offsetTable =
                new HashMap<Integer,Integer>();
        Map<Integer,String> classNameTable =
                new HashMap<Integer,String>();
        try {
            DataInputStream data = new DataInputStream(
                    new ByteArrayInputStream(classBytes));
            int magic = data.readInt();  // 0xcafebabe
            int minorVersion = data.readShort();
            int majorVersion = data.readShort();
            int constant_pool_count = data.readShort();
            int[] constant_pool = new int[constant_pool_count];
            for(int i = 1; i < constant_pool_count; i++) {
                int tag = data.read();
                int tableSize;
                switch(tag) {
                    case 1: // UTF
                        int length = data.readShort();
                        char[] bytes = new char[length];
                        for(int k = 0; k < bytes.length; k++)
                            bytes[k] = (char)data.read();
                        String className = new String(bytes);
                        classNameTable.put(i, className);
                        break;
                    case 5: // LONG
                    case 6: // DOUBLE
                        data.readLong(); // discard 8 bytes
                        i++; // Special skip necessary
                        break;
                    case 7: // CLASS
                        int offset = data.readShort();
                        offsetTable.put(i, offset);
                        break;
                    case 8: // STRING
                        data.readShort(); // discard 2 bytes
                        break;
                    case 3:  // INTEGER
                    case 4:  // FLOAT
                    case 9:  // FIELD_REF
                    case 10: // METHOD_REF
                    case 11: // INTERFACE_METHOD_REF
                    case 12: // NAME_AND_TYPE
                        data.readInt(); // discard 4 bytes;
                        break;
                    default:
                        throw new RuntimeException("Bad tag " + tag);
                }
            }
            short access_flags = data.readShort();
            int this_class = data.readShort();
            int super_class = data.readShort();
            return classNameTable.get(
                    offsetTable.get(this_class)).replace('/', '.');
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    // Demonstration:
    public static void main(String[] args) throws Exception {
        if(args.length > 0) {
            for(String arg : args)
                print(thisClass(BinaryFile.read(new File(arg))));
        } else
            // Walk the entire tree:
            for(File klass : Directory.walk(".", ".*\\.class"))
                print(thisClass(BinaryFile.read(klass)));
    }
} ///:~
/**
 * 虽然无法在这里介绍其中所有的细节，但每个类文件都必须遵循一定的格式，而我已经尽量用有意义的域名字来表示这些从ByteArrayInputStream中提出取来的数据片断。
 * 通过施加在输入流上的读操作，你能看出每个信息片的大小。例如，每个类文件的头32个bit总是一个"神秘的数字"hex0xcafebabe9，而接下来的两个short值是版本信息。常量池包含了程序中的常量，所以这是一个可变的值。
 * 接下来的short告诉我们这个常量池有多大，然后我们为其创建一个尺寸合适的数组。常量池中的每一个元素，其长度可能是一个固定的值，也可能是可变的值，因此我们必须检查每一个常量起始的标记，然后才能知道该怎么做，
 * 这就是switch语句中的工作。我们并不打算精确地分析类中的所有数据，仅仅是从文件的起始一步一步地走，直到取得我们所需的信息，因此你会发现，在这个过程中我们丢弃了大量的数据。
 * 关干类的信 息都保存在 classNameTable和offsetTable中。在读完了常量池之后，就找到了this_class信息，这是 offsetTable中的一个坐标，通过它能够找到一个进入classNameTable的坐标，
 * 然后就可以得到我们所需的类的名字了。
 *
 * 现在，让我们回到AtUnitjava程序，process（）方法现在拥有了类的名字，然后检查它是否包含"。"，如果有就表示该类定义于一个包中。没有包的类将被忽略。
 * 如果一个类在包中，那么我们就可以使用标准的类加载器并通过Class.forNameO将其加载进来。现在，我们终于可以开始对这个类进行@Unit注解的分析工作了。
 *
 * 我们只需关心三件事情∶首先是@Test方法，它们将被保存在TestMethos列表中，然后检查是否具有@TestObjectCreate和@TestObjectCleanup方法。从代码中可以看到，我们通过调用相应的方法来查询注解从而找到这些方法。
 *
 * 每当找到一个@Test方法，就打印出当前的类的名字，于是观察者立刻就可以知道发生了什么。接下来开始执行测试，也就是打印出方法名，然后调用createTestObject（（如果存在一个加了@TestObjectCreate注解的方法），
 * 或者调用默认的构造器。一旦创建出测试对象，就调用其上的测试方法。如果测试返回一个boolean值，就捕获该结果。如果测试方法没有返回值，那么当没有异常发生时，我们就假设测试成功，
 * 反之，如果当assert失败或有任何异常抛出时，就说明测试失败，这时将异常信息打印出来以显示错误的原因。如果有失败的测试发生，那么还要统计失败的次数，并将失败的测试所属的类和方法的名字加入failedTests，以便最后将其报告给用户。
 */
