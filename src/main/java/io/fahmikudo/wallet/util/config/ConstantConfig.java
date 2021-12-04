package io.fahmikudo.wallet.util.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class ConstantConfig {

    private static ConstantConfig instance = null;

    @Autowired
    private Environment env;

    private String apiKeyGeneral = null;

    @Bean
    public static ConstantConfig getInstance() {
        if (instance == null) {
            instance = new ConstantConfig();
        }

        return instance;
    }

    public String getApiKeyGeneral() {
        if (apiKeyGeneral == null) {
            apiKeyGeneral = env.getProperty("mini.wallet.api.key.general");
        }
        return apiKeyGeneral;
    }

}
