
package com.darian.dubbo.protocols;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.springframework.context.annotation.Profile;


@DubboService(protocol = {DubboProtocol.NAME, "rmi"})
@Profile("protocols")
public class DubboProtocolsRemoteProviderService implements DubboProtocolsRemoteInterface {
}