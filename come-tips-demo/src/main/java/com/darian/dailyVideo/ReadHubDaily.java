package com.darian.dailyVideo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2023/10/26  15:06
 */
public class ReadHubDaily {
    private static RestTemplate REST_TEMPLATE = new RestTemplate();

    private static Logger LOGGER = LoggerFactory.getLogger(ReadHubDaily.class);

    public static void main(String[] args) {
        String readTopicVideoContent = "欢迎观看每日早报。";


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("User-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.82 Safari/537.36");
        httpHeaders.add("sec-ch-ua", "\"Google Chrome\";v=\"107\", \"Chromium\";v=\"107\", \"Not=A?Brand\";v=\"24\"");
        // 添加缓存头
        httpHeaders.add("cache-control", "no-cache");
        httpHeaders.add("pragma", "no-cache");
        httpHeaders.add("accept-language", "zh-CN,zh;q=0.9");

//        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("https://readhub.cn/daily");
        HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<MultiValueMap<String, Object>>(httpHeaders);
        //封装请求头
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        formEntity = new HttpEntity<MultiValueMap<String, Object>>(params, httpHeaders);
        ResponseEntity<String> htmlBodyResponse = REST_TEMPLATE.exchange(
                "https://readhub.cn/daily", HttpMethod.GET, formEntity, String.class);

        String dailyAllResponse = htmlBodyResponse.getBody();

        String dailyDate = dailyAllResponse.split("<div class=\"Daily_date")[1].split("\">")[1].split("</div>")[0];
        System.out.println(dailyDate);

        String[] dailyDateSpilt = dailyDate.split("\\.");
        readTopicVideoContent = readTopicVideoContent + "今天是" +  dailyDateSpilt[0] + "年";
        readTopicVideoContent = readTopicVideoContent + dailyDateSpilt[1] + "月";
        readTopicVideoContent = readTopicVideoContent + dailyDateSpilt[2] + "日。";


        String[] thisDayUUidSpilt = dailyAllResponse.split("<script src=\"");

        String thisDayUUId = Arrays.asList(thisDayUUidSpilt)
                .stream()
                .filter(str -> str.contains("_buildManifest.js"))
                .map(str -> {
                    str = str.replace("/_buildManifest.js\" defer=\"\"></script>", "");
                    str = str.replace("https://cdn.readhub.cn/_next/static/", "");
                    return str;
                })
                .findAny()
                .get();

        System.out.println("thisDayUUId: " + thisDayUUId);

        htmlBodyResponse = REST_TEMPLATE.exchange(
                "https://readhub.cn/_next/data/" + thisDayUUId + "/daily.json",
                HttpMethod.GET, formEntity, String.class);

        String dailyJson = htmlBodyResponse.getBody();
        JSONObject jsonObject = JSONObject.parseObject(dailyJson);
        JSONArray jsonArray = jsonObject.getJSONObject("pageProps").getJSONArray("daily");

        for (int i = 0; i < jsonArray.size(); i++) {
            Object innerTopic = jsonArray.get(i);

            JSONObject topic = JSONObject.parseObject(JSON.toJSONString(innerTopic));
            String uid = topic.getString("uid");
            System.out.println(uid);

            htmlBodyResponse = REST_TEMPLATE.exchange(
                    "https://readhub.cn/topic/" + uid,
                    HttpMethod.GET,
                    formEntity,
                    String.class);

            String topicHtml = htmlBodyResponse.getBody();

            String title = topicHtml.split("<title>")[1].split("</title>")[0];
            System.out.println("第 " + (i + 1) + " 条新闻 : " + title);

            String content = topicHtml.split("<meta name=\"description\" content=\"")[1]
                    .split("\"/><meta name=\"keywords\" content=\"")[0];
            System.out.println(content);


            readTopicVideoContent = readTopicVideoContent + "\n"
                    + "第 " + i + " 条新闻 : " + title + "。\n"
                    + content;
        }

        System.out.println();
        System.out.println();

        System.out.println(readTopicVideoContent);

    }
}
