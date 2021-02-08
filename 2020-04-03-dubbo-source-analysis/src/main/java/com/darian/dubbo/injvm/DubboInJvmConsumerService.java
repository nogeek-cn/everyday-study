
package com.darian.dubbo.injvm;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.protocol.injvm.InjvmProtocol;
import org.springframework.stereotype.Service;

@Service
public class DubboInJvmConsumerService {

    /**
     * @DubboReference(parameters = {"scope", "local"})
     * <p></p>
     * @DubboReference(protocol = InjvmProtocol.NAME) 推荐使用这个
     * 这两个都可以 实现 本地调用
     */
    @DubboReference(protocol = InjvmProtocol.NAME)
    public DubboInJvmInterface dubboInJvmInterface;

    public void say() {
        System.out.println(dubboInJvmInterface.say());
    }
}