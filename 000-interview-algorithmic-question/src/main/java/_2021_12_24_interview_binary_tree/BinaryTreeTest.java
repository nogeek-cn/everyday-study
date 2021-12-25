package _2021_12_24_interview_binary_tree;

import java.util.Objects;
import java.util.Scanner;

/***
 *
 * @author darian1996
 */
public class BinaryTreeTest {


    private static class Node {
        Node left, right;
        int data;

        Node(int newData) {
            left = right = null;
            data = newData;
        }
    }

    private static Node insert(Node node, int data) {
        if (node == null) {
            node = new Node(data);
        } else {
            if (data <= node.data) {
                // 插入到左边
                node.left = insert(node.left, data);
            } else {
                // 插入到右边
                node.right = insert(node.right, data);
            }
        }
        return (node);
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        Node _root;
        int root_i = 0, root_cnt = 0, root_num = 0;
        // 先输入，二叉树里有几个数字
        root_cnt = in.nextInt();
        _root = null;
        for (root_i = 0; root_i < root_cnt; root_i++) {


            // 输入一个数，插入到二叉树中
            root_num = in.nextInt();
            if (root_i == 0) {
                _root = new Node(root_num);
            } else {
                insert(_root, root_num);
            }
        }

        int q = in.nextInt();

        for (int i = 0; i < q; i++) {
            int _x = in.nextInt();
            System.out.println(isPresent(_root, _x));
        }

        return;
    }


    private static int isPresent(Node root, int val) {
        if (Objects.isNull(root)) {
            return 0;
        }
        if (root.data == val) {
            return 1;
        } else if (root.data > val) {
            return isPresent(root.left, val);
        } else {
            return isPresent(root.right, val);
        }
    }
}
