package pub.tbc.sc.ext.netflix.lb;

/**
 * @Author tbc by 2021/4/26 16:53
 */
@FunctionalInterface
public interface ExceptionSupplier<R> {

    R get() throws Exception;

}
