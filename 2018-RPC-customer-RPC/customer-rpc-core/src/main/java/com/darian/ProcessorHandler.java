
package com.darian;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class ProcessorHandler<T> implements Runnable {

    private Socket socket;

    private T service;// 服务端发布的服务

    public ProcessorHandler(Socket socket, T service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        // 去处理我们的请求
        // 怎么拿到我们的对象

        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             //通过输出流讲结果输出给客户端
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {

            // 读取远程传输过来的对象 可以
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();

            //通过反射去调用本地的方法
            Object result = invoke(rpcRequest);

            objectOutputStream.writeObject(result);

            objectOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 利用反射调用相应的方法
    private Object invoke(RpcRequest request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object[] parameters = request.getParameters();
        Class<?>[] types = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            types[i] = parameters[i].getClass();
        }

        Method method = service.getClass().getMethod(request.getMethodName(), types);
        return method.invoke(service, parameters);

    }
}