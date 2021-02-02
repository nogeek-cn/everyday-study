package com.darian.dubbo.spi.naming;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.cluster.LoadBalance;

/**
 * 内置的 LoadBalance 扩展点
 */
public class InterNalNamingDubboLoadBalanceSPI {

    public static void main(String[] args) {
        LoadBalance randomLoadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension("random");
        // org.apache.dubbo.rpc.cluster.loadbalance.RandomLoadBalance@56f4468b
        System.out.println(randomLoadBalance);
    }

}