package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiResponse;
import org.example.capstone3.Model.Motorcycle;
import org.example.capstone3.OutDTO.MotorcycleDTO;
import org.example.capstone3.Service.MotorcycleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/motorcycle-system/motorcycle")
@RequiredArgsConstructor
public class MotorcycleController {

    private final MotorcycleService motorcycleService;

    @GetMapping("/get")
    public ResponseEntity getAllMotorcycles(){
        List<MotorcycleDTO> motorcycleDTOS = motorcycleService.getAllMotorcycles();
        return ResponseEntity.status(200).body(motorcycleDTOS);
    }


    @PostMapping("/add/{owner_id}")
    public ResponseEntity addMotorcycle(@PathVariable Integer owner_id, @RequestBody @Valid Motorcycle motorcycle) {

        motorcycleService.addMotorcycle(owner_id, motorcycle);
        return ResponseEntity.status(200).body(new ApiResponse("Motorcycle added successfully!"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateMotorcycle(@PathVariable Integer id, @RequestBody @Valid Motorcycle motorcycle){
        motorcycleService.updateMotorcycle(id, motorcycle);
        return ResponseEntity.status(200).body(new ApiResponse("Motorcycle updated successfully!"));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMotorcycle(@PathVariable Integer id){

        motorcycleService.deleteMotorcycle(id);
        return ResponseEntity.status(200).body(new ApiResponse("Motorcycle deleted successfully!"));

    }









}
