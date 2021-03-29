package org.freecash.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@Configuration
@EnableSwagger2
@ConditionalOnExpression("true")
public class SwaggerConfig {

    @Bean
    public Docket userRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("对外接口")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.freecash.controller.api"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("对外接口")
                .description("此文档仅供开发技术组领导、开发人员使用。")
                .termsOfServiceUrl("http://www.api.masterwang.top/")
                .version("1.0")
                .build();
    }

}
