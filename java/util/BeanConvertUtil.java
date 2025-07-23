import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BeanConvertUtil extends BeanUtils {
    public static <S, T> T convertTo(S source, Supplier<T> targetSupplier) {
        return convertTo(source, targetSupplier, null);
    }

    public static <S, T> T convertTo(S source, Supplier<T> targetSupplier, ConvertCallBack<S, T> callBack) {
        if (null == source || null == targetSupplier) {
            return null;
        }
        T target = targetSupplier.get();
        copyProperties(source, target);
        if (callBack != null) {
            callBack.callBack(source, target);
        }
        return target;
    }

    public static <S, T> List<T> convertListTo(List<S> sources, Supplier<T> targetSupplier) {
        return convertListTo(sources, targetSupplier, null);
    }

    public static <S, T> List<T> convertListTo(List<S> sources, Supplier<T> targetSupplier, ConvertCallBack<S, T> callBack) {
        if (null == sources || null == targetSupplier) {
            return null;
        }
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T target = targetSupplier.get();
            copyProperties(source, target);
            if (callBack != null) {
                callBack.callBack(source, target);
            }
            list.add(target);
        }
        return list;
    }

    @FunctionalInterface
    public interface ConvertCallBack<S, T> {
        void callBack(S t, T s);
    }

    /**
     * @param src    源对象
     * @param target 目标对象
     * @Description: 将src对象的非空属性值复制到target对象中
     */
    public static void copyNotNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    /**
     * 获取空属性名称
     *
     * @param object 对象
     * @return: @return {@link String[] }
     */
    private static String[] getNullPropertyNames(Object object) {
        final BeanWrapperImpl wrapper = new BeanWrapperImpl(object);
        return Stream.of(wrapper.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(propertyName -> wrapper.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
}
