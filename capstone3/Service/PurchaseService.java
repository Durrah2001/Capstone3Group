package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiException;
import org.example.capstone3.InDTO.PurchaseDTO_In;
import org.example.capstone3.Model.Motorcycle;
import org.example.capstone3.Model.Owner;
import org.example.capstone3.Model.Purchase;
import org.example.capstone3.Model.User;
import org.example.capstone3.OutDTO.PurchaseDTO;
import org.example.capstone3.OutDTO.PurchaseMotorcycleOutDTO;
import org.example.capstone3.OutDTO.PurchaseUserOutDTO;
import org.example.capstone3.Repository.MotorcycleRepository;
import org.example.capstone3.Repository.OwnerRepository;
import org.example.capstone3.Repository.PurchaseRepository;
import org.example.capstone3.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PurchaseService {


    private final PurchaseRepository purchaseRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;

    public List<PurchaseDTO> getAllPurchases() {
        List<Purchase> purchases = purchaseRepository.findAll();
        List<PurchaseDTO> purchaseOutDTOs = new ArrayList<>();
        for (Purchase purchase : purchases) {
            Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(purchase.getMotorcycle_id());
            purchaseOutDTOs.add(new PurchaseDTO(purchase.getPurchaseDate(),motorcycle.getIsForSale(),new PurchaseUserOutDTO(purchase.getUser().getName(),purchase.getUser().getEmail(),purchase.getUser().getPhoneNumber(),purchase.getUser().getAge(),purchase.getUser().getAddress()),new PurchaseMotorcycleOutDTO(motorcycle.getBrand(),motorcycle.getModel(),motorcycle.getYear(),motorcycle.getPrice(),motorcycle.getColor(),motorcycle.getIsAvailable())));
        }
        return purchaseOutDTOs;
    }

    public void addPurchase(PurchaseDTO_In purchaseInDTO) {
        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(purchaseInDTO.getMotorcycle_id());
        if (motorcycle == null) {
            throw new ApiException("Motorcycle not found");
        }
        User user = userRepository.findUserById(purchaseInDTO.getUser_id());
        if (user == null) {
            throw new ApiException("User not found");
        }
        Owner owner = ownerRepository.findOwnerById(purchaseInDTO.getOwner_id());
        Set<Motorcycle> motorcycleOwner = owner.getMotorcycles();
        for (Motorcycle mo:owner.getMotorcycles()){
            if (mo.getId() == motorcycle.getId()) {
                motorcycleOwner.remove(mo);
            }
        }
        if (owner == null) {
            throw new ApiException("Owner not found");
        }
        if (motorcycle.getIsAvailable() && motorcycle.getIsForSale()) {
            Purchase purchase = new Purchase(user, motorcycle.getId());
            purchaseRepository.save(purchase);
            owner.setMotorcycles(motorcycleOwner);
            ownerRepository.save(owner);
        }
    }




}
