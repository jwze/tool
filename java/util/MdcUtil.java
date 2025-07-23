import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class MdcUtil {

    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (CollectionUtils.isEmpty(context)) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            try {
                CurrentUserUtils.setCurrentUser(MDC.get("USER"));
                CurrentTaskUtils.setTaskInfo(MDC.get("TASKID"), MDC.get("FLOWID"));
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    public static <U> Supplier<U> wrap(final Supplier<U> supplier, final Map<String, String> context) {
        return () -> {
            if (CollectionUtils.isEmpty(context)) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            try {
                CurrentUserUtils.setCurrentUser(MDC.get("USER"));
                CurrentTaskUtils.setTaskInfo(MDC.get("TASKID"), MDC.get("FLOWID"));
                return supplier.get();
            } finally {
                MDC.clear();
            }
        };
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (CollectionUtils.isEmpty(context)) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            try {
                CurrentUserUtils.setCurrentUser(MDC.get("USER"));
                CurrentTaskUtils.setTaskInfo(MDC.get("TASKID"), MDC.get("FLOWID"));
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}