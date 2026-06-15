package spring.infra.api.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.infra.api.dtos.event.CreateEventRequest;
import spring.infra.api.dtos.event.CreateEventResponse;
import spring.infra.api.enums.EventStatus;
import spring.infra.api.models.Event;
import spring.infra.api.repository.EventRepository;
import spring.infra.api.exceptions.CnpjAlreadyExistsException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;


@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    public CreateEventResponse createEvent(CreateEventRequest request, UUID userId) {
        if (request.endsAt() <= request.startsAt()) {
            throw new IllegalArgumentException("The event must end after it starts.");
        }

        if (eventRepository.findByCnpj(request.cnpj()).isPresent()) {
            throw new CnpjAlreadyExistsException("An event with this CNPJ already exists.");
        }

        Event evt = new Event();

        evt.setName(request.name());
        evt.setCnpj(request.cnpj());
        evt.setDescription(request.description());
        evt.setAddress(request.address());
        evt.setLatitude(request.latitude());
        evt.setLongitude(request.longitude());
        evt.setMaxParticipants(request.maxParticipants());
        evt.setStartsAt(request.startsAt());
        evt.setEndsAt(request.endsAt());
        
        evt.setCreatedBy(userId);
        evt.setCreatedAt(Timestamp.from(Instant.now()));
        evt.setStatus(EventStatus.DRAFT);

        Event savedEvent = eventRepository.save(evt);

        return new CreateEventResponse(
                savedEvent.getId(),
                savedEvent.getName(),
                savedEvent.getStatus(),
                savedEvent.getCreatedAt().getTime()
        );
    }
}
