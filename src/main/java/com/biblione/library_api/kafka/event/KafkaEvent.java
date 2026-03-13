package com.biblione.library_api.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaEvent<T> {

    private String eventType;
    private T data;
    private Meta meta;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Meta {
        private UUID messageId;
        private OffsetDateTime timestamp;
        private String source;
        private String version;
    }

    public static <T> KafkaEvent<T> of(String eventType, T data) {
        return KafkaEvent.<T>builder()
                .eventType(eventType)
                .data(data)
                .meta(Meta.builder()
                        .messageId(UUID.randomUUID())
                        .timestamp(OffsetDateTime.now())
                        .source("biblione-library-api")
                        .version("v1")
                        .build())
                .build();
    }
}