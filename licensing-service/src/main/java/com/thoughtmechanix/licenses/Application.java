package com.thoughtmechanix.licenses;

import com.thoughtmechanix.licenses.config.ServiceConfig;
import com.thoughtmechanix.licenses.events.consumers.OrganizationChangeConsumer;
import com.thoughtmechanix.licenses.events.models.OrganizationChangeModel;
import com.thoughtmechanix.licenses.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.client.RestTemplate;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
@EnableKafka
public class Application {
    @Autowired
    private ServiceConfig serviceConfig;

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName( serviceConfig.getRedisServer());
        config.setPort( serviceConfig.getRedisPort() );
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Organization> redisTemplate() {
        RedisTemplate<String, Organization> template = new RedisTemplate<String, Organization>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
