package org.example.capstone3.OutDTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class MaintenanceRequestDTO {

    private LocalDate requestDate = LocalDate.now();

    private Double totalPrice;

    private String expertName;
    
    private String status;

    private LocalDate pickupDate;


    public MaintenanceRequestDTO(String expertName, LocalDate requestDate, Double totalPrice, @Pattern(regexp = "^(Pending|Completed)$") @NotEmpty(message = "varchar(10)") String status, LocalDate pickupDate) {
    }
}
