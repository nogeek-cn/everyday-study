package _2021_04_29_interview_reflect_node_list;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//AA
//  BB
//    FF
//    GG
//  CC
//    DD
//      HH
//    EE
//      II
//JJ
//  KK
//  LL
//    MM
//      NN
//        OO

// 必须定义 `ShowMeBug` 入口类和 `public static void main(String[] args)` 入口方法
public class ShowMeBug_2_2 {
    static class Node {
        int id;
        int parentId;
        String name;

        public Node(int id, int parentId, String name) {
            this.id = id;
            this.parentId = parentId;
            this.name = name;
        }
    }

    public static void main(String[] args) {
        List<Node> nodeList = Arrays.asList(
                new Node(1, 0, "AA"),
                new Node(2, 1, "BB"),
                new Node(3, 1, "CC"),
                new Node(4, 3, "DD"),
                new Node(5, 3, "EE"),
                new Node(6, 2, "FF"),
                new Node(7, 2, "GG"),
                new Node(8, 4, "HH"),
                new Node(9, 5, "II"),
                new Node(10, 0, "JJ"),
                new Node(11, 10, "KK"),
                new Node(12, 10, "LL"),
                new Node(13, 12, "MM"),
                new Node(14, 13, "NN"),
                new Node(15, 14, "OO")
        );
        print(nodeList);
    }

    public static void print(List<Node> nodeList) {
        if (nodeList == null || nodeList.size() == 0) {
            return;
        }

        Map<Integer, List<Node>> integerListMap = groupBy(nodeList);

        printNodeMap(integerListMap, 0, 0);

    }

    public static Map<Integer, List<Node>> groupBy(List<Node> nodeList) {
        return nodeList.stream()
                .collect(Collectors.groupingBy(node -> node.parentId));
    }

    public static void printNodeMap(Map<Integer, List<Node>> integerListMap, int parentId, int frame) {
        List<Node> nodes = integerListMap.get(parentId);
        if (nodes == null || nodes.size() == 0) {
            return;
        }
        for (Node node : nodes) {
            printOneNode(node, integerListMap, frame);
        }
    }

    private static void printOneNode(Node node, Map<Integer, List<Node>> integerListMap, int frame) {
        // 缩进
        for (int i = 0; i < frame; i++) {
            System.out.print("  ");
        }
        // name
        System.out.println(node.name);
        printNodeMap(integerListMap, node.id, frame + 1);
    }

}