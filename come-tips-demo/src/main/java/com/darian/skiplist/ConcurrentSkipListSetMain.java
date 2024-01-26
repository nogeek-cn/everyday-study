package com.darian.skiplist;


import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListSet;


import static com.darian.skiplist.SkipUtils.getName;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2024/1/25  11:03
 */
public class ConcurrentSkipListSetMain {

    public static void main(String[] args) {
        int count = 10;
        ConcurrentSkipListSet<SkipUtils.SetObject> concurrentSkipListSetAsc = new ConcurrentSkipListSet<SkipUtils.SetObject>(SkipUtils.COMPARATOR_ASC);
        for (int i = 0; i < count; i++) {
            concurrentSkipListSetAsc.add(getObj(false));
        }
        ConcurrentSkipListSet<SkipUtils.SetObject> concurrentSkipListSetDesc = new ConcurrentSkipListSet<SkipUtils.SetObject>(SkipUtils.COMPARATOR_DESC);
        for (int i = 0; i < count; i++) {
            concurrentSkipListSetDesc.add(getObj(true));
        }


        System.out.println("\n  asc 卖 \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t desc 买 \n");
        for (int i = 0; i < count; i++) {
            SkipUtils.SetObject ascFirst = concurrentSkipListSetAsc.pollFirst();
            SkipUtils.SetObject descFirst = concurrentSkipListSetDesc.pollFirst();
            System.out.println(ascFirst
                    + "\t\t"
                    + descFirst
                    + "\t\t"
                    + (((Objects.nonNull(descFirst) && Objects.nonNull(ascFirst) && descFirst.getPrice() >= ascFirst.getPrice())) ? "成交" : "-")
            );
        }

        System.out.println();
    }

    public static SkipUtils.SetObject getObj(boolean high) {
        SkipUtils.SetObject setObject = new SkipUtils.SetObject(getName(4), SkipUtils.getLowPrice(), SkipUtils.getTimeStamp());
        if (high) {
            setObject.setPrice(SkipUtils.getHighPrice());
        }
        return setObject;
    }


}
