package com.darian.velocity;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/4/20  上午12:27
 */
public class VelocityDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(VelocityDemo.class);

    private static VelocityEngine VELOCITY_ENGINE = new VelocityEngine();

    public static void main(String[] args) {
        String template = "Hello $name, welcome to $company.";
        Map<String, String> params = new HashMap<>();
        params.put("name", "John");
        params.put("company", "Velocity");
        params.put("redundant", "redundant"); // 多余的字段没有效果
        String result = formatTemplateVelocity(template, params);
        System.out.println(result); // output: Hello John, welcome to Velocity.
    }

    public static String formatTemplateVelocity(
            String template, Map<String, String> params) {
        if (template == null || template.isEmpty()) {
            return template;
        }
        VelocityContext context = new VelocityContext();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            context.put(entry.getKey(), entry.getValue());
        }
        StringWriter writer = new StringWriter();
        boolean evaluate = VELOCITY_ENGINE.evaluate(context, writer, template, template);
        if (!evaluate) {
            LOGGER.error("VelocityEngine evaluate error: {}", template);
        } else {
            return writer.toString();
        }
        return template;
    }
}
