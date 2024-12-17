package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiException;
import org.example.capstone3.InDTO.RentingRequestDTO_In;
import org.example.capstone3.Model.Motorcycle;
import org.example.capstone3.Model.Renting;
import org.example.capstone3.Model.RentingRequest;
import org.example.capstone3.Model.User;
import org.example.capstone3.OutDTO.RentingRequestDTO;
import org.example.capstone3.Repository.MotorcycleRepository;
import org.example.capstone3.Repository.RentingRepository;
import org.example.capstone3.Repository.RentingRequestRepository;
import org.example.capstone3.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentingRequestService {


    private final RentingRequestRepository rentingRequestRepository;
    private final RentingRepository rentingRepository;
    private final UserRepository userRepository;
    private final MotorcycleRepository motorcycleRepository;

    public List<RentingRequestDTO> getAllRentingRequests() {
        List<RentingRequest> rentingRequests = rentingRequestRepository.findAll();
        List<RentingRequestDTO> rentingRequestOutDTOs = new ArrayList<>();
        for (RentingRequest rentingRequest : rentingRequests) {
            rentingRequestOutDTOs.add(new RentingRequestDTO(rentingRequest.getRequestDate(),rentingRequest.getStartDate(),rentingRequest.getEndDate(),rentingRequest.getTotalCost()));
        }
        return rentingRequestOutDTOs;
    }

    public void addRentingRequest(RentingRequestDTO_In rentingRequestInDTO) {
        Renting renting = rentingRepository.findRentingById(rentingRequestInDTO.getRenting_id());
        if(renting == null) {
            throw new ApiException("Renting not found");
        }
        User user = userRepository.findUserById(rentingRequestInDTO.getUser_id());
        if(user == null) {
            throw new ApiException("User not found");
        }
        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(renting.getMotorcycle_id());
        if (motorcycle.getIsAvailable()){
            RentingRequest rentingRequest = new RentingRequest(rentingRequestInDTO.getStartDate(),rentingRequestInDTO.getEndDate(),user,renting, motorcycle);
            rentingRequestRepository.save(rentingRequest);
            rentingRepository.save(renting);
        }else {
            throw new ApiException("Motorcycle not available");
        }
    }

    public void updateRentingRequest(Integer rentingRequest_id,RentingRequestDTO_In rentingRequestInDTO) {
        Renting renting = rentingRepository.findRentingById(rentingRequestInDTO.getRenting_id());
        if(renting == null) {
            throw new ApiException("Renting not found");
        }
        RentingRequest rentingRequest = rentingRequestRepository.findRentingRequestById(rentingRequest_id);
        if(rentingRequest == null) {
            throw new ApiException("Renting Request not found");
        }
        User user = userRepository.findUserById(rentingRequestInDTO.getUser_id());
        if(user == null) {
            throw new ApiException("User not found");
        }
        rentingRequest.setStartDate(rentingRequestInDTO.getStartDate());
        rentingRequest.setEndDate(rentingRequestInDTO.getEndDate());
        rentingRequest.setRenting(renting);
        rentingRequest.setUser(user);
        rentingRequestRepository.save(rentingRequest);
    }


    public void deleteRentingRequest(Integer rentingRequest_id) {
        RentingRequest rentingRequest = rentingRequestRepository.findRentingRequestById(rentingRequest_id);
        long hoursUntilStart = Duration.between(LocalDateTime.now(), rentingRequest.getStartDate()).toHours();
        if (hoursUntilStart > 48){
            Renting renting = rentingRepository.findRentingById(rentingRequest_id);
            if(renting != null) {
                renting.setRentingRequests(null);
                rentingRepository.save(renting);
            }


        }
        rentingRequestRepository.delete(rentingRequest);

    }





}
