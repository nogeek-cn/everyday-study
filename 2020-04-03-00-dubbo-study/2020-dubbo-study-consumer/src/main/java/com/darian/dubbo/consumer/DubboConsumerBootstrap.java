package com.darian.dubbo.consumer;

import com.darian.dubbo.DemoService;
import com.darian.dubbo.RemoteInterfaceService;
import com.darian.dubbo.module.DubboTestRequest;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;

@EnableAutoConfiguration
public class DubboConsumerBootstrap {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @DubboReference(registry = {"shanghai", "hunan"})
    private DemoService demoService;

    @DubboReference(registry = {"shanghai", "hunan"})
    private RemoteInterfaceService remoteInterfaceService;

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerBootstrap.class).close();
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            for (int i = 0; i < 20; i++) {
                logger.info(demoService.sayHello("Provider [ " + i + " ]"));
                logger.info(remoteInterfaceService.test(new DubboTestRequest("Provider [ " + i + " ]")));
            }
        };
    }
}
