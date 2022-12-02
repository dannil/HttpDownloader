package com.github.dannil.httpdownloader.configuration;

import com.github.dannil.httpdownloader.interceptor.DownloadsAccessInterceptor;
import com.github.dannil.httpdownloader.interceptor.DownloadsInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration for interceptors.
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 2.0.0-SNAPSHOT
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    private DownloadsAccessInterceptor downloadsAccessInterceptor;

    @Autowired
    private DownloadsInterceptor downloadsInterceptor;

    /**
     * Default constructor.
     */
    public InterceptorConfiguration() {

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(downloadsAccessInterceptor).addPathPatterns("/downloads/start/**");
        registry.addInterceptor(downloadsInterceptor).addPathPatterns("/downloads/**");
    }

}
