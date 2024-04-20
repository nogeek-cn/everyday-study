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
 * @date 2024/4/20  上午10:04
 */
public class PojoVelocityDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(VelocityDemo.class);

    private static VelocityEngine VELOCITY_ENGINE = new VelocityEngine();

    public static void main(String[] args) {
        String template = "Hello $user.name, welcome to $you.name ’s $you.company. "
                + " $user.name and $you.name and $you.brother.name is a good friends. ";
        Map<String, Object> params = new HashMap();

        params.put("user", new User("Tom"));
        params.put("you", new You("jerry", "not Only geek club", new User("(jerry-brother-name)")));

        String result = formatTemplateVelocity(template, params);
        System.out.println(result); // Hello Tom, welcome to jerry ’s not Only geek club.  Tom and jerry and (jerry-brother-name) is a good friends.
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

    public static class User {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public User(String name) {
            this.name = name;
        }
    }

    public static class You {
        private String name;
        private String company;
        private User brother;


        public User getBrother() {
            return brother;
        }

        public void setBrother(User brother) {
            this.brother = brother;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public You(String name, String company, User brother) {
            this.name = name;
            this.company = company;
            this.brother = brother;
        }
    }
}


