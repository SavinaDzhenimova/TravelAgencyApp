package org.travelagency.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/excursions/**")
                .addResourceLocations("file:uploads/excursions/");

        registry.addResourceHandler("/uploads/flags/**")
                .addResourceLocations("file:uploads/flags/");
    }
}