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
//
//                             佛曰:
//
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；
//                  不见满街漂亮妹，哪个归得程序员？
// 

package pub.tbc.sc.ext.netflix.lb.strategy;

import com.netflix.loadbalancer.Server;
import org.slf4j.LoggerFactory;
import pub.tbc.sc.ext.netflix.lb.log.LoggerWrapper;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 示例策略：按路由键的哈希值与服务实例数量取模
 *
 * @Author tbc by 2022/01/01
 */
public class CustomHashingStrategy implements CustomLoadBalanceStrategy {
    private LoggerWrapper log = new LoggerWrapper(LoggerFactory.getLogger(getClass()));

    @Override
    public Server choose(String routeKey, List<Server> allServers) {
        return select(routeKey, allServers);
    }

    private Server select(String routeKey, List<Server> allServers) {
        int index = indexOf(Math.abs(routeKey.hashCode()), allServers.size());
        Server selected = sort(allServers).get(index);
        return selected;
    }

    private int indexOf(int routeHash, int size) {
        return routeHash % size;
    }

    protected List<Server> sort(List<Server> servers) {
        return servers.stream().sorted(Comparator.comparing(Server::getHostPort)).collect(toList());
    }


}