package com.maxim.springbootfeatures.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Configuration
@ApplicationPath("/")
public class ResteasyConfig extends Application {
}
