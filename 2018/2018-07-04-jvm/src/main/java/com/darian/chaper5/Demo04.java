package com.darian.chaper5;

import java.io.IOException;
import java.io.InputStream;

/**
 * 加载当前包下的类
 */
public class Demo04 {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        ClassLoader mycload = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                // com.gupao.edu.vip.course.chaper5.Demo04.Class
                String fileName = name.substring(name.lastIndexOf(".") +1) + ".class";
                InputStream in = getClass().getResourceAsStream(fileName);
                if(in == null){
                    return super.loadClass(name);
                }
                try {
                    byte[] buff = new byte[in.available()];
                    in.read(buff);
                    return defineClass(name,buff,0,buff.length);
                } catch (IOException e) {
                    throw  new ClassNotFoundException();
                }
            }
        };
        System.out.println(mycload.getClass().getClassLoader());
        System.out.println(Demo04.class.getClassLoader());
        Object obj = mycload.loadClass("com.gupao.edu.vip.course.chaper5.Demo04").newInstance();

        System.out.println(obj.getClass().getClassLoader());
        System.out.println(obj instanceof  Demo04);
    }
}
