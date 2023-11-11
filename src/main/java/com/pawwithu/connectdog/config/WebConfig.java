package com.pawwithu.connectdog.config;

import com.pawwithu.connectdog.common.converter.DogSizeConverter;
import com.pawwithu.connectdog.common.converter.PostStatusConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DogSizeConverter());
        registry.addConverter(new PostStatusConverter());
    }
}
