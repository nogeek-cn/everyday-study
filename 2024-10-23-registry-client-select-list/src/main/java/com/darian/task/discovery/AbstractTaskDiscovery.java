package com.darian.task.discovery;

import com.darian.task.bean.TaskInstance;

import java.util.List;
import java.util.function.Supplier;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/10/24  08:07
 */
public abstract class AbstractTaskDiscovery implements TaskDiscovery {
    protected Integer localPortToBind;
    protected Supplier<String> localInstanceBindHostSupplier;
    protected Supplier<List<TaskInstance>> sortedTaskInstancesSupplier;

    @Override
    public final List<TaskInstance> getSortedTaskInstances() {
        return sortedTaskInstancesSupplier.get();
    }


    @Override
    public final TaskInstance getLocalInstance() {
        return new TaskInstance(localInstanceBindHostSupplier.get(), localPortToBind);
    }
}
