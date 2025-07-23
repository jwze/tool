import com.linkyoyo.weatherpredict.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Objects;
import java.util.function.Supplier;

public class EntityInitUtil {
    private static final Logger logger = LoggerFactory.getLogger(EntityInitUtil.class);

    public static void create(Object obj, User userDTO) {
        Class<?> clazz = obj.getClass();
        try {
            clazz.getMethod("setId", Long.class).invoke(obj, SnowFlakeUtil.getSnowFlakeId());
            clazz.getMethod("setDelFlag", Boolean.class).invoke(obj, false);
            clazz.getMethod("setCreateTime", Date.class).invoke(obj, new Date());
            clazz.getMethod("setUpdateTime", Date.class).invoke(obj, new Date());
        } catch (Exception e) {
            logger.error("初始化实体类创建信息失败（基本信息）: {}", e.getMessage());
        }
        if (Objects.nonNull(userDTO)) {
            try {
                if (Objects.nonNull(userDTO.getId())) {
                    clazz.getMethod("setCreateId", Long.class).invoke(obj, userDTO.getId());
                }
                clazz.getMethod("setCreateName", String.class).invoke(obj, userDTO.getNickName());
                if (Objects.nonNull(userDTO.getId())) {
                    clazz.getMethod("setUpdateId", Long.class).invoke(obj, userDTO.getId());
                }
                clazz.getMethod("setUpdateName", String.class).invoke(obj, userDTO.getNickName());
            } catch (Exception e) {
                logger.error("初始化实体类创建信息失败（用户信息）: {}", e.getMessage());
            }
        }
    }

    public static void update(Object obj, User userDTO) {
        Class<?> clazz = obj.getClass();
        try {
            clazz.getMethod("setUpdateTime", Date.class).invoke(obj, new Date());
        } catch (Exception e) {
            logger.error("初始化实体类更新信息失败（基本信息）: {}", e.getMessage());
        }
        if (Objects.nonNull(userDTO)) {
            try {
                if (Objects.nonNull(userDTO.getId())) {
                    clazz.getMethod("setUpdateId", Long.class).invoke(obj, userDTO.getId());
                }
                clazz.getMethod("setUpdateName", String.class).invoke(obj, userDTO.getNickName());
            } catch (Exception e) {
                logger.error("初始化实体类更新信息失败（用户信息）: {}", e.getMessage());
            }
        }
    }

    public static <T> T query(Supplier<T> target, Long id) {
        try {
            T t = target.get();
            Class<?> clazz = t.getClass();
            clazz.getMethod("setId", Long.class).invoke(t, id);
            clazz.getMethod("setDelFlag", Boolean.class).invoke(t, false);
            return t;
        } catch (Exception e) {
            logger.error("初始化实体类查询信息失败: {}", e.getMessage());
            throw new RuntimeException("初始化实体类查询信息失败: " + e.getMessage());
        }
    }
}
