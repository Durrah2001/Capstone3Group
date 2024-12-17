package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiException;
import org.example.capstone3.Model.Owner;
import org.example.capstone3.OutDTO.CourseDTO;
import org.example.capstone3.OutDTO.MotorcycleDTO;
import org.example.capstone3.OutDTO.OwnerDTO;
import org.example.capstone3.Repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final MotorcycleService motorcycleService;
    private final CourseService courseService;


    public List<OwnerDTO> getAllOwners(){

        List<Owner> owners = ownerRepository.findAll();

        List<OwnerDTO> ownerDTOList = new ArrayList<>();
        List<MotorcycleDTO> motorcycles = motorcycleService.getAllMotorcycles();
        List<CourseDTO> courses = courseService.getAllCourses();

        for(Owner owner : owners){
            OwnerDTO ownerDTO = new OwnerDTO(owner.getName(), owner.getEmail(), owner.getPhoneNumber(), owner.getAddress(),motorcycles, courses );
            ownerDTOList.add(ownerDTO);
        }
        return ownerDTOList;
    }


    public void createOwner(Owner owner) {
        ownerRepository.save(owner);
    }

    public void updateOwner(Integer id, Owner owner){

        Owner o = ownerRepository.findOwnerById(id);

        if(o == null)
            throw new ApiException("Owner not found!");

        o.setName(owner.getName());
        o.setEmail(owner.getEmail());
        o.setPhoneNumber(owner.getPhoneNumber());
        o.setPassword(owner.getPassword());

        ownerRepository.save(o);
    }

    public void deleteOwner(Integer id){

        Owner o = ownerRepository.findOwnerById(id);

        if(o == null)
            throw new ApiException("Owner not found!");

        ownerRepository.delete(o);

    }
    ////////////////













}
