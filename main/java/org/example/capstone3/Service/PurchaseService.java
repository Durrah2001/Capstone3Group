package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiException;
import org.example.capstone3.InDTO.PurchaseDTO_In;
import org.example.capstone3.Model.Motorcycle;
import org.example.capstone3.Model.Purchase;
import org.example.capstone3.Model.User;
import org.example.capstone3.OutDTO.PurchaseDTO;
import org.example.capstone3.OutDTO.PurchaseMotorcycleOutDTO;
import org.example.capstone3.OutDTO.PurchaseUserOutDTO;
import org.example.capstone3.Repository.MotorcycleRepository;
import org.example.capstone3.Repository.PurchaseRepository;
import org.example.capstone3.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {


    private final PurchaseRepository purchaseRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final UserRepository userRepository;

    public List<PurchaseDTO> getAllPurchases() {
        List<Purchase> purchases = purchaseRepository.findAll();
        List<PurchaseDTO> purchaseOutDTOs = new ArrayList<>();
        for (Purchase purchase : purchases) {
            purchaseOutDTOs.add(new PurchaseDTO(purchase.getPurchaseDate(),new PurchaseUserOutDTO(purchase.getUser().getName(),purchase.getUser().getEmail(),purchase.getUser().getPhoneNumber(),purchase.getUser().getAge(),purchase.getUser().getAddress()),new PurchaseMotorcycleOutDTO(purchase.getMotorcycle().getBrand(),purchase.getMotorcycle().getModel(),purchase.getMotorcycle().getYear(),purchase.getMotorcycle().getPrice(),purchase.getMotorcycle().getColor(),purchase.getMotorcycle().getIsAvailable())));

        }
        return purchaseOutDTOs;
    }

    public void addPurchase(PurchaseDTO_In purchaseInDTO) {

        User user = userRepository.findUserById(purchaseInDTO.getUser_id());
        if (user == null) {
            throw new ApiException("User not found");
        }
        Purchase purchase = new Purchase(user);
        purchaseRepository.save(purchase);
    }


    public void updatePurchase(Integer purchase_id,PurchaseDTO_In purchaseInDTO) {
        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(purchaseInDTO.getMotorcycle_id());
        if (motorcycle == null) {
            throw new ApiException("Motorcycle not found");
        }
        Purchase purchase = purchaseRepository.findPurchaseById(purchase_id);
        if (purchase == null) {
            throw new ApiException("Purchase not found");
        }
        User user = userRepository.findUserById(purchaseInDTO.getUser_id());
        if (user == null) {
            throw new ApiException("User not found");
        }
        purchase.setMotorcycle_id(purchaseInDTO.getMotorcycle_id());
        purchase.setUser(userRepository.findUserById(user.getId()));
        purchaseRepository.save(purchase);

    }




}
