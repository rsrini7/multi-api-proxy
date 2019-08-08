package com.github.rsrini7.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.*;

@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    registry.addResourceHandler("/favicon.ico").addResourceLocations("/");
    registry.addResourceHandler("/robots.txt").addResourceLocations("/");
  }

  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    configurer.setUseRegisteredSuffixPatternMatch(true);
  }

  @Override
  public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
    // Turning off suffix-based content negotiation
    configurer.favorPathExtension(false);
  }
}
