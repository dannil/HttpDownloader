package com.github.dannil.httpdownloader.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

/**
 * Configuration for Thymeleaf.
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 2.0.0-SNAPSHOT
 */
@Configuration
public class ThymeleafConfiguration {

    /**
     * Default constructor.
     */
    public ThymeleafConfiguration() {

    }

    /**
     * Bean for configuring where Thymeleaf can find the template files.
     *
     * @return bean for configuring where Thymeleaf can find the template files
     */
    @Bean
    @Description("Thymeleaf Template Resolver")
    public ClassLoaderTemplateResolver secondaryTemplateResolver() {
        ClassLoaderTemplateResolver secondaryTemplateResolver = new ClassLoaderTemplateResolver();
        secondaryTemplateResolver.setPrefix("views/");
        secondaryTemplateResolver.setSuffix(".html");
        secondaryTemplateResolver.setTemplateMode(TemplateMode.HTML);
        secondaryTemplateResolver.setCharacterEncoding("UTF-8");
        secondaryTemplateResolver.setOrder(1);
        secondaryTemplateResolver.setCheckExistence(true);

        return secondaryTemplateResolver;
    }

    /**
     * Bean for configuring the layout dialect.
     *
     * @return bean for configuring the layout dialect
     */
    @Bean
    @Description("Thymeleaf Layout Dialect")
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

}
