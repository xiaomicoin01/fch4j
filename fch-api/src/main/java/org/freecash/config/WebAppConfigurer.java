package org.freecash.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.freecash.controller.interceptor.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class WebAppConfigurer  extends WebMvcConfigurationSupport {

    /** 解决跨域问题 **/
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/api/v1/wallet/**");
    }

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);

        /*
         * 1、需要先定义一个 convert 转换消息的对象;
         * 2、添加fastJson 的配置信息，比如：是否要格式化返回的json数据;
         * 3、在convert中添加配置信息.
         * 4、将convert添加到converters当中.
         *
         */

        // 1、需要先定义一个 convert 转换消息的对象;
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        //2、添加fastJson 的配置信息，比如：是否要格式化返回的json数据;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat
        );

        //3、在convert中添加配置信息.
        fastConverter.setFastJsonConfig(fastJsonConfig);

        //4、将convert添加到converters当中.
        converters.add(fastConverter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * SpringBoot自动配置本身并不会把/swagger-ui.html
         * 这个路径映射到对应的目录META-INF/resources/下面
         * 采用WebMvcConfigurerAdapter将swagger的静态文件进行发布;
         */
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX +"/static/");
        super.addResourceHandlers(registry);
    }
}