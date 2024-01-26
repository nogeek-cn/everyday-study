package com.darian.skiplist;

import com.darian.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2024/1/25  14:35
 */
public class SkipUtils {

    public static Integer getHighPrice() {
        return ThreadLocalRandom.current().nextInt(1, 8);
    }

    public static Integer getLowPrice() {
        return ThreadLocalRandom.current().nextInt(3, 10);
    }
    public static Integer getPrice() {
        return ThreadLocalRandom.current().nextInt(1, 10);
    }

    public static Long getTimeStamp() {
        return ThreadLocalRandom.current().nextInt(-100000, 100000) + System.currentTimeMillis();
    }

    public static String getName(int forI) {
        char[] chars = new char[]{
                'a', 'b', 'c', 'd', 'e', 'f', 'g',
                'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G',
                'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z'
        };
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < forI; i++) {
            result.append(chars[ThreadLocalRandom.current().nextInt(chars.length)]);
        }
        return result.toString();
    }


    public static Comparator<SetObject> COMPARATOR_ASC = (o1, o2) -> {
        int ageCompare = o1.price.compareTo(o2.price);
        if (ageCompare != 0) {
            return ageCompare;
        } else {
            int timestampCompare = o1.timestamp.compareTo(o2.timestamp);
            if (timestampCompare != 0) {
                return timestampCompare;
            } else {
                return o1.name.compareTo(o2.name);
            }
        }
    };
    public static Comparator<SetObject> COMPARATOR_DESC = (o1, o2) -> {
        int ageCompare = o2.price.compareTo(o1.price);
        if (ageCompare != 0) {
            return ageCompare;
        } else {
            int timestampCompare = o1.timestamp.compareTo(o2.timestamp);
            if (timestampCompare != 0) {
                return timestampCompare;
            } else {
                return o2.name.compareTo(o1.name);
            }
        }
    };

    @Data
    @AllArgsConstructor
    public static class SetObject {
        private String name;

        private Integer price;

        private Long timestamp;

        @Override
        public boolean equals(Object o) {
            return false;
        }


        @Override
        public int hashCode() {
            return Objects.hash(name, price);
        }


        @Override
        public String toString() {
            return "SetObject{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    ", timestamp=" + DateUtils.format(new Date(timestamp), DateUtils.PATTERN_DATETIME) +
                    '}';
        }
    }
}
