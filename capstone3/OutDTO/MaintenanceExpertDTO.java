package org.example.capstone3.OutDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MaintenanceExpertDTO {

    private String name;
    private String email;
    private String specialty;
    private Boolean isApproved;
    private String description;


}
