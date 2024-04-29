package com.authentifcation.projectpitwo.configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(infoAPI());
    }
    public Info infoAPI() {
        return new Info().title("SpringDoc-Demo")
                .description("SKyTeck-demo")
                .contact(contactAPI());
    }
    public Contact contactAPI() {
        Contact contact = new Contact().name("Skyteck Team")
                .email("skyteck@esprit.tn")
                .url("https://www.linkedin.com/in/**********/");
        return contact;
    }


    @Bean
    public GroupedOpenApi RoomAPI() {
        return GroupedOpenApi.builder()
                .group("Only ROOM Management API")
                .pathsToMatch("/room/**")
                .pathsToExclude("**")
                .build();
    }
    @Bean
    public GroupedOpenApi PosteAPI() {
        return GroupedOpenApi.builder()
                .group("Only Poste Management API")
                .pathsToMatch("/poste/**")
                .pathsToExclude("**")
                .build();
    }
    @Bean
    public GroupedOpenApi CommentAPI() {
        return GroupedOpenApi.builder()
                .group("Only Comment Management API")
                .pathsToMatch("/comment/**")
                .pathsToExclude("**")
                .build();
    }



}
