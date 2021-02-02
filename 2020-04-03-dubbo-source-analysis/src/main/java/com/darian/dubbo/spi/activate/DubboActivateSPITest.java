package com.darian.dubbo.spi.activate;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Filter;

public class DubboActivateSPITest {

    public static void main(String[] args) {
        ExtensionLoader<Filter> extensionLoader = ExtensionLoader.getExtensionLoader(Filter.class);
        URL dubboUrl = new URL("", "", 0);

        // [org.apache.dubbo.rpc.filter.EchoFilter@5cb9f472, org.apache.dubbo.rpc.filter.ClassLoaderFilter@cb644e, org.apache.dubbo.rpc
        // .filter.GenericFilter@13805618, org.apache.dubbo.rpc.filter.ConsumerContextFilter@56ef9176, org.apache.dubbo.rpc.filter
        // .ContextFilter@4566e5bd, org.apache.dubbo.rpc.protocol.dubbo.filter.FutureFilter@1ed4004b, org.apache.dubbo.rpc.protocol.dubbo
        // .filter.TraceFilter@ff5b51f, org.apache.dubbo.rpc.filter.TimeoutFilter@25bbe1b6, org.apache.dubbo.monitor.support
        // .MonitorFilter@5702b3b1, org.apache.dubbo.rpc.filter.ExceptionFilter@69ea3742]
        System.out.println(extensionLoader.getActivateExtension(dubboUrl, "cache"));

        // 10
        System.out.println(extensionLoader.getActivateExtension(dubboUrl, "cache").size());

        dubboUrl = dubboUrl.addParameter("cache", "cache");
        // 11
        System.out.println(extensionLoader.getActivateExtension(dubboUrl, "cache").size());
    }
}