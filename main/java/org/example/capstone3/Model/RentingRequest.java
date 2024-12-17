package org.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
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
public class RentingRequest {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "date ")
    private LocalDate requestDate = LocalDate.now();

    @Column(columnDefinition = "date not null")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private LocalDate startDate;

    @Column(columnDefinition = "date not null")
    @Future(message = "End date must be in the future")
    private LocalDate endDate;

    @Column(columnDefinition = "int")
    private Integer totalCost;

    @Column(columnDefinition = "int not null")
    private Integer motorcycle_id;

    ////////


    @ManyToOne
    @JsonIgnore
    private Renting renting;

    @ManyToOne
    @JsonIgnore
    private User user;



    public RentingRequest(@FutureOrPresent(message = "Start date must be in the present or future") LocalDate startDate, @Future(message = "End date must be in the future") LocalDate endDate, User user, Renting renting, Motorcycle motorcycle) {
    }
}
