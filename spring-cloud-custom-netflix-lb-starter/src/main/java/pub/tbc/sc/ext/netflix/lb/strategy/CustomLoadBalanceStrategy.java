package pub.tbc.sc.ext.netflix.lb.strategy;

import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * @Author tbc by 2021/12/31
 */
public interface CustomLoadBalanceStrategy {

    Server choose(String routeKey, List<Server> allServers);

}
