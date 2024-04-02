package ua.foxmided.foxstudent103852.cardatabaserestservice.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;

@Configuration
public class OpenApiSimpleResponsesConfig {
    private static final List<HttpStatus> STATUSES = List.of(
            HttpStatus.CREATED,
            HttpStatus.BAD_REQUEST,
            HttpStatus.UNAUTHORIZED,
            HttpStatus.FORBIDDEN,
            HttpStatus.NOT_FOUND,
            HttpStatus.INTERNAL_SERVER_ERROR);
    private static final Map<String, MediaType> RESPONSES_MEDIATYPE = new HashMap<>();

    static {
        STATUSES.forEach(status -> {
            MediaType mediaType = new MediaType();
            mediaType.setSchema(new Schema<Map<String, Object>>()
                    .addProperty("status", new IntegerSchema().example(status.value()))
                    .addProperty("message", new StringSchema().example(status.name())));
            RESPONSES_MEDIATYPE.put(String.valueOf(status.value()), mediaType);
        });
    }

    @Bean
    OpenApiCustomizer openApiCustomiserForSimpleResponses() {

        return openAPI -> openAPI
                .components(openAPI.getComponents()
                        .addSchemas("200ResponseSchema", new Schema<Map<String, Object>>()
                                .title(" ")
                                .addProperty("status", new IntegerSchema().example(HttpStatus.OK.value()))
                                .addProperty("message", new StringSchema().example(HttpStatus.OK.name()))))

                .getPaths().entrySet().stream()
                .forEach(path -> path.getValue().readOperations().stream()
                        .forEach(operation -> operation.getResponses().entrySet()
                                .forEach(key -> {
                                    if (RESPONSES_MEDIATYPE.keySet().contains(key.getKey())) {
                                        key.getValue().getContent().clear();
                                        key.getValue().getContent().addMediaType(
                                                org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                                                RESPONSES_MEDIATYPE.get(key.getKey()));
                                    }
                                })));
    }

}
