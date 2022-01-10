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

package pub.tbc.sc.ext.netflix.lb;

import com.netflix.loadbalancer.Server;
import lombok.AllArgsConstructor;
import lombok.Data;
import pub.tbc.sc.ext.netflix.lb.strategy.CustomLoadBalanceStrategy;

import java.util.List;

/**
 * @Author tbc by 2022/1/2 13:19 下午
 */
@Data
@AllArgsConstructor
public class CustomRouterKey {
    private CustomLoadBalanceStrategy lbStrategy;
    private String routeKeyValue;

    public static CustomRouterKey of(CustomLoadBalanceStrategy lbStrategy, String value) {
        return new CustomRouterKey(lbStrategy, value);
    }

    public Server choose(List<Server> allServers) {
        return lbStrategy.choose(routeKeyValue, allServers);
    }

}
