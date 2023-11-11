package com.ufba.stock_control.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI config() {
    return new OpenAPI()
      .info(
        new Info()
        .title("Stock control System")
        .version("1.0.0")
        .description("Stock management system")
     );
  }
}
