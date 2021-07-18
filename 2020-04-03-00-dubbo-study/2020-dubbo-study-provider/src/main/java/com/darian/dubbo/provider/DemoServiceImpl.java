package com.darian.dubbo.provider;

import com.darian.dubbo.DemoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DubboService(
        registry = {"shanghai", "hunan"})
public class DemoServiceImpl implements DemoService {
    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Override
    public String sayHello(String name) {
        logger.info("DemoServiceImpl Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "DemoServiceImpl Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }

}
