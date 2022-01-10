package pub.tbc.sc.ext.netflix.lb;

import org.slf4j.LoggerFactory;
import pub.tbc.sc.ext.netflix.lb.log.LoggerWrapper;

import java.util.function.Supplier;

/**
 * @Author tbc by 2022/1/1 1:22 上午
 */
public class LoadBalanceUtil {
    private static LoggerWrapper log = new LoggerWrapper(LoggerFactory.getLogger(LoadBalanceUtil.class));
    private static final ThreadLocal<CustomRouterKey> ROUTE_KEY_HOLDER = new ThreadLocal<>();

    public static <T> T routeRun(CustomRouterKey customRouterKey, Supplier<T> supplier) {
        ROUTE_KEY_HOLDER.set(customRouterKey);
        try {
            log.info("[feign-ribbon]routerKey : {}", ROUTE_KEY_HOLDER.get());
            return supplier.get();
        } finally {
            log.info("[feign-ribbon]finally : 清空 ROUTE_KEY_HOLDER");
            ROUTE_KEY_HOLDER.remove();
        }
    }

    public static <T> T throwableRun(CustomRouterKey customRouterKey, ExceptionSupplier<T> supplier) throws Exception {
        ROUTE_KEY_HOLDER.set(customRouterKey);
        try {
            log.info("[feign-ribbon][invoke before]routerKey : {}", ROUTE_KEY_HOLDER.get());
            return supplier.get();
        } catch (Exception e) {
            log.error("发生异常：{}", e.getMessage());
            throw e;
        } finally {
            log.info("[feign-ribbon]finally : 清空 ROUTE_KEY_HOLDER");
            ROUTE_KEY_HOLDER.remove();
        }
    }

    public static CustomRouterKey getRouteKey() {
        return ROUTE_KEY_HOLDER.get();
    }

}
