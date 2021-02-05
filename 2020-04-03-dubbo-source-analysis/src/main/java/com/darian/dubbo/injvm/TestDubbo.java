
package com.darian.dubbo.injvm;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.protocol.injvm.InjvmProtocol;
import org.springframework.stereotype.Service;

@Service
public class TestDubbo {

    @DubboReference(
            parameters = {"scope", "local"}
            //, protocol = InjvmProtocol.NAME
    )
    public TestInterface testInterface;

    public void say() {
        System.out.println(testInterface.say());
    }
}