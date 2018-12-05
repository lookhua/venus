package com.smartdata.venus.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.smartdata.venus.uc.core.utils.FileUpload;

/**
 * @author khlu
 * @date 2018/11/3
 */
@Configuration
public class UploadFilePathConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(FileUpload.getPathPattern()).addResourceLocations("file:" + FileUpload.getUploadPath());
    }
}
