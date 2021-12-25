package com.darian.elasticjob;

import lombok.Data;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.dataflow.job.DataflowJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/***
 * 流式获取
 *
 * https://shardingsphere.apache.org/elasticjob/current/cn/features/elastic/#sharding-%E8%8A%82%E7%82%B9
 * @author darian1996
 */
public class MyDataFlowJob implements DataflowJob<MyDataFlowJob.Order> {

    private Logger LOGGER = LoggerFactory.getLogger(MyDataFlowJob.class);

    //模拟100个未处理订单

    private static List<Order> orders = new ArrayList<>();

    {
        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            order.setOrderId(i);
            order.setStatus(0);
            orders.add(order);
        }
    }


    @Override
    public List<Order> fetchData(ShardingContext shardingContext) {
        //订单号%分片总数==当前分片项
        List<Order> orderList = orders.stream().filter(o -> o.getStatus() == 0)
                .filter(o -> o.getOrderId() % shardingContext.getShardingTotalCount() == shardingContext.getShardingItem())
                .collect(Collectors.toList());

        List<Order> subList = null;
        if (orderList != null && orderList.size() > 0) {
            subList = orderList.subList(0, 10);
        }
        //由于抓取数据过快，为更好看出效果，此处休眠一会儿
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("分片项:{},我抓取的数据:{}", shardingContext.getShardingItem(), subList);
        return subList;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<Order> list) {
        list.forEach(o -> o.setStatus(1));
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("分片项:{},处理中.....", shardingContext.getShardingItem());
    }

    @Data
    public static class Order {
        //订单id
        private Integer orderId;
        //订单状态,0:未处理，1:已处理
        private Integer status;
    }
}