package com.baihy;

import com.baihy.service.IndexService;
import com.baihy.service.impl.IndexServiceImpl;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.core.DefaultGeneratorStrategy;
import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;

/**
 * @projectName: spring-study-demo
 * @packageName: com.baihy.main
 * @description:
 * @author: huayang.bai
 * @date: 2019/9/8 11:34 上午
 */
public class CglibMain {

    public static void main(String[] args) {
        /**
         * 设置class文件的输出地址。
         *  通过源码我们知道，如果没有设置DebuggingClassWriter.DEBUG_LOCATION_PROPERTY环境变量，就不会输出class文件
         */
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "d:/code");

        IndexService target = new IndexServiceImpl("abc");

        InvocationHandler invocationHandler0 = getInvocationHandler0(target);
        InvocationHandler invocationHandler1 = getInvocationHandler1(target);
        InvocationHandler invocationHandler2 = getInvocationHandler2(target);

        IndexService proxy = (IndexService) createProxy(new Callback[]{invocationHandler0, invocationHandler1, invocationHandler2});
        proxy.index0("huayang.bai");
        proxy.index1("huayang.bai");
        proxy.index2("huayang.bai");

    }

    public static Object createProxy(Callback[] callbacks) {
        // cglib实现代理类的核心类，Enhancer译为：增强剂
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(IndexServiceImpl.class); // 设置代理类继承目标类(可以是类，也可以是接口)
        // 目标类已经实现了UseService接口，而cglib的代理是通过继承来实现的，所以，代理类已经实现了UserService接口
        //enhancer.setInterfaces(new Class[]{UserService.class});
        enhancer.setStrategy(new DefaultGeneratorStrategy()); // 默认的，可设置，可不设置。
        enhancer.setCallbacks(callbacks);// 设置Callback就相当于是动态封装代理类和目标类中的逻辑
        //  Class clazz = enhancer.createClass();// 生成代理类的字节码对象
        enhancer.setCallbackFilter(new CallbackFilter() {
            /**
             * CallbackFilter：作用是：为方法执行拦截器，accept方法的返回值就是Callbacks数组的索引。
             */
            @Override
            public int accept(Method method) {
                // System.out.println(method.getName());
                if (method.getName().equals("index0")) {
                    //表示的含义是：当方法的名称为index0是，使用callbacks数组下标为2的Callback
                    return 2;
                }
                if (method.getName().equals("index1")) {
                    return 1;
                }
                if (method.getName().equals("index2")) {
                    return 0;
                }
                return 0;
            }
        });
        return enhancer.create(new Class[]{String.class}, new Object[]{"abc"});// 创建一个代理对象,指定调用父类含有参数的构造方法
        // return enhancer.create();// 创建一个代理对象
    }


    public static InvocationHandler getInvocationHandler0(Object target) {
        return (proxy, method, args) -> {
            /**
             *
             * @param proxy 表示是代理对象
             * @param method 表示的是目标对象的方法
             * @param args 表示的是目标对象的方法的参数
             * @return
             * @throws Throwable
             */
            // System.out.println(proxy.getClass());
            System.out.println("##################目标方法调用之前########## #########");
            System.out.println("0000000000000000000000000000" + method.getName());
            Object result = method.invoke(target, args); // 注意：这里一定要使用目标对象来调用。
            System.out.println("##################目标方法调用之后###################");
            return result;
        };
    }

    public static InvocationHandler getInvocationHandler1(Object target) {
        return (proxy, method, args) -> {
            /**
             *
             * @param proxy 表示是代理对象
             * @param method 表示的是目标对象的方法
             * @param args 表示的是目标对象的方法的参数
             * @return
             * @throws Throwable
             */
            // System.out.println(proxy.getClass());
            System.out.println("##################目标方法调用之前########## #########");
            System.out.println("111111111111111111111111" + method.getName());
            Object result = method.invoke(target, args); // 注意：这里一定要使用目标对象来调用。
            System.out.println("##################目标方法调用之后###################");
            return result;
        };
    }

    public static InvocationHandler getInvocationHandler2(Object target) {
        return (proxy, method, args) -> {
            /**
             *
             * @param proxy 表示是代理对象
             * @param method 表示的是目标对象的方法
             * @param args 表示的是目标对象的方法的参数
             * @return
             * @throws Throwable
             */
            // System.out.println(proxy.getClass());
            System.out.println("##################目标方法调用之前########## #########");
            System.out.println("222222222222222222222" + method.getName());
            Object result = method.invoke(target, args); // 注意：这里一定要使用目标对象来调用。
            System.out.println("##################目标方法调用之后###################");
            return result;
        };
    }

    public static MethodInterceptor getMethodInterceptor() {
        /**
         * 定义一个方法拦截器
         * @param obj  是代理对象
         * @param method  拦截的真实对象的方法
         * @param args 拦截的真实方法的参数数组
         * @param proxy  代理对象中，生成的代理对象的方法
         * @return
         * @throws Throwable
         */
        return (proxy, method, args, methodProxy) -> {
            System.out.println(proxy.getClass());
            // 如果想使用method对象，这个对象是目标对象的方法，所以反射调用时，一定要用目标对象
            //Object result = method.invoke(userService, args);// 这里是使用目标对象通过反射来调用目标对象的方法。
            System.out.println(methodProxy.getClass());
            Object result1 = methodProxy.invokeSuper(proxy, args); // 这里是通过代理对象来的调用代理对象的方法。来调用目标对象的方法。
            /**
             * 这里为什么可以不实例化目标对象，只要实例化代理对象，就能实现目标对象的调用呢？
             *  因为cglib的代理对象和目标对象是继承关系，当实现子类(代理对象)时，会自动调用父类中的构造方法实例化。所以这不需要实例化目标对象
             */
            System.out.println(result1);
            return result1;
        };
    }

}
