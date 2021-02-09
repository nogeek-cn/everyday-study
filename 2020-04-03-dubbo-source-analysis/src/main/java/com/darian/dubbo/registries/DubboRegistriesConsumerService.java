package com.darian.dubbo.registries;

import com.darian.dubbo.remote.DubboRemoteInterface;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "spring.profiles.active", havingValue = "registries")
public class DubboRegistriesConsumerService {

    @DubboReference(
            registry = {
                    "nacos", "zookeeper"
            }
    )
    private DubboRegistriesInterface dubboRegistriesInterface;

    public String say() {
        return dubboRegistriesInterface.say();
    }
}