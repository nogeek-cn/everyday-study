package com.darian.dubbo.provider;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;


@EnableAutoConfiguration
public class DubboProviderBootstrap2 {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboProviderBootstrap2.class)
                .run(args);
    }
}