package com.darian.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/10/21  下午9:57
 */
@Configuration
public class MockConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockConfiguration.class);

    /**
     * mock 配置文件路径前缀
     */
    public static String MOCK_CONFIG_PATH_PREFIX;
    /**
     * 多模块下的模块
     */
    public static String MOCK_CONFIG_MODULE_NAME = null;

    public static List<String> ALL_MOCK_CLASS_METHOD_NAME;

    static {
        MOCK_CONFIG_PATH_PREFIX = System.getProperty("user.dir");

        if (MOCK_CONFIG_MODULE_NAME != null) {
            MOCK_CONFIG_PATH_PREFIX += (File.separator + MOCK_CONFIG_MODULE_NAME);
        }

        MOCK_CONFIG_PATH_PREFIX += (File.separator + "src");
        MOCK_CONFIG_PATH_PREFIX += (File.separator + "main");
        MOCK_CONFIG_PATH_PREFIX += (File.separator + "resources");
        MOCK_CONFIG_PATH_PREFIX += (File.separator + "mock_config");

        LOGGER.info("MOCK_CONFIG_PATH_PREFIX:" + MOCK_CONFIG_PATH_PREFIX);

        File file = new File(MOCK_CONFIG_PATH_PREFIX);
        File[] childFiles = file.listFiles();
        if (Objects.nonNull(childFiles)) {
            ALL_MOCK_CLASS_METHOD_NAME = Stream.of(childFiles).map(File::getName).collect(Collectors.toList());
            LOGGER.info("ALL_MOCK_CLASS_METHOD_NAME:" + ALL_MOCK_CLASS_METHOD_NAME);
        } else {
            ALL_MOCK_CLASS_METHOD_NAME = new ArrayList<>();
            LOGGER.warn("mock_config 文件夹下没有配置文件");
        }
    }

    @Component
    public static class MockBeanPostProcessor implements BeanPostProcessor {

        private static final Logger LOGGER = LoggerFactory.getLogger(MockBeanPostProcessor.class);

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            for (String mockClassMethodName : ALL_MOCK_CLASS_METHOD_NAME) {
                String[] split = mockClassMethodName.split("#");
                String className = split[0];
                String methodName = split[1].replace(".json", "");

                // Mock 住了这个 Bean
                if (bean.getClass().getName().equals(className)) {
                    LOGGER.info("[MockBeanPostProcessor][after][{}]", mockClassMethodName);

                    Enhancer enhancer = new Enhancer();
                    enhancer.setSuperclass(bean.getClass());
                    enhancer.setUseCache(false);
                    enhancer.setCallback(new MethodInterceptor() {
                        @Override
                        public Object intercept(Object obj, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
                            if (method.getName().equals(methodName)) {
                                // 代理方法
                                String resultJson = classPathReadUtil.readMockConfig(mockClassMethodName);
                                LOGGER.info("[resultJson][{}]", resultJson);

                                Class<?> resultType = ResolvableType.forMethodReturnType(method).resolve();
                                Object result;
                                if (List.class.equals(resultType)) {
                                    Class<?> listItemClass = ResolvableType.forMethodReturnType(method).getGeneric(0).getRawClass();
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    TypeFactory typeFactory = objectMapper.getTypeFactory();
                                    CollectionType collectionType = typeFactory.constructCollectionType(List.class, listItemClass);
                                    result = objectMapper.readValue(resultJson, collectionType);
                                } else if (Map.class.equals(resultType)) {
                                    Class<?> mapKeyClass = ResolvableType.forMethodReturnType(method).getGeneric(0).getRawClass();
                                    Class<?> mapValueClass = ResolvableType.forMethodReturnType(method).getGeneric(1).getRawClass();
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    TypeFactory typeFactory = objectMapper.getTypeFactory();
                                    MapType mapType = typeFactory.constructMapType(Map.class, mapKeyClass, mapValueClass);
                                    result = objectMapper.readValue(resultJson, mapType);
                                } else if (Set.class.equals(resultType)) {
                                    Class<?> setItemClass = ResolvableType.forMethodReturnType(method).getGeneric(0).getRawClass();
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    TypeFactory typeFactory = objectMapper.getTypeFactory();
                                    CollectionType collectionType = typeFactory.constructCollectionType(Set.class, setItemClass);
                                    result = objectMapper.readValue(resultJson, collectionType);
                                } else {
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    result = objectMapper.readValue(resultJson, resultType);
                                }

                                return result;
                            } else {
                                return methodProxy.invokeSuper(obj, params);
                            }
                        }
                    });

                    Constructor<?> constructor = bean.getClass().getConstructors()[0];
                    Class<?>[] parameterTypes = constructor.getParameterTypes();
                    return enhancer.create(parameterTypes, new Object[0]);
                }
            }
            return bean;

        }
    }

    public static class classPathReadUtil {
        private static final Logger LOGGER = LoggerFactory.getLogger(classPathReadUtil.class);

        public static String readMockConfig(String fileName) {
            String filePath = MOCK_CONFIG_PATH_PREFIX + File.separator + fileName;
            try {
                FileInputStream fileInputStream = new FileInputStream(filePath);
                return new BufferedReader(
                        new InputStreamReader(fileInputStream))
                        .lines()
                        .collect(Collectors.joining(System.lineSeparator())).trim();
            } catch (Exception e) {
                LOGGER.error("readMockConfig error", e);
            }
            return "";
        }
    }


}