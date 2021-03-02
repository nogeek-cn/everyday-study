
package com.darian.dubbo.registries;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.context.annotation.Profile;

@DubboService(
        registry = {
                "nacos", "zookeeper"
        }
)
@Profile("registries")
public class DubboRegistriesProviderService implements DubboRegistriesInterface {

}