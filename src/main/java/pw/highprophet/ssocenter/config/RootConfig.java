package pw.highprophet.ssocenter.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pw.highprophet.ssocenter.web.common.Utils;

/**
 * Created by HighProphet945 on 2017/7/25.
 */
@Configuration
public class RootConfig {

    @Bean
    public Utils utils() {
        return Utils.getInstance();
    }


}
