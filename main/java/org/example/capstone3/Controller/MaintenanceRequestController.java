package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiResponse;
import org.example.capstone3.InDTO.MaintenanceRequestDTO_In;
import org.example.capstone3.OutDTO.MaintenanceRequestDTO;
import org.example.capstone3.Service.MaintenanceRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/motorcycle-system/maintenance-request")
@RequiredArgsConstructor
public class MaintenanceRequestController {

    private final MaintenanceRequestService maintenanceRequestService;

    @GetMapping("/get")
    public ResponseEntity getAllMaintenanceRequest(){
        List<MaintenanceRequestDTO> maintenanceRequestDTOS = maintenanceRequestService.getAllMaintenanceRequest();
        return ResponseEntity.status(200).body(maintenanceRequestDTOS);
    }

    @PostMapping("/add")
    public ResponseEntity addMaintenanceRequest(@RequestBody @Valid MaintenanceRequestDTO_In maintenanceRequestDTO_in){
        maintenanceRequestService.addMaintenanceRequest(maintenanceRequestDTO_in);
        return ResponseEntity.status(200).body(new ApiResponse("MaintenanceRequest created!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMaintenanceRequest(@PathVariable Integer id, @RequestBody @Valid MaintenanceRequestDTO_In maintenanceRequestDTO_in){

        maintenanceRequestService.updateMaintenanceRequest(id, maintenanceRequestDTO_in);
        return ResponseEntity.status(200).body(new ApiResponse("MaintenanceRequest updated!"));

    }

    @DeleteMapping("/delete/{maintenanceRequest_id}")
    public ResponseEntity deleteMaintenanceRequest(@PathVariable Integer maintenanceRequest_id){

        maintenanceRequestService.deleteMaintenanceRequest(maintenanceRequest_id);
        return ResponseEntity.status(200).body(new ApiResponse("MaintenanceRequest deleted!"));

    }
}
