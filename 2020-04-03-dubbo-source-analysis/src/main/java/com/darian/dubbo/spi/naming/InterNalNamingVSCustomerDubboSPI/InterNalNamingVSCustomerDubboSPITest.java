package com.darian.dubbo.spi.naming.InterNalNamingVSCustomerDubboSPI;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.cluster.LoadBalance;

public class InterNalNamingVSCustomerDubboSPITest {

    public static void main(String[] args) {
        LoadBalance randomLoadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension("random");
        // com.darian.dubbo.spi.InterNalNamingVSCustomerDubboSPI.MyRandomLoadBalance@27082746
        // 所以 dubbo/ 目录下的优先于 dubbo/internal 目录下的
        System.out.println(randomLoadBalance);
    }
}