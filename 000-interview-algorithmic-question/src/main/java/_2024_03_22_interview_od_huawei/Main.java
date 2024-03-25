// import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Scanner;
//import java.util.stream.Collectors;
//
//// 注意类名必须为 Main, 不要有任何 package xxx 信息
//public class Main {
//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        // 注意 hasNext 和 hasNextLine 的区别
//
//        List<String> strList = new ArrayList<>();
//        while (in.hasNextLine()) {
//            String next = in.nextLine();
//            strList.add(next);
//        }
//
//        long count = Arrays.stream(strList.stream()
//                        .map(str -> {
//                            if (str.contains("-- ")) {
//                                str = str.split("-- ")[0];
//                            }
//                            return str;
//                        })
//                        .collect(Collectors.joining(","))
//                        .split(";"))
//                .filter(str -> {
//                    return !"".equals(str);
//                })
//                .filter(str -> {
//                    return !" ".equals(str);
//                })
//                .filter(str -> {
//                    return !"\t".equals(str);
//                })
//                .count();
//        ;
//        System.out.println(count);
//    }
//}
package _2024_03_22_interview_od_huawei;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别

        List<String> strList = new ArrayList<>();
//        while (in.hasNextLine()) {
//            String next = in.nextLine();
//            strList.add(next);
//        }

        strList.add("COMMAND TABLE IF EXISTS \"UNITED ; --  STATE\";     \n");
        long count = Arrays.stream(strList.stream()
                        .map(str -> {
                            if (str.contains("-- ")) {
                                if (str.contains("\"")) {
                                    String start = str.substring(0, str.indexOf("\""));
                                    String end = str.substring(str.lastIndexOf("\""), str.length());
                                    str = start + " a "+end;
                                }
                                if (str.contains("'")) {
                                    String start = str.substring(0, str.indexOf("'"));
                                    String end = str.substring(str.lastIndexOf("'"), str.length());
                                    str = start + " a " + end;
                                }
                                str = str.split("-- ")[0];
                            }
                            return str;
                        })
                        .collect(Collectors.joining(","))
                        .split(";"))
                .map(str -> {
                    str = str.replace("\t", "");
                    str = str.replace(" ", "");
                    str = str.replace("\n", "");
                    return str;
                })
                .filter(str -> {
                    return !"".equals(str);
                })
                .filter(str -> {
                    return !" ".equals(str);
                })
                .filter(str -> {
                    return !"\t".equals(str);
                })
                .count();
        ;
        System.out.println(count);
    }
}