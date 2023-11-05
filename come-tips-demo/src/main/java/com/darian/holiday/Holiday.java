package com.darian.holiday;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.darian.ClassPathFileReadUtils;
import com.darian.DateUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.util.*;
import java.util.function.BiConsumer;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2023/11/1  17:44
 */
public class Holiday {

    public static void main(String[] args) throws Exception {
        JSONObject jsonObject = JSON.parseObject(ClassPathFileReadUtils.readClassPathResource("static" +
                File.separator + "domain" +
                File.separator + "holiday" +
                File.separator + "holiday_gov.json"

        ));


        Map<String, Integer> result = new LinkedHashMap<>();

        Date first = DateUtils.parse("20240101", "yyyyMMdd");

        for (int i = 0; i < 366; i++) {
            Date thisDate = DateUtils.plusDays(first, i);
            String thisDateFormat = DateUtils.format(thisDate, "yyyyMMdd");

            Integer holidayTag = jsonObject.getInteger(thisDateFormat);
            if (Objects.nonNull(holidayTag)) {
                result.put(thisDateFormat, holidayTag);
            } else {
                LocalDateTime dateTime = DateUtils.toDateTime(thisDate);
                String dayOfWeekName = dateTime.getDayOfWeek().name();
                if (dayOfWeekName.equals("SATURDAY") || dayOfWeekName.equals("SUNDAY")) {
                    result.put(thisDateFormat, 1);
                } else {
                    result.put(thisDateFormat, 0);
                }
            }
        }


        result.forEach(new BiConsumer<String, Integer>() {
            @Override
            public void accept(String dateFormat, Integer integer) {
                try {
                    String out =
                            System.getProperty("user.dir") + File.separator
                                    + "src" + File.separator
                                    + "main" + File.separator
                                    + "resources" + File.separator
                                    + "static" + File.separator
                                    + "domain" + File.separator
                                    + "holiday" + File.separator
                                    + dateFormat + ".txt";
                    File ifile = new java.io.File(out);
                    FileOutputStream fop = new FileOutputStream(ifile);
                    fop.write(String.valueOf(integer).getBytes());
                    fop.flush();
                    fop.close();
                } catch (Exception e) {

                }

            }
        });


        String holidayOut =
                System.getProperty("user.dir") + File.separator
                        + "src" + File.separator
                        + "main" + File.separator
                        + "resources" + File.separator
                        + "static" + File.separator
                        + "domain" + File.separator
                        + "holiday" + File.separator
                        + "holiday.json";
        File ifile = new java.io.File(holidayOut);
        FileOutputStream fop = new FileOutputStream(ifile);
        fop.write(JSON.toJSONString(result).getBytes());
        fop.flush();
        fop.close();

    }
}
