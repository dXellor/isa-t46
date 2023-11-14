package com.isat46.isaback.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openApi() {

        return new OpenAPI()
                .info(new Info()
                        .title("ISA-T46 MEMA")
                        .description("Medical Equipment Management Api")
                        .version("v0.1")
                        .contact(new Contact()
                                .name("ISA-T46")
                                .url("https://github.com/dXellor/isa-t46")
                                .email("lmaopao@gmail.com"))
                        .termsOfService("TOC")
                        .license(new License().name("EpicLicense").url("#"))
                );
    }
}
