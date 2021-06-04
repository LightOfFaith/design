package com.union.design.web.config;

import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setCharset(StandardCharsets.UTF_8);
        fastJsonConfig.setDateFormat("yyyyMMddHHmmss");
        fastJsonConfig.setSerializerFeatures(
                // 输出Map的null值
                SerializerFeature.WriteMapNullValue,
                // 将字符串类型字段的空值输出为空字符串""
                SerializerFeature.WriteNullStringAsEmpty,
                // 将List类型字段空值输出为[]
                SerializerFeature.WriteNullListAsEmpty,
                // 格式化JSON缩进
                SerializerFeature.PrettyFormat
        );
        // 将对象类型的字段null值不输出
        fastJsonConfig.setSerializeFilters(
                (PropertyFilter) (object, name, value) -> Objects.nonNull(value)
        );
        converter.setFastJsonConfig(fastJsonConfig);
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(supportedMediaTypes);
        converters.add(0, converter);
    }

}