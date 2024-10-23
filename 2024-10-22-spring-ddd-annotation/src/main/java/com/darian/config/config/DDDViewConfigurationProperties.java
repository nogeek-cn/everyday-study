package com.darian.config.config;

import com.darian.config.annotation.BizConfig;
import com.darian.config.enums.DDDViewLevel;
import lombok.Data;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/10/23  下午12:21
 */
@Data
@BizConfig
@ConfigurationProperties(prefix = "ddd.view")
public class DDDViewConfigurationProperties implements InitializingBean {

    private DDDViewLevel level;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (level == null) {
            throw new BeanCreationException("ddd.view.level must be not null");
        }
    }
}
