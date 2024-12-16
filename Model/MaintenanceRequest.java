package org.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MaintenanceRequest {

    @Id //no generate to be wrapping
    private Integer id;


    @Column(columnDefinition = "DATE")
    private LocalDate requestDate = LocalDate.now();  // Date when the maintenance request was created by owner

    @Column(columnDefinition = "Double")
    private Double totalPrice;

    //Relations

    @OneToOne
    @MapsId
    @JsonIgnore
    private MaintenanceExpert expert;

    @OneToOne
    @MapsId
    @JsonIgnore
    private Motorcycle motorcycle;

    @ManyToOne
    @JsonIgnore
    private Owner owner;  // The owner who made the maintenance request



}
