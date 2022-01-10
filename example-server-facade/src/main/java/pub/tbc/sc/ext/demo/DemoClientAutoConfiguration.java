package pub.tbc.sc.ext.demo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author tbc by 2022/1/10 11:00
 */
@Configuration
@ConditionalOnProperty(prefix = "custom.feignClientWrapper", name = "enable", matchIfMissing = true)
public class DemoClientAutoConfiguration {

    @Primary
    @Bean(DemoFeignClient.WRAPPER_BEAN_NAME)
    public DemoFeignClientWrapper spotOrderFeignClientWrapper(@Qualifier(DemoFeignClient.BEAN_NAME) DemoFeignClient target) {
        return new DemoFeignClientWrapper(target);
    }
}
