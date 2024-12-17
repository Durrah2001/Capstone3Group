package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiResponse;
import org.example.capstone3.Model.EventRegistration;
import org.example.capstone3.Service.EventRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/motorcycle-system/event-registration")
@RequiredArgsConstructor
public class EventRegistrationController {

    private final EventRegistrationService eventRegistrationService;

    @GetMapping("/get")
    public ResponseEntity getEventRegistrations() {
        return ResponseEntity.status(200).body(eventRegistrationService.getEventRegistrations());
    }

    @PostMapping("/add")
    public ResponseEntity addEventRegistration(@RequestBody @Valid EventRegistration eventRegistration) {
        eventRegistrationService.addEventRegistration(eventRegistration);
        return ResponseEntity.status(200).body(new ApiResponse("eventRegistration added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateEventRegistration(@PathVariable Integer id,@RequestBody @Valid EventRegistration eventRegistration) {
        eventRegistrationService.updateEventRegistration(id, eventRegistration);
        return ResponseEntity.status(200).body(new ApiResponse("eventRegistration updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEventRegistration(@PathVariable Integer id) {
        eventRegistrationService.deleteEventRegistration(id);
        return ResponseEntity.status(200).body(new ApiResponse("eventRegistration deleted"));
    }


}
