package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiException;
import org.example.capstone3.InDTO.MaintenanceRequestDTO_In;
import org.example.capstone3.Model.MaintenanceRequest;
import org.example.capstone3.Model.Motorcycle;
import org.example.capstone3.Model.Owner;
import org.example.capstone3.OutDTO.MaintenanceRequestDTO;
import org.example.capstone3.Repository.MaintenanceRequestRepository;
import org.example.capstone3.Repository.MotorcycleRepository;
import org.example.capstone3.Repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceRequestService {

    private final MaintenanceRequestRepository maintenanceRequestRepository;
    private final OwnerRepository ownerRepository;
    private final MotorcycleRepository motorcycleRepository;



    public List<MaintenanceRequestDTO> getAllMaintenanceRequest(){

        List<MaintenanceRequest> maintenanceRequests = maintenanceRequestRepository.findAll();

        List<MaintenanceRequestDTO> maintenanceRequestDTOS = new ArrayList<>();

        for(MaintenanceRequest maintenanceRequest : maintenanceRequests){
            MaintenanceRequestDTO motorcycleDTOS = new MaintenanceRequestDTO(maintenanceRequest.getExpertName(), maintenanceRequest.getRequestDate(), maintenanceRequest.getTotalPrice(), maintenanceRequest.getStatus() );
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

        MaintenanceRequest maintenanceRequest = new MaintenanceRequest( maintenanceRequestDTO_in.getExpertName(), motorcycle, owner );

        maintenanceRequestRepository.save(maintenanceRequest);

    }


    public void updateMaintenanceRequest(Integer maintenanceRequest_id, MaintenanceRequestDTO_In maintenanceRequestDTO_in){

        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_id);

        if(maintenanceRequest ==null)
            throw new ApiException("MaintenanceRequest not found!");

        maintenanceRequest.setExpertName(maintenanceRequestDTO_in.getExpertName());

        if (maintenanceRequestDTO_in.getMotorcycle_id() != null) {
            Motorcycle motorcycle = motorcycleRepository.findById(maintenanceRequestDTO_in.getMotorcycle_id())
                    .orElseThrow(() -> new ApiException("Motorcycle not found!"));
            maintenanceRequest.setMotorcycle(motorcycle);
        }

        if (maintenanceRequestDTO_in.getOwner_id() != null) {
            Owner owner = ownerRepository.findById(maintenanceRequestDTO_in.getOwner_id())
                    .orElseThrow(() -> new ApiException("Owner not found !"));
            maintenanceRequest.setOwner(owner);
        }

        maintenanceRequestRepository.save(maintenanceRequest);
    }

    public void deleteMaintenanceRequest(Integer maintenanceRequest_id ){

        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_id);
        if(maintenanceRequest == null)
            throw new ApiException("MaintenanceRequest not found!");

        maintenanceRequestRepository.delete(maintenanceRequest);

    }




















}
