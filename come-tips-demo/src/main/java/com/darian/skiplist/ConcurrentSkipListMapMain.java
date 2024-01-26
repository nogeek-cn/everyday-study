package com.darian.skiplist;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListMap;

import static com.darian.skiplist.SkipUtils.getName;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2024/1/25  11:04
 */
public class ConcurrentSkipListMapMain {

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, SkipUtils.SetObject> concurrentSkipListMap = new ConcurrentSkipListMap<>();
        addObj(concurrentSkipListMap);
        addObj(concurrentSkipListMap);
        addObj(concurrentSkipListMap);
        addObj(concurrentSkipListMap);



        for (Map.Entry<Integer, SkipUtils.SetObject> stringSetObjectEntry : concurrentSkipListMap.entrySet()) {
            System.out.println(stringSetObjectEntry);
        }
    }

    private static void addObj(ConcurrentSkipListMap<Integer, SkipUtils.SetObject> concurrentSkipListMap) {
        SkipUtils.SetObject setObject = new SkipUtils.SetObject(getName(4), SkipUtils.getPrice(), SkipUtils.getTimeStamp());
        concurrentSkipListMap.put(setObject.getPrice(), setObject);

//        setObject.setName(SkipUtils.getName(4));
//        concurrentSkipListMap.put(setObject.getPrice(), setObject);

    }
}
