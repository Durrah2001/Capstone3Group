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
import java.time.LocalDate;
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

    public Integer addRentingRequest(RentingRequestDTO_In rentingRequestDTOIn) {
        // Step 1: Validate input dates
        if (rentingRequestDTOIn.getStartDate().isAfter(rentingRequestDTOIn.getEndDate())) {
            throw new ApiException("Start date cannot be after end date!");
        }
        if (rentingRequestDTOIn.getStartDate().isBefore(LocalDate.now())) {
            throw new ApiException("Start date must be today or in the future!");
        }

        // Step 2: Check if the Renting offer exists
        Renting renting = rentingRepository.findRentingById(rentingRequestDTOIn.getRenting_id());
        if(renting ==null){
            throw  new ApiException("Renting offer not found!");}

        // Step 3: Check for existing rentals on the selected motorcycle
        boolean isRented = rentingRepository.existsByMotorcycleAndDateRange(
                renting.getMotorcycle_id(), rentingRequestDTOIn.getStartDate(), rentingRequestDTOIn.getEndDate()
        );
        if (isRented) {
            throw new ApiException("The motorcycle is not available for the requested dates!");
        }

        // Step 4: Fetch the User
        User user = userRepository.findUserById(rentingRequestDTOIn.getUser_id());
        if(user ==null){
            throw  new ApiException("User not found!");}
        // Step 5: Create and save RentingRequest
        RentingRequest rentingRequest = new RentingRequest();
        rentingRequest.setUser(user);
        rentingRequest.setRenting(renting);
        rentingRequest.setStartDate(rentingRequestDTOIn.getStartDate());
        rentingRequest.setEndDate(rentingRequestDTOIn.getEndDate());
        rentingRequest.setMotorcycle_id(rentingRequestDTOIn.getMotorcycle_id());

        // Calculate total cost based on price per day
        int totalCost = calculateTotalCost(renting.getPricePerDay(), rentingRequestDTOIn.getStartDate(), rentingRequestDTOIn.getEndDate());
        rentingRequest.setTotalCost(totalCost);

        rentingRequestRepository.save(rentingRequest);

        // Return success message
        return totalCost;
    }

    // Helper method to calculate total cost
    private Integer calculateTotalCost(Double pricePerDay, LocalDate startDate, LocalDate endDate) {
        long days = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return (int) (days * pricePerDay);
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
        // Step 1: Find the RentingRequest
        RentingRequest rentingRequest = rentingRequestRepository.findRentingRequestById(rentingRequest_id) ;
        if(rentingRequest==null)
        {new ApiException("Renting request not found!");}

        // Step 2: Calculate hours until start date
        LocalDateTime startDateTime = rentingRequest.getStartDate().atStartOfDay();
        long hoursUntilStart = Duration.between(LocalDateTime.now(), startDateTime).toHours();

        // Step 3: Check if cancellation is allowed
        if (hoursUntilStart <= 48) {
            throw new ApiException("Cannot cancel the request as the start date is less than 48 hours away!");
        }

        // Step 4: Nullify any associated Renting (if needed)
        Renting renting = rentingRequest.getRenting();
        if (renting != null) {
            renting.setRentingRequests(null);
            rentingRepository.save(renting);
        }

        // Step 5: Delete the RentingRequest
        rentingRequestRepository.delete(rentingRequest);
    }







}
