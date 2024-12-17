package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiException;
import org.example.capstone3.InDTO.MaintenanceRequestDTO_In;
import org.example.capstone3.Model.MaintenanceExpert;
import org.example.capstone3.Model.MaintenanceRequest;
import org.example.capstone3.Model.Motorcycle;
import org.example.capstone3.Model.Owner;
import org.example.capstone3.OutDTO.MaintenanceRequestDTO;
import org.example.capstone3.Repository.MaintenanceExpertRepository;
import org.example.capstone3.Repository.MaintenanceRequestRepository;
import org.example.capstone3.Repository.MotorcycleRepository;
import org.example.capstone3.Repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceRequestService {

    private final MaintenanceRequestRepository maintenanceRequestRepository;
    private final OwnerRepository ownerRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final MaintenanceExpertRepository maintenanceExpertRepository;



    public List<MaintenanceRequestDTO> getAllMaintenanceRequest(){

        List<MaintenanceRequest> maintenanceRequests = maintenanceRequestRepository.findAll();

        List<MaintenanceRequestDTO> maintenanceRequestDTOS = new ArrayList<>();

        for(MaintenanceRequest maintenanceRequest : maintenanceRequests){
            MaintenanceRequestDTO motorcycleDTOS = new MaintenanceRequestDTO(maintenanceRequest.getExpertName(), maintenanceRequest.getRequestDate(), maintenanceRequest.getTotalPrice(), maintenanceRequest.getStatus(), maintenanceRequest.getPickupDate() );
            maintenanceRequestDTOS.add(motorcycleDTOS);
        }
        return maintenanceRequestDTOS;
    }

    public void addMaintenanceRequest(MaintenanceRequestDTO_In maintenanceRequestDTO_in){

        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(maintenanceRequestDTO_in.getMotorcycle_id());
        if(motorcycle == null){
              throw new ApiException("Motorcycle not found");
        }

        Owner owner = ownerRepository.findOwnerById(maintenanceRequestDTO_in.getOwner_id());
        if(owner == null)
                throw new ApiException("Owner not found");


        if (maintenanceRequestDTO_in.getPickupDate().isBefore(LocalDate.now())) {
            throw new ApiException("Pickup date cannot be in the past!");
        }

        MaintenanceExpert expert = maintenanceExpertRepository.findMaintenanceExpertByName(maintenanceRequestDTO_in.getExpertName());
        if (expert == null) {
            throw new ApiException("Expert not found!");
        }

        Double totalPrice = calculateTotalPrice(expert, maintenanceRequestDTO_in.getPickupDate());

        MaintenanceRequest maintenanceRequest = new MaintenanceRequest( maintenanceRequestDTO_in.getExpertName(), motorcycle, owner, maintenanceRequestDTO_in.getPickupDate());

        maintenanceRequest.setStatus("Pending");
        maintenanceRequest.setTotalPrice(totalPrice);

        maintenanceRequestRepository.save(maintenanceRequest);

    }

    //method to calculate total price
    private Double calculateTotalPrice(MaintenanceExpert expert, LocalDate pickupDate) {
        // calc price based on expert daily rate and number of days
        Double numberOfDays = (double) Duration.between(LocalDate.now().atStartOfDay(), pickupDate.atStartOfDay()).toDays();

        // Calculate total price as the daily rate times the number of days
        return expert.getMaintenancePricePerDay() * numberOfDays;
    }


    public void updateMaintenanceRequest(Integer maintenanceRequest_id, MaintenanceRequestDTO_In maintenanceRequestDTO_in){

        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_id);

        if(maintenanceRequest ==null)
            throw new ApiException("MaintenanceRequest not found!");

        maintenanceRequest.setExpertName(maintenanceRequestDTO_in.getExpertName());
        maintenanceRequest.setPickupDate(maintenanceRequestDTO_in.getPickupDate());


        if (maintenanceRequestDTO_in.getOwner_id() != null) {
            Owner owner = ownerRepository.findOwnerById(maintenanceRequestDTO_in.getOwner_id());
            if(owner== null)
                    throw new ApiException("Owner not found !");

            maintenanceRequest.setOwner(owner);
        }

        if (maintenanceRequestDTO_in.getMotorcycle_id() != null) {
            maintenanceRequest.setMotorcycle_id(maintenanceRequestDTO_in.getMotorcycle_id());
        }

        if (maintenanceRequestDTO_in.getPickupDate() != null) {
            MaintenanceExpert expert = maintenanceExpertRepository.findMaintenanceExpertByName(maintenanceRequestDTO_in.getExpertName());
            if (expert == null) {
                throw new ApiException("Expert not found");
            }

            // calculate again the total price if the pickupdate is changed
            Double newTotalPrice = calculateTotalPrice(expert, maintenanceRequestDTO_in.getPickupDate());
            maintenanceRequest.setTotalPrice(newTotalPrice);
        }

        maintenanceRequestRepository.save(maintenanceRequest);
    }

    public void updateMaintenanceRequestStatusToCompleted(Integer maintenanceRequest_id, String expertName) {
        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_id);

        if (maintenanceRequest == null)
            throw new ApiException("MaintenanceRequest not found!");

        // Check if the current expert is the one assigned to the request
        if (!maintenanceRequest.getExpertName().equalsIgnoreCase(expertName)) {
            throw new ApiException("Only the expert can mark the maintenance request as completed!");
        }

        // Only allow the status to be updated if the request is in 'Pending' status
        if (!"Pending".equalsIgnoreCase(maintenanceRequest.getStatus())) {
            throw new ApiException("Maintenance request is not in a Pending status, it cannot be marked as completed!");
        }

        // Update status
        maintenanceRequest.setStatus("Completed");
        maintenanceRequestRepository.save(maintenanceRequest);
    }


    public void deleteMaintenanceRequest(Integer maintenanceRequest_id ){

        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_id);

        if(maintenanceRequest == null)
            throw new ApiException("MaintenanceRequest not found!");

        // Check if the pickupDate is after the current date (meaning the expert has completed their work)
        if (maintenanceRequest.getPickupDate() != null && maintenanceRequest.getPickupDate().isAfter(LocalDate.now())) {
            throw new ApiException("Cannot delete this Maintenance Request !");
        }

        maintenanceRequestRepository.delete(maintenanceRequest);

    }




















}
