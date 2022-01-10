//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                  佛祖镇楼           BUG辟易

package pub.tbc.sc.ext.netflix.lb.spring;

import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pub.tbc.sc.ext.netflix.lb.CustomHystrixConcurrencyStrategy;

/**
 * @Author tbc by 2022/1/1
 */
@Configuration
@Import(CustomRibbonConfiguration.class)
@ConditionalOnProperty(prefix = "pub.customRouteStrategy", name = "enable", matchIfMissing = true)
public class CustomRouteStrategyAutoConfiguration {

    @Bean
    public HystrixConcurrencyStrategy hystrixConcurrencyStrategy() {
        return new CustomHystrixConcurrencyStrategy();
    }

}
