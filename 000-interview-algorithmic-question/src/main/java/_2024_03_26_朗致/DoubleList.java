package _2024_03_26_朗致;


import java.util.Objects;

// 如果一个二叉树的层数为K，且结点总数是(2^k) -1 ，则它就是满二叉树。
public class DoubleList {

    public static void main(String[] args) {
        Node<Integer> parent1 = new Node<>();
        parent1.setValue(1);
        child(parent1, 4);

        Node<Integer> parent2 = new Node<>();
        parent2.setValue(1);
        child(parent2, 4);

        Node<Integer> parent3 = new Node<>();
        parent3.setValue(1);
        child(parent3, 4);

        parent1.setParent(parent2);
        parent2.setParent(parent3);
        parent3.setParent(parent1);

        // 遍历子树，找 top，找 top 的另一边的 子树，再找

        Node<Integer> top = printThis(parent1.getRight().getLeft());
        print(top.getParent());
        print(top.getParent().getParent());
    }

    public static Node<Integer> printThis(Node<Integer> node) {
        if (node.getValue() == 1) {
            print(node);
            return node;
        } else {
            print(node);
            return printOther(node);
        }
    }

    public static Node<Integer> printOther(Node<Integer> node) {
        System.out.println(node.getParent().getValue());
        if (node.getValue() % 2 == 0) {
            print(node.getParent().getRight());
        } else {
            print(node.getParent().getLeft());
        }

        if (node.getParent().getValue() == 1) {
            return node.getParent();
        }

        return printOther(node.getParent());
    }

    public static void print(Node<Integer> parent) {
        if (Objects.isNull(parent)) {
            return;
        }
        System.out.println(parent.getValue());
        print(parent.getLeft());
        print(parent.getRight());
    }


    public static void child(Node<Integer> parent, int k) {
        if (k == 1) {
            return;
        }

        Node<Integer> left = new Node();
        left.setValue(2 * parent.getValue());
        Node<Integer> right = new Node();
        right.setValue(2 * parent.getValue() + 1);
        left.setParent(parent);
        right.setParent(parent);

        parent.setLeft(left);
        parent.setRight(right);

        child(left, k - 1);
        child(right, k - 1);
    }

}

