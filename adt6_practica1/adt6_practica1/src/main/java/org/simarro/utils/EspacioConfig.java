package org.simarro.utils;

// Importa los modelos, NO las anotaciones
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EspacioConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Incidencia API")
                        .description("Ejemplo de API REST para gestionar incidencias")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Ruben Santacatalina")
                                .email("rubsanpel@alu.edu.gva.es")
                                .url("https://ieslluissimarro.org/")));
    }
}