package com.github.rsrini7.api.config.swagger;

import com.github.rsrini7.api.config.AppProperties;
import com.google.common.base.Predicate;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.inject.Inject;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@Api(value = "Data API", tags = {"multi api", "proxy","Json-API"})
public class SwaggerConfig {

  private final AppProperties appProperties;

  @Inject
  public SwaggerConfig(AppProperties appProperties) {
    this.appProperties = appProperties;
  }

  @Bean
  public Docket newsApi() {
    return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(apiInfo())
      .select().paths(paths())
      .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("Multi API Proxy")
      .description("This api to access apis from external providers")
      .contact(new Contact("Srinivasan Ragothaman", "https://github.com/rsrini7", "7rsrini@gmail.com"))
      .license("MIT")
      .licenseUrl("https://choosealicense.com/licenses/mit/")
      .version(appProperties.getVersion())
      .build();
  }

  private Predicate<String> paths() {
    return or(
      regex("/json-api/.*"),
      regex("/version"));
  }
}
