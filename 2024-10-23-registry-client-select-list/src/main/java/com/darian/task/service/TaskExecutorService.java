package com.darian.task.service;


import com.darian.distributed.bean.TaskInstance;
import com.darian.distributed.selector.DistributedTaskInstanceSelector;
import com.darian.task.entity.TaskDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/10/24  11:52
 */
@Service
public class TaskExecutorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskExecutorService.class);

    private static volatile CopyOnWriteArrayList<TaskDo> MEMORY_TASK = new CopyOnWriteArrayList<>();
    private static volatile int MEMORY_TASK_MAX_SIZE = 500;

    @Resource
    private DistributedTaskInstanceSelector distributedTaskInstanceSelector;

    @Resource
    private TaskStatusChange taskStatusChange;

    public void addFirst(TaskDo taskDo) {
        MEMORY_TASK.add(0, taskDo);
    }

    public void doRefresh() {
        List<TaskDo> dbTaskDoList = Collections.emptyList();
        MEMORY_TASK = new CopyOnWriteArrayList<>(dbTaskDoList);
    }

    public void notifyToRefresh() {
        // todo: 从数据库中获取任务列表
        if (distributedTaskInstanceSelector.isRunInCurrentByFirstStrategy()) {
            doRefresh();
        } else {
            TaskInstance taskInstance = distributedTaskInstanceSelector.selectServiceInstanceByFirstStrategy();
            // todo: 远程调用通知那台机器去刷新对应的任务列表
            // todo: 什么 RPC 协议，那就怎么调用
        }
    }


    public void execute() {
        if (!distributedTaskInstanceSelector.isRunInCurrentByFirstStrategy()) {
            LOGGER.info("[distributedTaskInstanceSelector][isRunInCurrentByFirstStrategy][first]");
        }
        // RefreshTask async
        async("RefreshTask", () -> {
            if (MEMORY_TASK == null || MEMORY_TASK.size() < MEMORY_TASK_MAX_SIZE / 2) {
                doRefresh();
            }
        });
        if (MEMORY_TASK == null || MEMORY_TASK.isEmpty()) {
            LOGGER.info("[MEMORY_TASK][isEmpty][first]");
            return;
        }

        TaskDo taskDo = callIgnore(() -> MEMORY_TASK.remove(0), IndexOutOfBoundsException.class);

        if (Objects.isNull(taskDo)) {
            LOGGER.info("[MEMORY_TASK][first][isEmpty]");
            return;
        }

        taskStatusChange.changeToRunning();

        try {
            // todo: 任务的执行逻辑
            // todo: 去做具体的任务的执行逻辑
            taskStatusChange.changeToFinished();
        } catch (Exception e) {
            // 异常分类是否可以重试的异常
            // todo: 记录异常日志
            taskStatusChange.changeToException();
        }


    }

    public void async(String taskName, Runnable runnable) {
        // todo: 一步工具，提交到线程池工具里边
    }

    public <T> T callIgnore(Callable<T> callable, Class<?> ignoreExceptionClass) {
        try {
            return callable.call();
        } catch (Exception e) {
            if (ignoreExceptionClass.isAssignableFrom(e.getClass())) {
                LOGGER.info("[callIgnore][ignoreException][{}]", e.getMessage());
                return null;
            }
            throw new RuntimeException(e);
        }
    }


}
