package com.darian;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/***
 * https://dwz.tax/oCCD
 * https://qrpay.uomg.com/qr.html?ali=https%3A%2F%2Fqr.alipay.com%2Ffkx11494qoi1x6zi2xlzc87&vx=wxp%3A%2F%2Ff2f0Lfgk1-N0AMFx7RqkhjmJc7KeKxO99KA5842Ah6XMv6c&qq=https%3A%2F%2Fi.qianbao.qq.com%2Fwallet%2Fsqrcode.htm%3Fm%3Dtenpay&uin=1934849492
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2023/8/3  16:09
 */
public class PayQRCodeThree {

    static Map<String, String> PARAM_MAP;

    static {
        PARAM_MAP = new HashMap<>();
        // 	支付宝收款地址，url编码后传递
        PARAM_MAP.put("alipay", "https://qr.alipay.com/fkx11494qoi1x6zi2xlzc87");
        // 	微信收款地址，url编码后传递
        PARAM_MAP.put("vxpay", "wxp://f2f0Lfgk1-N0AMFx7RqkhjmJc7KeKxO99KA5842Ah6XMv6c");
        // QQ收款地址，url编码后传递
        PARAM_MAP.put("qqpay", "https://i.qianbao.qq.com/wallet/sqrcode.htm?m=tenpay&f=wallet&a=1&ac=CAEQ1OvNmgcY982tpgZCIDYwZDQ3MzdiOTY2OGM3ZmNmZjEyMWI5ODFmNWM0Yzli_xxx_sign&u=1934849492&n=%E6%97%A0%E5%89%91%7E");

        // 收款QQ号，作为头像，商户名依据
        PARAM_MAP.put("uin", "1934849492");
        // 选择输出格式[json|jpg]
        PARAM_MAP.put("format", "json");
        // 二维码背景颜色例如:ffffff
        PARAM_MAP.put("bgColor", "ffffff");
        // 二维码颜色例如:999999
        PARAM_MAP.put("Color", "999999");
        // 范围：0 - 127；0 完全不透明
        PARAM_MAP.put("bgalpha", "0");
        // 范围：0 - 127；0 完全不透明
        PARAM_MAP.put("alpha", "0");
    }

    public static void main(String[] args) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        String URL = "https://api.uomg.com/api/qrcode.pay?";

        String paramString = PARAM_MAP.entrySet().stream()
                .map(entry -> {
                    return entry.getKey() + "=" + entry.getValue();
                }).collect(Collectors.joining("&", "?", ""));

        URL = URL + paramString;
        HttpHeaders httpHeaders = restTemplate.headForHeaders(URL);
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediaTypeList);
        String responseJson = restTemplate.getForObject(URL, String.class);
        System.out.println(responseJson);
    }
}
