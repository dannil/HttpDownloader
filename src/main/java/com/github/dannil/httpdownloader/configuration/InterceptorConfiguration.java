package com.github.dannil.httpdownloader.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.dannil.httpdownloader.interceptor.DownloadsAccessInterceptor;
import com.github.dannil.httpdownloader.interceptor.DownloadsInterceptor;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    DownloadsAccessInterceptor downloadsAccessInterceptor;

    @Autowired
    DownloadsInterceptor downloadsInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(downloadsAccessInterceptor).addPathPatterns("/downloads/start/**");
        registry.addInterceptor(downloadsInterceptor).addPathPatterns("/downloads/**");
    }

}
