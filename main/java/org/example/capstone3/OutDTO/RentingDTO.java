package org.example.capstone3.OutDTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RentingDTO {


    private Double pricePerDay;


    private String pickupLocation;


    private String dropOffLocation;




}

