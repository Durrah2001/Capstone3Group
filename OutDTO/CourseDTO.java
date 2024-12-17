package org.example.capstone3.OutDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseDTO {

    private String name;

    private String description;

    private Double price;

    private Integer duration; //in days

}