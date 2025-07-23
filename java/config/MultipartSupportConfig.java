import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

@Configuration
public class MultipartSupportConfig {

    @Bean
    @Primary
    @Scope("prototype")
    public Encoder multipartFormEncoder() {
        return new SpringFormEncoder();
    }

    @Bean
    @Primary
    @Scope("prototype")
    public Encoder multipartFormEncoder(ObjectFactory<HttpMessageConverters> converters) {
        return new SpringFormEncoder(new SpringEncoder(converters));
    }

    @Bean
    public feign.Logger.Level multipartLoggerLevel() {
        return feign.Logger.Level.BASIC;
    }

}