import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

public class FreemarkerGeneratorUtil {

    private static FreemarkerGeneratorUtil freemarkerGeneratorUtil = null;

    public static synchronized FreemarkerGeneratorUtil getInstance() {
        if (freemarkerGeneratorUtil == null) {
            synchronized (FreemarkerGeneratorUtil.class) {
                if (freemarkerGeneratorUtil == null) {
                    freemarkerGeneratorUtil = new FreemarkerGeneratorUtil();
                }
            }
        }
        return freemarkerGeneratorUtil;
    }

    private Template createTemplate(String templateName) throws IOException {
        LinkyoyoProperties linkyoyoProperties = ApplicationContextUtil.getBean(LinkyoyoProperties.class);
        FileTemplateLoader fileTemplateLoader = new FileTemplateLoader(new File(linkyoyoProperties.getPy().getWordTemplate() + "ftl" + File.separator));
        Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setTemplateLoader(fileTemplateLoader);
        return configuration.getTemplate(templateName, "UTF-8");
    }

    public String generateToString(String templateName, Object templateData) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Template template = createTemplate(templateName);
        template.process(templateData, stringWriter);
        stringWriter.flush();
        return stringWriter.toString();
    }
}
