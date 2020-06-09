
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
            getChildRen(zk, zpath);

        }
    }

    public static void getChildRen(ZooKeeper zk, String zpath) throws Exception {
        List<String> zooChildren = zk.getChildren(zpath, false);

        for (String child : zooChildren) {
            System.out.println("[path]{" + zpath + "}  [child]{" + child + "}");
            getChildRen(zk, zpath.endsWith("/") ? zpath + child : zpath + "/" + child);
        }
    }
}