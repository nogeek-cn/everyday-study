package _2021_04_29_interview_reflect_node_list;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface IA {
    String getHelloName();
}

public class ShowMeBug_1 {
    public static void main(String[] arges) throws Exception {
        IA ia = (IA) createObject(IA.class.getName() + "$getHelloName=Abc");
        System.out.println(ia.getHelloName()); //方法名匹配的时候，输出“Abc”
        ia = (IA) createObject(IA.class.getName() + "$getTest=Bcd");
        System.out.println(ia.getHelloName()); //方法名不匹配的时候，输出null
    }

    //请实现方法createObject，接口中"getName()"方法名仅仅是个示例，不能写死判断

    public static Object createObject(String str) throws Exception {
        return (IA) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{IA.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (str.indexOf("$") < 0) {
                            return null;
                        }
                        if (str.indexOf("=") < 0) {
                            return null;
                        }
                        String invokeMethodName = str.substring(str.indexOf("$") + 1, str.indexOf("="));
                        String resultStr = str.substring(str.indexOf("=") + 1);

                        if (method.getName().equals(invokeMethodName)) {
                            return resultStr;
                        }
                        return null;
                    }
                });

//        throw new Exception("还没有实现的方法");
    }
}
