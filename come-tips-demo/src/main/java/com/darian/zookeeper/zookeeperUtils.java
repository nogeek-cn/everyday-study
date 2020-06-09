
package com.darian.zookeeper;

import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.List;

public class zookeeperUtils {
    public static void main(String[] args) throws Exception {
        getDataTest();
    }

    public static void getDataTest() throws Exception {
        String hostPort = "zookeeper.darian.com:2181";
        String zpath = "/";
        List<String> zooChildren = new ArrayList<String>();
        ZooKeeper zk = new ZooKeeper(hostPort, 2000, null);
        if (zk != null) {

            zooChildren = zk.getChildren(zpath, false);
            System.err.println("/");
            getChildRen(zk, zpath, 1);

        }
    }

    public static void getChildRen(ZooKeeper zk, String zpath, int deep) throws Exception {
        List<String> zooChildren = zk.getChildren(zpath, false);

        for (String child : zooChildren) {
            for (int i = 0; i < deep; i++) {
                System.err.print("  ");
            }
            System.err.print("|-");
            System.err.println(child);
            getChildRen(zk, zpath.endsWith("/") ? zpath + child : zpath + "/" + child, deep + 1);
        }
    }
}