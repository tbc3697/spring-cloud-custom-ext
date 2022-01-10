package pub.tbc.sc.ext.netflix.lb.spring;

import com.netflix.loadbalancer.IRule;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pub.tbc.sc.ext.netflix.lb.CustomZoneAvoidanceRule;
import pub.tbc.sc.ext.netflix.lb.log.LoggerWrapper;

/**
 * @Author tbc by 2021/12/31
 */
@Configuration
public class CustomRibbonClientConfiguration {
    private LoggerWrapper log = new LoggerWrapper(LoggerFactory.getLogger(getClass()));

    @Bean
    public IRule rule() {
        log.info("创建自定义 IRule");
        CustomZoneAvoidanceRule rule = new CustomZoneAvoidanceRule();
        return rule;
    }

}
