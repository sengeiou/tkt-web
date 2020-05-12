package com.mtl.cypw.web.config;

import com.mtl.cypw.web.interceptor.AuthRequestInterceptor;
import com.mtl.cypw.web.interceptor.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.persistence.OrderBy;

/**
 * @author tang.
 * @date 2019/11/28.
 */
@Order(1)
@Configuration
@Slf4j
@EnableSwagger2
public class MyConfiguration extends WebMvcConfigurationSupport {

    @Value("${global.swagger.enable:false}")
    private boolean swaggerEnable;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.debug("加载拦截器");
        registry.addInterceptor(getRequestInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/pub/v1/enterprise")
                .excludePathPatterns("/pub/v1/notify/**")
                .excludePathPatterns("/pub/v1/check/user/login")
                .excludePathPatterns("/pub/v1/admin/user/login")
                .excludePathPatterns("/swagger-ui.html")
                .excludePathPatterns("/swagger/**")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/v2/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/error");
        registry.addInterceptor(getAuthRequestInterceptor())
                .addPathPatterns("/buyer/v1/**")
                .addPathPatterns("/seller/v1/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public RequestInterceptor getRequestInterceptor() {
        log.debug("创建拦截器");
        return new RequestInterceptor();
    }

    @Bean
    public AuthRequestInterceptor getAuthRequestInterceptor() {
        log.debug("创建权限拦截器");
        return new AuthRequestInterceptor();
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //.enable(swaggerEnable)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mtl.cypw.web"))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(LocalDate.class, java.util.Date.class)
                .directModelSubstitute(DateTime.class, java.util.Date.class)
                .apiInfo(apiInfo());
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("彩熠-票务平台接口文档")
                .description("彩熠票务平台API接口")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .contact(new Contact("彩熠票务", "www.tktstar.com", "tech@tktstar.com"))
                .build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.setAllowCredentials(true);
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configSource);
    }
}
