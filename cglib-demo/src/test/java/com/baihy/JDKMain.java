package com.baihy;


import com.baihy.service.IndexService;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

/**
 * @projectName: spring-study-demo
 * @packageName: com.baihy.main
 * @description:
 * @author: huayang.bai
 * @date: 2019/09/09 17:26
 */
public class JDKMain {


    public static void main(String[] args) {
        generator();
    }

    /**
     * jdk的动态代理生成代理类
     */
    public static void generator() {
        String className = "Aaa";
        Class<?>[] interfaces = new Class[]{IndexService.class};
        byte[] bytes = ProxyGenerator.generateProxyClass(className, interfaces);
        try (FileOutputStream outputStream = new FileOutputStream("cglib-demo/target/classes/Aaa.class")) {
            outputStream.write(bytes);
            outputStream.flush();
            System.out.println("*****************字节码文件生成完成****************");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
