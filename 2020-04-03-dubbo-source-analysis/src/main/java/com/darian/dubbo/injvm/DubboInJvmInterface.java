
package com.darian.dubbo.injvm;

public interface DubboInJvmInterface {
    default String say() {
        return this.getClass().getName() + ".say";
    }
}