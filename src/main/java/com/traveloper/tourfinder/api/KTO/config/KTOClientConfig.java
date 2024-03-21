package com.traveloper.tourfinder.api.KTO.config;

import com.traveloper.tourfinder.api.KTO.service.KTOSearchService;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class KTOClientConfig {

    @Bean
    public RestClient ktoRestClient() {
        return RestClient.builder()
                .baseUrl("http://apis.data.go.kr")
                .build();
    }

    @Bean
    public KTOSearchService ktoSearchService() {
        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(ktoRestClient()))
                .build()
                .createClient(KTOSearchService.class);
    }

}