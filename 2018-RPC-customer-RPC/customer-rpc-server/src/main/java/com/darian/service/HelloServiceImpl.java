package com.darian.service;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a> 
 * @date 2020/6/7  17:45
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String msg) {
        return "[ServerDemo.HelloServiceImpl]msg:{"+msg+"}";
    }
}
