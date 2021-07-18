package com.darian.dubbo.module;

import java.io.Serializable;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/7/18  下午3:02
 */
public class DubboTestRequest implements Serializable {

    private String name;

    public DubboTestRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
