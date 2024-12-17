package org.example.capstone3.OutDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PurchaseDTO {

    private LocalDate purchaseDate ;

    private PurchaseUserOutDTO user;

    private PurchaseMotorcycleOutDTO motorcycle;

    public PurchaseDTO(LocalDate purchaseDate, Boolean isForSale, PurchaseUserOutDTO purchaseUserOutDTO, PurchaseMotorcycleOutDTO purchaseMotorcycleOutDTO) {
    }
}
