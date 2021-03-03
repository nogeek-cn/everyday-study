package com.darian.dubbo.onlyconsumer;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("onlyconsumer")
public class DubboOnlyConsumerService {

    @DubboReference
    private OnlyConsumerRemoteInterface onlyConsumerRemoteInterface;
    // org.apache.dubbo.common.bytecode.proxy0@42c6df2
    // - handler: org.apache.dubbo.rpc.proxy.InvokerInvocationHandler@7b83a53c
    //   - invoker:  invoker :interface com.darian.dubbo.onlyconsumer.OnlyConsumerRemoteInterface -> nacos://nacos.deepin.darian.top:81/org.apache.dubbo.registry.RegistryService?application=testname&check=false&dubbo=2.0.2&init=false&interface=com.darian.dubbo.onlyconsumer.OnlyConsumerRemoteInterface&methods=say&pid=46464&qos.enable=false&register.ip=30.45.75.216&release=2.7.7&side=consumer&sticky=false&timestamp=1614746297899,directory: org.apache.dubbo.registry.integration.RegistryDirectory@a146b11
    //     - invoker: interface com.darian.dubbo.onlyconsumer.OnlyConsumerRemoteInterface -> nacos://nacos.deepin.darian.top:81/org.apache.dubbo.registry.RegistryService?application=testname&check=false&dubbo=2.0.2&init=false&interface=com.darian.dubbo.onlyconsumer.OnlyConsumerRemoteInterface&methods=say&pid=46464&qos.enable=false&register.ip=30.45.75.216&release=2.7.7&side=consumer&sticky=false&timestamp=1614746297899

    public String say() {
        return onlyConsumerRemoteInterface.say();
    }
}