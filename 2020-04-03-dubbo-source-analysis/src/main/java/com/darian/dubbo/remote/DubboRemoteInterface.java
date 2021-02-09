package com.darian.dubbo.remote;

public interface DubboRemoteInterface {

    default String say() {
        return this.getClass().getName() + ".say";
    }
}