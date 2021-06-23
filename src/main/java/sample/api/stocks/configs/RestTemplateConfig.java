package sample.api.stocks.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestTemplateConfig {

    @Value("${stocks.api.fqdn}")
    private String stocksApiUrl;

    @Bean
    @Qualifier("stocksApiRestTemplate")
    public RestTemplate stocksApiRestTemplate() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(this.stocksApiUrl));
        return restTemplate;
    }
}
