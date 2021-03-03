package com.darian.dubbo.registries;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("registries")
public class DubboRegistriesConsumerService {

    @DubboReference(
            registry = {
                    "nacos", "zookeeper"
            },
            injvm = false
    )
    private DubboRegistriesInterface dubboRegistriesInterface;

    public String say() {
        return dubboRegistriesInterface.say();
    }
}