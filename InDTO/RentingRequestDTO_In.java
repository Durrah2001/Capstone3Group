package org.example.capstone3.InDTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RentingRequestDTO_In {

    private Integer renting_id;

    @Column(columnDefinition = "date not null")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private LocalDate startDate;

    @Column(columnDefinition = "date not null")
    @Future(message = "End date must be in the future")
    private LocalDate endDate;


    
}
