package com.darian.dubbo.registries;

public interface DubboRegistriesInterface {

    default String say() {
        return this.getClass().getName() + ".say";
    }
}