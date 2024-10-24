package com.darian.distributed.discovery;

import com.darian.distributed.bean.TaskInstance;

import java.util.List;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/10/24  06:59
 */
public interface TaskDiscovery {
    List<TaskInstance> getSortedTaskInstances();

    TaskInstance getLocalInstance();
}
