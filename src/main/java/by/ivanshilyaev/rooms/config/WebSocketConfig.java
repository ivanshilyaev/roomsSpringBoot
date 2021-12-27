package by.ivanshilyaev.rooms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/*
 * Tomcat uses ServletContainerInitializer to find any classes
 * annotated with ServerEndpoint in an application.
 * Spring Boot, on the other hand, doesn’t support ServletContainerInitializer
 * when you’re using any embedded web container.
 */

@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
