//package com.darian.context;
//
//import com.darian.config.annotation.BizConfig;
//import com.darian.config.config.EnableDDDView;
//import com.darian.config.spring.beans.factory.InjectManageBeanPostProcessor;
//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.beans.factory.support.AbstractBeanDefinition;
//import org.springframework.beans.factory.support.BeanDefinitionBuilder;
//import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
//import org.springframework.core.annotation.AnnotationAttributes;
//import org.springframework.core.type.AnnotationMetadata;
//import org.springframework.util.ClassUtils;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.LinkedHashSet;
//import java.util.Set;
//
//import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;
//
///***
// *
// *
// * @author <a href="mailto:">notOnlyGeek</a>
// * @date 2024/10/23  下午12:38
// */
//public class DDDViewScanRegistrar implements ImportBeanDefinitionRegistrar {
//    @Override
//    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
//        Set<String> packagesToScan = getPackagesToScan(importingClassMetadata);
//
//        BeanDefinitionBuilder builder = rootBeanDefinition(InjectManageBeanPostProcessor.class);
//        builder.addConstructorArgValue(packagesToScan);
//        builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
//        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
//        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);
//
//    }
//
//
//    private Set<String> getPackagesToScan(AnnotationMetadata metadata) {
//        //  todo: 扫描对应的 目录
////        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
////                metadata.getAnnotationAttributes(DubboComponentScan.class.getName()));
//        // TODO: 需要修改
//        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
//                metadata.getAnnotationAttributes(EnableDDDView.class.getName()));
//        String[] basePackages = attributes.getStringArray("basePackages");
//        Class<?>[] basePackageClasses = attributes.getClassArray("basePackageClasses");
//        String[] value = attributes.getStringArray("value");
//        // Appends value array attributes
//        Set<String> packagesToScan = new LinkedHashSet<String>(Arrays.asList(value));
//        packagesToScan.addAll(Arrays.asList(basePackages));
//        for (Class<?> basePackageClass : basePackageClasses) {
//            packagesToScan.add(ClassUtils.getPackageName(basePackageClass));
//        }
//        if (packagesToScan.isEmpty()) {
//            return Collections.singleton(ClassUtils.getPackageName(metadata.getClassName()));
//        }
//        return packagesToScan;
//    }
//}
