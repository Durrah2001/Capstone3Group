package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiException;
import org.example.capstone3.Model.Motorcycle;
import org.example.capstone3.Model.Owner;
import org.example.capstone3.OutDTO.MotorcycleDTO;
import org.example.capstone3.Repository.MotorcycleRepository;
import org.example.capstone3.Repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;
    private final OwnerRepository ownerRepository;

    public List<MotorcycleDTO> getAllMotorcycles(){

        List<Motorcycle> motorcycles = motorcycleRepository.findAll();

        List<MotorcycleDTO> motorcycleDTOS = new ArrayList<>();

        for(Motorcycle motorcycle : motorcycles){

            MotorcycleDTO motorcycleDTO = new MotorcycleDTO(motorcycle.getBrand(), motorcycle.getModel(), motorcycle.getYear(), motorcycle.getPrice(), motorcycle.getColor(), motorcycle.getIsAvailable());

            motorcycleDTOS.add(motorcycleDTO);
        }

        return motorcycleDTOS;
    }

    public void addMotorcycle(Integer owner_id, Motorcycle motorcycle) {

        Owner owner = ownerRepository.findOwnerById(owner_id);

        if (owner == null)
            throw new ApiException("Owner not found!");

        //assign motorcycle to one owner
        motorcycle.setOwner(owner);
        motorcycleRepository.save(motorcycle);
    }


    public void updateMotorcycle(Integer id, Motorcycle motorcycle) {

        Motorcycle m = motorcycleRepository.findMotorcycleById(id);
        if (m == null)
            throw new ApiException("Motorcycle not found!");

        m.setBrand(motorcycle.getBrand());
        m.setModel(motorcycle.getModel());
        m.setPrice(motorcycle.getPrice());
        m.setColor(motorcycle.getColor());
        m.setYear(motorcycle.getYear());
        m.setIsAvailable(motorcycle.getIsAvailable());
        motorcycleRepository.save(m);
    }

    public void deleteMotorcycle(Integer id){

        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(id);
        if(motorcycle == null)
            throw new ApiException("Motorcycle not found!");

        motorcycleRepository.delete(motorcycle);

    }









}
