import org.slf4j.MDC;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;

public class ThreadPoolMdcExecutor<T> extends CompletableFuture<T> {

    private static final boolean USE_COMMON_POOL = (ForkJoinPool.getCommonPoolParallelism() > 1);

    private static final Executor ASYNC_POOL = Executors.newFixedThreadPool(16);

    public ThreadPoolMdcExecutor() {
        super();
    }

    static final class ThreadPerTaskExecutor implements Executor {
        public void execute(Runnable r) {
            new Thread(r).start();
        }
    }

    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        return runAsync(MdcUtil.wrap(runnable, MDC.getCopyOfContextMap()), ASYNC_POOL);
    }

    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) {
        return supplyAsync(MdcUtil.wrap(supplier, MDC.getCopyOfContextMap()), ASYNC_POOL);
    }
}
