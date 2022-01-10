package pub.tbc.sc.ext.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author tbc by 2022/1/10 11:05
 */
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
