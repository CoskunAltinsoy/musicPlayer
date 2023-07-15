package com.atmosware.musicplayer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        Contact contact = new Contact();
        contact.setName("Mücahit Coşkun Altınsoy");
        contact.email("coskun.altinsoyy@gmail.com");
        return new OpenAPI()
                .info(new Info()
                        .title("Swagger UI")
                        .version("1.0.0")
                        .description("Music Player Swagger UI")
                        .license(new License().name("DefineX Final Case Licence")));

    }
}
