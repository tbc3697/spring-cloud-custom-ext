package pub.tbc.sc.ext.netflix.lb;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import pub.tbc.sc.ext.netflix.lb.log.LoggerWrapper;

import java.util.List;

/**
 * 自定义IRule
 *
 * @Author tbc by 2022/1/1 0:19
 */
public class CustomZoneAvoidanceRule extends ZoneAvoidanceRule {
    private LoggerWrapper log = new LoggerWrapper(LoggerFactory.getLogger(getClass()));

    public CustomZoneAvoidanceRule() {
        super();
        log.info("实例化 pub.tbc.sc.ext.netflix.lb.CustomZoneAvoidanceRule");
    }

    @Override
    public Server choose(Object key) {
        ILoadBalancer lb = super.getLoadBalancer();
        List<Server> allServers = lb.getAllServers();
        if (CollectionUtils.isEmpty(allServers)) {
            return super.choose(key);
        }
        CustomRouterKey routeKey = LoadBalanceUtil.getRouteKey();
        if (routeKey == null) {
            return super.choose(key);
        }
        Server server = routeKey.choose(allServers);
        return server == null ? super.choose(key) : server;
    }

}
