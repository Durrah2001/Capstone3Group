package org.example.capstone3.InDTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PurchaseDTO_In {

    private Integer motorcycle_id;

    @Column(columnDefinition = "date")
    private LocalDate purchaseDate = LocalDate.now();


}
