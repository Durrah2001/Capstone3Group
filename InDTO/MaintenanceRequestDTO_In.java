package org.example.capstone3.InDTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MaintenanceRequestDTO_In {

    private Integer expert_id;

    private Integer motorcycle_id;


    @Column(columnDefinition = "DATE")
    private LocalDate requestDate = LocalDate.now();

    @Column(columnDefinition = "Double")
    private Double totalPrice;



}
