一个简单的spring-cloud 扩展，在 spring-cloud-netflix 应用中支持基于自定义的路由键进行负载；

项目提供了一个默认的负载策略，可在此基础上实现自己的负载策略；

***
模块说明：

* spring-cloud-custom-netflix-lb-starter：自定义负载模块
* example-server：示例服务端（todo 待补充）
* example-client：示例客户端（todo 待补充）
* example-server-facade：示例facade，一般由服务提供者提供，在 client 中引入使用

***
使用示例：

1. 在向客户端提供的facade 包中，正常定义Feign接口，且设置 `primary = false`，示例：

```java
package pub.tbc.sc.ext.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(
        value = "${feignClient.demoFeignClient}",
        contextId = "myContextId",
        path = "/demoFeignClient",
        primary = false
)
public interface DemoFeignClient {
    String BEAN_NAME = "pub.tbc.sc.ext.demo.DemoFeignClient";
    String WRAPPER_BEAN_NAME = "demoFeignClient";

    @PostMapping("/doSomeThing")
    Object doSomeThing(@RequestBody Map<String, Object> param);
}
```

2. 定义Feign接口的包装实现：

```java
package pub.tbc.sc.ext.demo;

import lombok.RequiredArgsConstructor;
import pub.tbc.sc.ext.netflix.lb.CustomRouterKey;
import pub.tbc.sc.ext.netflix.lb.LoadBalanceUtil;
import pub.tbc.sc.ext.netflix.lb.strategy.CustomLoadBalanceStrategy;
import pub.tbc.sc.ext.netflix.lb.strategy.CustomLoadBalanceStrategyEnum;

import java.util.Map;

@RequiredArgsConstructor
public class DemoFeignClientWrapper implements DemoFeignClient {
    private final DemoFeignClient target;

    @Override
    public Object doSomeThing(Map<String, Object> param) {
        String routeKeyStr = getRouteKey(param);
        // 负载策略，此处为示例策略，按业务需求自行实现自己的策略
        CustomLoadBalanceStrategy strategy = CustomLoadBalanceStrategyEnum.MODULUS;
        CustomRouterKey routeKey = CustomRouterKey.of(strategy, routeKeyStr);
        return LoadBalanceUtil.routeRun(routeKey, () -> target.doSomeThing(param));
    }

    /**
     * 示例，自行定义自己的路由键
     */
    private String getRouteKey(Map<String, Object> param) {
        return param.get("routeKey").toString();
    }
}

```

3. EnableAutoConfiguration(或者自行手动装配、客户端扫包都可):

```java
package pub.tbc.sc.ext.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ConditionalOnProperty(prefix = "custom.feignClientWrapper", name = "enable", matchIfMissing = true)
public class CustomFeignClientAutoConfiguration {

    @Primary
    @Bean(DemoFeignClient.WRAPPER_BEAN_NAME)
    public DemoFeignClientWrapper spotOrderFeignClientWrapper(@Qualifier(DemoFeignClient.BEAN_NAME) DemoFeignClient target) {
        return new DemoFeignClientWrapper(target);
    }
}

```

spring.factories:

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
  pub.tbc.sc.ext.demo.DemoClientAutoConfiguration
```

4. 最后，业务端引入该 facade包上，直接在应用中注入MyInterface即可；


