
package com.darian.dubbo.injvm;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.protocol.injvm.InjvmProtocol;

/**
 * @DubboService(parameters = {"scope", "local"})
 * <p>或者</p>
 * @DubboService(protocol = InjvmProtocol.NAME)
 * 两个都可以实现本地发布 JVM 服务
 */
//@DubboService(parameters = {"scope", "local"})
@DubboService(protocol = InjvmProtocol.NAME)
public class DubboInJvmProviderService implements DubboInJvmInterface {

    @Override
    public String say() {
        return "com.darian.LocalService.say";
    }
}