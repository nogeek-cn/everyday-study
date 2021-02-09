
package com.darian.dubbo.registries;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@DubboService(
        registry = {
                "nacos", "zookeeper"
        }
)
@ConditionalOnProperty(value = "spring.profiles.active", havingValue = "registries")
public class DubboRegistriesProviderService implements DubboRegistriesInterface {

}