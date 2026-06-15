package spring.infra.api.dtos.event;

import spring.infra.api.enums.EventStatus;

import java.util.UUID;

public record CreateEventResponse(
        UUID id,
        String name,
        EventStatus status,
        Long createdAt
) {
}
