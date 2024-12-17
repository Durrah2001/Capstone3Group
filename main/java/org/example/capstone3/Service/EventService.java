package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiException;
import org.example.capstone3.Model.Company;
import org.example.capstone3.Model.Event;
import org.example.capstone3.OutDTO.EventDTO;
import org.example.capstone3.Repository.CompanyRepository;
import org.example.capstone3.Repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final CompanyRepository companyRepository;


    public List<EventDTO> getEvents(){
        List<Event> events = eventRepository.findAll();
        List<EventDTO> eventDTOS = new ArrayList<>();

        for(Event event:events){
            EventDTO eventDTO = new EventDTO(event.getName(),event.getLocation(),event.getDetails(),event.getDate());
            eventDTOS.add(eventDTO);
        }
        return eventDTOS;
    }


    public void addEvent(Integer companyId ,Event event){
        Company company = companyRepository.findCompanyById(companyId);
        if(company== null){
            throw new ApiException("Company not found");
        }
        event.setCompany(company);
        eventRepository.save(event);
    }

    public void updateEvent(Integer id, Event event){
        Event event1 = eventRepository.findEventById(id);
        if(event1==null){
            throw new ApiException("Event not found");
        }
        event1.setDate(event.getDate());
        event1.setDetails(event.getDetails());
        event1.setName(event.getName());
        event1.setLocation(event.getLocation());
    }


    public void deleteEvent(Integer id){
        Event event = eventRepository.findEventById(id);
        if(event ==null){
            throw new ApiException("Event not found");
        }
        eventRepository.delete(event);
    }
}
