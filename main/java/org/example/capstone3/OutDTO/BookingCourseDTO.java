package org.example.capstone3.OutDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class BookingCourseDTO {


    private LocalDate bookingDate;

    private LocalDate courseStartDate;

    private LocalDate courseEndDate;
}