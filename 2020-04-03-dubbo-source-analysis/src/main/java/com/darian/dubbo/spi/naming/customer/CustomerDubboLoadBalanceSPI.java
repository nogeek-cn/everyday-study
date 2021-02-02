package com.darian.dubbo.spi.naming.customer;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.cluster.LoadBalance;

public class CustomerDubboLoadBalanceSPI {

    public static void main(String[] args) {
        // darianrandom=com.darian.dubbo.spi.naming.customer.DarianRandomLoadBalance
        LoadBalance darianrandom = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension("darianrandom");
        //com.darian.dubbo.spi.naming.customer.DarianRandomLoadBalance@27082746
        System.out.println(darianrandom);
    }
}