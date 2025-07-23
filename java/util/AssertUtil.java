import java.util.Collection;
import java.util.Objects;

public class AssertUtil extends org.springframework.util.Assert {
    public static void isNull(String message, Object... objects) {
        for (Object obj : objects) {
            if (Objects.nonNull(obj)) {
                throw new BizException(message);
            }
        }
    }

    public static void notNull(String message, Object... objects) {
        for (Object obj : objects) {
            if (Objects.isNull(obj)) {
                throw new BizException(message);
            }
        }
    }

    public static void notNull(String message, byte[] obj) {
        if (Objects.isNull(obj)) {
            throw new BizException(message);
        }
    }

    public static void notEmpty(String message, String... strings) {
        for (String str : strings) {
            if (str == null || str.isEmpty()) {
                throw new BizException(message);
            }
        }
    }

    public static void notEmpty(String message, Collection<?>... collections) {
        for (Collection<?> coll : collections) {
            if (coll == null || coll.isEmpty()) {
                throw new BizException(message);
            }
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BizException(message);
        }
    }

    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new BizException(message);
        }
    }
}
