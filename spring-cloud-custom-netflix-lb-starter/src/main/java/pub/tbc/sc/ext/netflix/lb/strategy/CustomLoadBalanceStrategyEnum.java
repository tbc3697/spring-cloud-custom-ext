package pub.tbc.sc.ext.netflix.lb.strategy;

import com.netflix.loadbalancer.Server;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * @Author tbc by 2021/12/31
 */
@AllArgsConstructor
public enum CustomLoadBalanceStrategyEnum implements CustomLoadBalanceStrategy {
    MODULUS(new CustomHashingStrategy());

    private CustomLoadBalanceStrategy strategy;

    @Override
    public Server choose(String routeKey, List<Server> allServers) {
        return strategy.choose(routeKey, allServers);
    }

}
