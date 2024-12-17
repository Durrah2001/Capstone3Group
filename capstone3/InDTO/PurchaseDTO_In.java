package org.example.capstone3.InDTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
public class PurchaseDTO_In {

    private Integer motorcycle_id;

    private Integer user_id;

    private Integer owner_id;



}
