package kz.slamkulgroup.kyndebackendapp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    
    @Value("${app.openapi.dev-url:http://localhost:8080}")
    private String devUrl;
    
    @Value("${app.openapi.prod-url:https://api.kynde.com}")
    private String prodUrl;
    
    @Bean
    public OpenAPI kyndeOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");
        
        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");
        
        Contact contact = new Contact();
        contact.setEmail("support@slamkulgroup.kz");
        contact.setName("Kynde Support");
        contact.setUrl("https://www.slamkulgroup.kz");
        
        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");
        
        Info info = new Info()
                .title("Kynde Habit Tracking API")
                .version("1.0.0")
                .contact(contact)
                .description("A production-ready Spring Boot 3.x backend application for a habit-tracking mobile app. " +
                           "This API provides comprehensive habit management, task tracking, streak calculation, " +
                           "and push notification features with JWT authentication.")
                .termsOfService("https://www.slamkulgroup.kz/terms")
                .license(mitLicense);
        
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");
        
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearerAuth");
        
        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer))
                .addSecurityItem(securityRequirement)
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme));
    }
}