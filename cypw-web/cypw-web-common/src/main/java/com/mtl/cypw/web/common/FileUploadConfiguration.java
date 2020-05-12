package com.mtl.cypw.web.common;

import com.mtl.cypw.common.utils.FileUploadTemplate;
import com.mtl.cypw.common.utils.config.FileUploadProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tang.
 * @date 2020/3/16.
 */
@Configuration
public class FileUploadConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "ucloud")
    public FileUploadProperties fileUploadProperties() {
        return new FileUploadProperties();
    }

    @Bean
    public FileUploadTemplate commonFileUploadTemplate() {
        return new FileUploadTemplate(fileUploadProperties());
    }
}
