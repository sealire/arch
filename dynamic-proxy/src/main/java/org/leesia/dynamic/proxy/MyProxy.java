package org.leesia.dynamic.proxy;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @Auther: leesia
 * @Date: 2018/9/10 11:11
 * @Description:
 */
public class MyProxy {

    private static String ln = "\r\n";

    /**
     * Proxy最重要的方法是newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h){ ..}
     *
     * @param loader
     * @param interfaces
     * @param h
     * @return
     */
    public static Object newProxyInstance(MyClassLoader loader, Class<?>[] interfaces, MyInvocationHandler h) {
        try {
            //1. 生成源代码
            String proxySrc = generateSrc(interfaces[0]);
            //2. 将源代码输出到磁盘中
            String filePath = MyProxy.class.getResource("").getPath();
            File f = new File(filePath + "$Proxy0.java");
            FileWriter fw = new FileWriter(f);
            System.out.println(f.getAbsolutePath());
            fw.write(proxySrc);
            fw.flush();
            fw.close();
            //3. 编译源代码，并且生成.class文件
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
            //4. 将class文件中的内容，动态加载到jvm中
            Iterable iterable = manager.getJavaFileObjects(f);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, iterable);
            task.call();
            manager.close();
            //5. 返回被代理后的对象
            Class proxyClass = loader.findClass("$Proxy0");
            Constructor c = proxyClass.getConstructor(MyInvocationHandler.class);
            f.delete();
            return c.newInstance(h);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private static String generateSrc(Class<?> interfaces) {
        StringBuffer src = new StringBuffer();
        src.append("package org.leesia.dynamic.proxy;" + ln);
        src.append("import java.lang.reflect.Method;" + ln);
        src.append("public class $Proxy0 implements " + interfaces.getName() + "{ " + ln);

        src.append("MyInvocationHandler h;" + ln);
        src.append("public $Proxy0(MyInvocationHandler h){" + ln);
        src.append("this.h = h;" + ln);
        src.append("}" + ln);

        for (Method m : interfaces.getMethods()) {
            src.append("public " + m.getReturnType().getName() + " " + m.getName() + "()" + "{" + ln);

            src.append("try{" + ln);

            //输出双引号 "\""
            src.append("Method m = " + interfaces.getName() + ".class.getMethod(\"" + m.getName() + "\",new Class[]{});" + ln);
            src.append("this.h.invoke(this,m,null);" + ln);

            src.append("}catch(Throwable e){e.printStackTrace();}" + ln);

            src.append("}" + ln);
        }
        src.append("}");
        return src.toString();
    }
}
