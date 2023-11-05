package com.darian;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/3/24  9:38 AM
 */
public class ClassPathFileReadUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(ClassPathFileReadUtils.class);

    public static String readClassPathResource(String filepath) {
        try {
            InputStream inputStream = new ClassPathResource(filepath).getInputStream();

            return new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"))
                    .lines()
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (Exception e) {
            LOGGER.error("[ClassPathFileReadUtils.readClassPathResource]", e);
        }
        return "";
    }

}
