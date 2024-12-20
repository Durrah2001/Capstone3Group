package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiResponse;
import org.example.capstone3.InDTO.RentingRequestDTO_In;
import org.example.capstone3.Service.RentingRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/motorcycle-system/renting-request")

public class RentingRequestController {


    private final RentingRequestService rentingRequestService;

    @GetMapping("/get")
    public ResponseEntity getAllRentingRequests() {
        return ResponseEntity.status(200).body(rentingRequestService.getAllRentingRequests());
    }

    @PostMapping("/add")
    public ResponseEntity addRentingRequest(@RequestBody @Valid RentingRequestDTO_In rentingRequestInDTO) {
        rentingRequestService.addRentingRequest(rentingRequestInDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Renting Request Added "));
    }

    @PutMapping("/update/{rentingRequest_id}")
    public ResponseEntity updateRentingRequest(@PathVariable Integer rentingRequest_id,@RequestBody @Valid RentingRequestDTO_In rentingRequestInDTO) {
        rentingRequestService.updateRentingRequest(rentingRequest_id, rentingRequestInDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Renting Request Updated "));
    }

    @DeleteMapping("/delete/{rentingRequest_id}")
    public ResponseEntity deleteRentingRequest(@PathVariable Integer rentingRequest_id) {
        rentingRequestService.deleteRentingRequest(rentingRequest_id);
        return ResponseEntity.status(200).body(new ApiResponse("Renting Request Deleted "));
    }






}
