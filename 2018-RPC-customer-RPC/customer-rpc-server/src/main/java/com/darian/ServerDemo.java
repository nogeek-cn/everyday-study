package com.darian;

import com.darian.service.HelloService;
import com.darian.service.HelloServiceImpl;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/6/7  17:45
 */
public class ServerDemo {
    public static void main(String[] args) {
        HelloService helloService=new HelloServiceImpl();
        RpcServer rpcServer=new RpcServer();
        rpcServer.publisher(helloService,8888);
    }
}
