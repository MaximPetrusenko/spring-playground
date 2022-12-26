package com.maxim.springbootfeatures.clientproxy;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

@Configuration
public class ClientProxyConfig {

    @Value("${users.api.url.v1}")
    private String userEndpointUrl;

    @Bean
    public UserResourceV1 getUserResourceV1() {

        // Client
        ResteasyClient client = (ResteasyClient) ClientBuilder.newClient();
        // WebTarget
        ResteasyWebTarget target = client.target(userEndpointUrl);
        // Return Proxy
        UserResourceV1 proxy = target.proxy(UserResourceV1.class);
        return proxy;

    }


}

