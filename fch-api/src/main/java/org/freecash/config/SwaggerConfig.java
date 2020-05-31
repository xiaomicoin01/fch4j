package org.freecash.config;

import io.swagger.models.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author reyton
 */
@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket userRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("CID单点登录接口")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.freecash.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("CID单点登录接口")
                .description("此文档仅供开发技术组领导、开发人员使用。\n" +"登陆方式包括：签名登陆和密码登陆"+"\n"+
                    ">>签名登陆流程：" +
                    "------------1.获取获取随机码"+"\n"+
                    "------------2.签名登陆"+"\n"+
                    "------------3.验证jwt接口是否有效"+"\n"+
                    ">>密码登陆流程：" +
                    "------------1.获取获取随机码"+"\n" +
                    "------------2.签名登陆"+"\n"+
                    "------------3.验证jwt接口是否有效"+"\n"
                )
                .termsOfServiceUrl("http://www.xxx.com/")
                .version("1.0")
                .build();
    }

}
