
## 1 题目：

```java

import java.lang.reflect.Proxy;

interface IA {
    String getHelloName();
}

public class ShowMeBug_1 {
    public static void main(String[] arges) throws Exception {
        IA ia = (IA) createObject(IA.class.getName() + "$getHelloName=Abc");
        System.out.println(ia.getHelloName()); //方法名匹配的时候，输出“Abc”
        ia = (IA) createObject(IA.class.getName() + "$getTest=Bcd");
        System.out.println(ia.getHelloName()); //方法名不匹配的时候，输出null
    }

    //请实现方法createObject，接口中"getName()"方法名仅仅是个示例，不能写死判断

    public static Object createObject(String str) throws Exception {
        throw new Exception("还没有实现的方法");
    }
}


```


## 题目二

```java
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
public class ShowMeBug_2 {
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
        // 可以写函数
    }
    
}



```