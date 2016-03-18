package com.xerry.config;

import com.xerry.viewer.RssViewer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;

/**
 * Created by eric on 2016/3/18.
 */
@Configuration
@EnableScheduling
@EnableWebMvc
@ComponentScan(basePackages = "com.xerry")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public BeanNameViewResolver createBeanNameViewResolver() {
        return new BeanNameViewResolver();
    }

    @Bean(name = "rssViewer")
    public RssViewer createViewer() {
        return new RssViewer();
    }

}
