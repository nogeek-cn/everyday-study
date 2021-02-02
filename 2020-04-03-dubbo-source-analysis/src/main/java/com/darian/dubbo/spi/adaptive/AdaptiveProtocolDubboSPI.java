package com.darian.dubbo.spi.adaptive;

import org.apache.dubbo.common.compiler.Compiler;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Protocol;

public class AdaptiveProtocolDubboSPI {

    public static void main(String[] args) {
        Protocol protocolAdaptiveExtension = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
        // org.apache.dubbo.rpc.Protocol$Adaptive@73846619
        System.out.println(protocolAdaptiveExtension);

        Compiler adaptiveAdaptiveExtension = ExtensionLoader.getExtensionLoader(Compiler.class).getAdaptiveExtension();
        // org.apache.dubbo.common.compiler.support.AdaptiveCompiler@4bec1f0c
        System.out.println(adaptiveAdaptiveExtension);
    }
}