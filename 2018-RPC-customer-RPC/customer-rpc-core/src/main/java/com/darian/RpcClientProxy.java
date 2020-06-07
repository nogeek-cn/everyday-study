
package com.darian;

import java.lang.reflect.Proxy;

public class RpcClientProxy {

    // 这个里边要去做一个代理，

    public <T> T clientProxy(final Class<T> interfaceCls,
                             final String host, final  int port){
        return (T) Proxy.newProxyInstance(
                interfaceCls.getClassLoader(),
                new Class[] {interfaceCls},
                new RemoteInvocationHandler(host,port));
    }
}