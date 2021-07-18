package com.darian.dubbo;

import com.darian.dubbo.module.DubboTestRequest;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/7/18  下午3:02
 */
public interface RemoteInterfaceService {

    String test(DubboTestRequest request);
}
