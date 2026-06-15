package spring.infra.api.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring.infra.api.dtos.auth.SignupResponse;
import spring.infra.api.dtos.event.CreateEventRequest;
import spring.infra.api.dtos.event.CreateEventResponse;
import spring.infra.api.services.AuthService;
import spring.infra.api.services.EventService;
import spring.infra.api.services.UserService;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateEventResponse> createEvent(
            @Valid
            @RequestBody
            CreateEventRequest createEventRequest,
            @AuthenticationPrincipal
            Jwt jwt
    ){
        UUID userId = UUID.fromString(jwt.getSubject());
        CreateEventResponse response = this.eventService.createEvent(createEventRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
