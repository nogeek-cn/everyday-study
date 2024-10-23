package com.darian.task.discovery;

import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.exception.NacosException;
import com.darian.task.bean.TaskInstance;
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
public class DubboTaskDiscovery implements TaskDiscovery {
    private static final Logger LOGGER = LoggerFactory.getLogger(DubboTaskDiscovery.class);

    private final NacosServiceDiscovery nacosServiceDiscovery;
    private final String localInstanceServiceId;
    private final Integer localPortToBind;

    public DubboTaskDiscovery(NacosServiceDiscovery nacosServiceDiscovery, String localInstanceServiceId, Integer localPortToBind) {
        this.nacosServiceDiscovery = nacosServiceDiscovery;
        this.localInstanceServiceId = localInstanceServiceId;
        this.localPortToBind = localPortToBind;
    }

    @Override
    public List<TaskInstance> getSortedTaskInstances(String serviceId) {
        List<ServiceInstance> nacosInstances = null;
        try {
            nacosInstances = nacosServiceDiscovery.getInstances(serviceId);
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
    }

    @Override
    public String getLocalInstanceServiceId() {
        return localInstanceServiceId;
    }


    @Override
    public TaskInstance getLocalInstance() {
        String localInstanceBindHost = NetUtils.getLocalHost();
        return new TaskInstance(localInstanceBindHost, localPortToBind);
    }
}
