package com.baihy;

import com.baihy.service.IndexService;
import com.baihy.service.impl.IndexServiceImpl;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.core.DefaultGeneratorStrategy;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.MethodInterceptor;

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
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "d:/");
        IndexService target = new IndexServiceImpl();
        MethodInterceptor methodInterceptor = getMethodInterceptor();
        IndexService proxy = (IndexService) createProxy(methodInterceptor);
        proxy.index("huayang.bai");

    }

    public static Object createProxy(Callback callback) {
        // cglib实现代理类的核心类，Enhancer译为：增强剂
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(IndexServiceImpl.class); // 设置代理类继承目标类
        // 目标类已经实现了UseService接口，而cglib的代理是通过继承来实现的，所以，代理类已经实现了UserService接口
        //enhancer.setInterfaces(new Class[]{UserService.class});
        enhancer.setStrategy(new DefaultGeneratorStrategy());
        enhancer.setCallback(callback);// 设置Callback就相当于是动态封装代理类和目标类中的逻辑
        return enhancer.create();
    }


    public static InvocationHandler getInvocationHandler(Object target) {
        return (proxy, method, args) -> {
            /**
             *
             * @param proxy 表示是代理对象
             * @param method 表示的是目标对象的方法
             * @param args 表示的是目标对象的方法的参数
             * @return
             * @throws Throwable
             */
            System.out.println(proxy.getClass());
            System.out.println("##################目标方法调用之前########## #########");
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
