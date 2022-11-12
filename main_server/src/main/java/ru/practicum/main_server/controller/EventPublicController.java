package ru.practicum.main_server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_server.dto.EventFullDto;
import ru.practicum.main_server.dto.EventShortDto;
import ru.practicum.main_server.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
@Slf4j
public class EventPublicController {
    private final EventService eventService;

    public EventPublicController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping()
    List<EventShortDto> getEvents(@RequestParam(required = false) String text,
                                  @RequestParam(required = false) List<Long> categories,
                                  @RequestParam(required = false) Boolean paid,
                                  @RequestParam(required = false) String rangeStart,
                                  @RequestParam(required = false) String rangeEnd,
                                  @RequestParam(defaultValue = "true", required = false) Boolean onlyAvailable,
                                  @RequestParam(required = false) String sort,
                                  @RequestParam(defaultValue = "0", required = false) int from,
                                  @RequestParam(defaultValue = "10", required = false) int size,
                                  HttpServletRequest request) {
        log.info("get events public");
        eventService.sentHitStat(request);
        return eventService
                .getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/{id}")
    EventFullDto getEventById(@PathVariable long id, HttpServletRequest request) {
        log.info("get event id={}", id);
        eventService.sentHitStat(request);
        return eventService.getEventById(id);
    }
}
