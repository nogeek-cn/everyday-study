package com.darian.distributed.selector;


import com.darian.distributed.discovery.TaskDiscovery;
import com.darian.distributed.bean.TaskInstance;


import org.springframework.stereotype.Component;


import javax.annotation.Resource;
import java.util.List;


/***
 * 通过注册中心做故障转移，服务发现有延迟，并不保证唯一
 * <br/>
 * 分布式锁 + DistributedTaskConfig.isRunInCurrent() 然后去判断是否执行
 * 内存中的任务队列不执行的时候就清理
 * <br/>
 * 任务状态: 等待执行 执行中 完成/失败，异常-可重试，不可重试
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/10/23  下午9:36
 */
@Component
public class DistributedTaskInstanceSelector {


    @Resource
    private TaskDiscovery taskDiscovery;


    public boolean isRunInCurrentByFirstStrategy() {
        return isRunInCurrent(0);
    }

    public TaskInstance selectServiceInstanceByFirstStrategy() {
        return selectServiceInstance(0);
    }

    /**
     * 是否要在当前实例执行
     *
     * @param wheelIndex 服务实例改为为一轮，这个是第几个
     * @return 是否要在当前实例执行
     */
    public boolean isRunInCurrent(int wheelIndex) {
        TaskInstance taskInstance = selectServiceInstance(wheelIndex);
        TaskInstance localTaskInstance = taskDiscovery.getLocalInstance();
        return localTaskInstance.equals(taskInstance);
    }

    public TaskInstance selectServiceInstance(Integer wheelIndex) {

        List<TaskInstance> sortedTaskInstances = taskDiscovery.getSortedTaskInstances();
        if (sortedTaskInstances == null) {
            return null;
        }
//        InetUtils

        // size = 1, index = 1, 0
        // size = 1, index = 2, 0
        // size = 1, index = 2, 0
        // size = 2, index = 1, 1
        // size = 2, index = 2, 0
        return sortedTaskInstances.get(wheelIndex % sortedTaskInstances.size());
    }

    public static void main(String[] args) {
        System.out.println(1 % 2);
        System.out.println(2 % 2);
    }

}
