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
 * @date 2024/4/20  上午12:34
 */
public class SuperVelocityDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(VelocityDemo.class);

    private static VelocityEngine VELOCITY_ENGINE = new VelocityEngine();

    public static void main(String[] args) {
        String template = "Hello $user.name, welcome to $you.name ’s $you.company. "
                + " $user.name and $you.name is a good friends. ";
        Map<String, Object> params = new HashMap();
        HashMap<Object, Object> user = new HashMap<>();
        user.put("name", "Tom");

        HashMap<Object, Object> you = new HashMap<>();
        you.put("name", "jerry");
        you.put("company", "not Only geek club");

        params.put("user", user);
        params.put("you", you);

        String result = formatTemplateVelocity(template, params);
        System.out.println(result); // Hello Tom, welcome to jerry ’s not Only geek club.  Tom and jerry is a good friends.
    }

    public static String formatTemplateVelocity(
            String template, Map<String, Object> params) {
        if (template == null || template.isEmpty()) {
            return template;
        }
        VelocityContext context = new VelocityContext();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
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