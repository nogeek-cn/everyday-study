package com.darian.velocity;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/4/20  下午7:04
 */
public class IFVelocityDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(VelocityDemo.class);

    private static VelocityEngine VELOCITY_ENGINE = new VelocityEngine();

    public static void main(String[] args) {
        String template = "Hello #if($user.name=='Tom') [in] $user.name [in] #end, $user.name,  welcome to $you.name ’s $you.company. "
                + " $user.name and $you.name and $you.brother.name is a good friends. ";
        Map<String, String> user = new HashMap<>();
        user.put("name", "Tom");

        Map<String, Object> you = new HashMap<>();
        you.put("name", "jerry");
        you.put("company", "not Only geek club");
        Map<String, String> youBrother = new HashMap<>();
        youBrother.put("name", "(jerry-brother-name)");
        you.put("brother", youBrother);

        Map<String, Object> params = new HashMap();
        params.put("user", user);
        params.put("you", you);
        String result = formatTemplateVelocity(template, params);
        System.out.println(result); // Hello  [in] Tom [in] , Tom,  welcome to jerry ’s not Only geek club.  Tom and jerry and (jerry-brother-name) is a good friends.
        System.out.println();

        template = "Hello #if($user.name=='TomBBBB') [in] $user.name [in] #end, $user.name,  welcome to $you.name ’s $you.company. "
                + " $user.name and $you.name and $you.brother.name is a good friends. ";
        result = formatTemplateVelocity(template, params);
        System.out.println(result); // Hello , Tom,  welcome to jerry ’s not Only geek club.  Tom and jerry and (jerry-brother-name) is a good friends.
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
