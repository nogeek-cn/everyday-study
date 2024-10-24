package com.darian.distributed.discovery;

import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.exception.NacosException;
import com.darian.distributed.bean.TaskInstance;
import org.apache.dubbo.common.utils.NetUtils;
import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.core.env.Environment;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/10/24  07:00
 */
public class DubboTaskDiscovery extends AbstractTaskDiscovery {
    private static final Logger LOGGER = LoggerFactory.getLogger(DubboTaskDiscovery.class);


    public DubboTaskDiscovery(NacosServiceDiscovery nacosServiceDiscovery, Environment environment) {
        // 注册中心 serviceId
        String localInstanceServiceId = environment.getProperty("spring.application.name");
        // 注册中心 注册实例 本地服务绑定的 IP
        this.localInstanceBindHostSupplier = NetUtils::getLocalHost;
        // 注册中心 注册实例 本地服务绑定的端口
        Integer localPortToBind = environment.getProperty("dubbo.protocol.port", Integer.class);
        if (Objects.isNull(localPortToBind)) {
            localPortToBind = DubboProtocol.DEFAULT_PORT;
        }
        this.localPortToBind = localPortToBind;
        // 注册中心 注册实例 本地服务实例 方法
        this.sortedTaskInstancesSupplier = () -> {
            List<ServiceInstance> nacosInstances = null;
            try {
                nacosInstances = nacosServiceDiscovery.getInstances(localInstanceServiceId);
            } catch (NacosException e) {
                throw new RuntimeException(e);
            }

            if (nacosInstances == null) {
                return null;
            }

            List<TaskInstance> taskInstanceList = nacosInstances.stream()
                    .filter(Objects::nonNull)
                    .filter(nacosInstance -> nacosInstance.getHost() != null)
                    .map(nacosInstance ->
                            new TaskInstance(nacosInstance.getHost(), nacosInstance.getPort()))
                    .sorted(Comparator.comparing(TaskInstance::getHost)
                            .thenComparing(TaskInstance::getPort))
                    .collect(Collectors.toList());
            LOGGER.info("taskInstanceList:{}", taskInstanceList);
            return taskInstanceList;
        };


    }


}
