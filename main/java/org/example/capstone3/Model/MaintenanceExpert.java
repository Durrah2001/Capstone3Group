package org.example.capstone3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MaintenanceExpert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(20) not null unique")
    @NotEmpty(message = "Empty name")
    private String name;

    @Column(columnDefinition = "varchar(35) not null unique")
    @NotEmpty(message = "Empty email")
    @Email(message = "Enter valid email")
    private String email;


    @Column(columnDefinition = "boolean default false ")
    private Boolean isApproved=false;

    @Column(columnDefinition = "varchar(20) not null")
    @NotEmpty(message = "Empty specialty")
    private String specialty;

    @Positive(message = "Price must be a positive number!")
    @Column(columnDefinition = "DOUBLE not null")
    private Double maintenancePricePerDay;

    @Column(columnDefinition = "varchar(500) not null")
    @NotEmpty(message = "Description not valid")
    private String description;


    /////Relations

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "expert")
    private Set<MaintenanceRequest> maintenanceRequestSet;


}
