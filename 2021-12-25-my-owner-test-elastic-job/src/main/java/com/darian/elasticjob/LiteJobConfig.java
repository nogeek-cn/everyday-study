package com.darian.elasticjob;

import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.lite.internal.schedule.JobScheduler;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperConfiguration;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.context.annotation.Bean;

/***
 *
 * @author darian1996
 */
public class LiteJobConfig {
    private static final String SERVER_LISTS = "192.168.24.128:2181";

    private static final String NAMES_SPACE = "myDataFlowJob";

    @Bean
    public static void JobScheduler() {
//        new ScheduleJobBootstrap(zkCenter(), new MyDataFlowJob(), createJobConfiguration()).schedule();

//        new JobScheduler(zkCenter(), dataFlowJobConfiguration()).init();
    }


    public static CoordinatorRegistryCenter zkCenter() {
        ZookeeperConfiguration zookeeperConfiguration =
                new ZookeeperConfiguration(SERVER_LISTS, NAMES_SPACE);

        CoordinatorRegistryCenter coordinatorRegistryCenter =
                new ZookeeperRegistryCenter(zookeeperConfiguration);
        //注册中心初始化
        coordinatorRegistryCenter.init();
        return coordinatorRegistryCenter;
    }


    /**
     * job配置
     *
     * @return
     */
    public static JobConfiguration dataFlowJobConfiguration() {
        // , "0/10 * * * * ? ", 2
        // job核心配置
        JobConfiguration jobCoreConfiguration = null;
//        JobConfiguration jobCoreConfiguration = JobConfiguration
//                .newBuilder("myDataFlowJob")
//                .build();

//        //job类型配置
//        JobTypeConfiguration jobTypeConfiguration =
//                new DataflowJobConfiguration(jobCoreConfiguration, MyDataFlowJob.class.getCanonicalName(),true);
//
//        //job根的配置
//        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration
//                .newBuilder(jobTypeConfiguration)
//                .build();
        return jobCoreConfiguration;
    }
}
