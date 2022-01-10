package pub.tbc.sc.ext.demo;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.tbc.sc.ext.netflix.lb.CustomRouterKey;
import pub.tbc.sc.ext.netflix.lb.LoadBalanceUtil;
import pub.tbc.sc.ext.netflix.lb.log.LoggerWrapper;
import pub.tbc.sc.ext.netflix.lb.strategy.CustomLoadBalanceStrategy;
import pub.tbc.sc.ext.netflix.lb.strategy.CustomLoadBalanceStrategyEnum;

import java.util.Map;

/**
 * @author tbc by 2022/1/10 11:07
 */
@RequiredArgsConstructor
public class DemoFeignClientWrapper implements DemoFeignClient {
    private Logger log = new LoggerWrapper(LoggerFactory.getLogger(getClass()));
    private final DemoFeignClient target;

    @Override
    public Object doSomeThing(Map<String, Object> param) {
        // 路由键，将做为负载策略依据
        String routeKeyStr = getRouteKey(param);
        // 负载策略，此处为示例策略，按业务需求自行实现自己的策略
        CustomLoadBalanceStrategy strategy = CustomLoadBalanceStrategyEnum.MODULUS;
        CustomRouterKey routeKey = CustomRouterKey.of(strategy, routeKeyStr);
        log.info("do something in wrapper, routeKey={}, strategy={}", routeKeyStr, strategy);
        return LoadBalanceUtil.routeRun(routeKey, () -> target.doSomeThing(param));
    }

    /**
     * 示例，自行定义自己的路由键，比如按用户、按地区等等
     */
    private String getRouteKey(Map<String, Object> param) {
        return param.get("routeKey").toString();
    }
}
