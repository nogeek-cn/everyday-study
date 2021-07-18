package com.darian.dubbo.provider;

import com.darian.dubbo.RemoteInterfaceService;
import com.darian.dubbo.module.DubboTestRequest;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/7/18  下午3:04
 */
@DubboService(
        registry = {"shanghai", "hunan"})
public class RemoteInterfaceServiceImpl implements RemoteInterfaceService {

    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Override
    public String test(DubboTestRequest request) {
        logger.info("RemoteInterfaceServiceImpl Hello " + request.getName() + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "RemoteInterfaceServiceImpl Hello " + request.getName() + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }
}
