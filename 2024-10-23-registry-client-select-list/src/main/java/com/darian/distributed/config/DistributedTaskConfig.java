package com.darian.distributed.config;

import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.darian.distributed.discovery.DubboTaskDiscovery;
import com.darian.distributed.discovery.TaskDiscovery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/10/24  07:07
 */
@Configuration
public class DistributedTaskConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedTaskConfig.class);

    @Resource
    private NacosServiceDiscovery nacosServiceDiscovery;
    @Resource
    private Environment environment;

    @Bean
    public TaskDiscovery taskDiscovery() {
        DubboTaskDiscovery dubboTaskDiscovery = new DubboTaskDiscovery(nacosServiceDiscovery, environment);
        LOGGER.info("[TaskDiscovery.localInstance][{}]", dubboTaskDiscovery.getLocalInstance());
        return dubboTaskDiscovery;
    }
}
