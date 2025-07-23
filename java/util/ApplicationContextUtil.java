import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    public synchronized static void setStaticApplicationContext(ApplicationContext context) {
        ApplicationContextUtil.context = context;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        setStaticApplicationContext(context);
    }

    public static ApplicationContext getContext() {
        return context;
    }
}
