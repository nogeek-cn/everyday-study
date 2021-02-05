
package com.darian.dubbo.injvm;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.protocol.injvm.InjvmProtocol;

@DubboService(
        parameters = {"scope", "local"}
        //, protocol = {InjvmProtocol.NAME}
        //, interfaceClass = TestInterface.class
)
public class LocalService implements TestInterface {

    @Override
    public String say() {
        return "com.darian.LocalService.say";
    }
}