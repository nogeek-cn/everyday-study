package com.darian.config.config;

import io.microsphere.spring.context.event.BeanListener;
import io.microsphere.spring.context.event.BeanTimeStatistics;
import io.microsphere.spring.context.event.LoggingBeanListener;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/10/27  14:22
 */
@Configuration
public class TestAAAConfig implements ApplicationListener<ApplicationReadyEvent> {


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
//        TestConfig bean = event.getApplicationContext().getBean(TestConfig.class);
//        System.out.println("xxxxx:" + bean.beanTimeStatistics.getStopWatch().toString());
    }

//    @Configuration
//    public static class TestConfig {
//
//        private final ObjectProvider<BeanListener[]> beanListeners;
//
//        private final ObjectProvider<List<BeanListener>> beanListenersList;
//
//        @Autowired
//        private ObjectProvider<BeanTimeStatistics> beanTimeStatisticsObjectProvider;
//
//        @Autowired
//        private ObjectProvider<LoggingBeanListener> loggingBeanListenerObjectProvider;
//
//        @Resource(name = "io.microsphere.spring.context.event.BeanTimeStatistics#0")
//        private BeanTimeStatistics beanTimeStatistics;
//
//        @Resource(type = LoggingBeanListener.class)
//        private LoggingBeanListener loggingBeanListener;
//
//        @Resource
//        public void setLoggingBeanListener(LoggingBeanListener loggingBeanListener) {
//
//        }
//
//        public TestConfig(ObjectProvider<BeanListener[]> beanListeners,
//                          ObjectProvider<List<BeanListener>> beanListenersList,
//                          @Qualifier("io.microsphere.spring.context.event.LoggingBeanListener#0") LoggingBeanListener loggingBeanListener) {
//            this.beanListeners = beanListeners;
//            this.beanListenersList = beanListenersList;
//        }
//
//    }


}
