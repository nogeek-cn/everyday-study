package com.darian.config.enums;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/10/23  下午12:25
 */
public enum DDDViewLevel {

    NO("NO", "不进行控制"),
    VIEW("VIEW", "仅仅是查看"),
    CONTROL("CONTROL", "控制启动，不合法不能启动"),
    ;

    private String code;
    private String desc;

    DDDViewLevel(String code, String desc) {
        this.code = code;
    }
}
