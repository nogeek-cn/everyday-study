package com.darian.dubbo.protocols;

public interface DubboProtocolsRemoteInterface {

    default String say() {
        return this.getClass().getName() + ".say";
    }
}