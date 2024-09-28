package com.thoughtmechanix.licenses;

import com.thoughtmechanix.licenses.events.consumers.OrganizationChangeConsumer;
import com.thoughtmechanix.licenses.events.models.OrganizationChangeModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableKafka
public class Application {
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    public OrganizationChangeConsumer orgChangeConsumer() {
//        return new OrganizationChangeConsumer();
//    }
}
