
package com.darian;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHandler implements InvocationHandler {
    private String host;
    private int    port;

    public RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 传输一些东西，让服务端知道我在做什么
        // 就必须传一些东西给服务端。
        // 建立一个传输
        RpcRequest request = new RpcRequest();

        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameters(args);

        // 然后把这个对象作为一个传输

        TCPTransport tcpTransport = new TCPTransport(this.host, this.port);

        return tcpTransport.send(request);
    }
}