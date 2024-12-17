package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.ApiResponse.ApiException;
import org.example.capstone3.Model.*;
import org.example.capstone3.OutDTO.*;
import org.example.capstone3.Repository.MotorcycleRepository;
import org.example.capstone3.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final MotorcycleRepository motorcycleRepository;



    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userOutDTOs = new ArrayList<>();
        List<EventRegistration> eventRegistrations = new ArrayList<>();
        List<EventRegistrationDTO> eventRegistrationOutDTOs = new ArrayList<>();
        List<Purchase> purchases = new ArrayList<>();
        List<PurchaseDTO> purchaseOutDTOs = new ArrayList<>();
        List<RentingRequest> rentingRequests = new ArrayList<>();
        List<RentingRequestDTO> rentingRequestOutDTOs = new ArrayList<>();
        List<BookingCourse> bookingCourses = new ArrayList<>();
        List<BookingCourseDTO> bookingCourseOutDTOs = new ArrayList<>();
        for (User user : users) {
            for (EventRegistration er:user.getEventRegestrationSet()){
                eventRegistrations.add(er);
                eventRegistrationOutDTOs.add(new EventRegistrationDTO(er.getEvent().getId(),er.getOwner().getId(),er.getUser().getId()));
            }
            for (Purchase p:purchases){
                purchases.add(p);
                Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(p.getMotorcycle_id());
                purchaseOutDTOs.add(new PurchaseDTO(p.getPurchaseDate(),motorcycle.getIsForSale(),new PurchaseUserOutDTO(user.getName(),user.getEmail(),user.getPhoneNumber(),user.getAge(),user.getAddress()),new PurchaseMotorcycleOutDTO(motorcycle.getBrand(),motorcycle.getModel(),motorcycle.getYear(),motorcycle.getPrice(),motorcycle.getColor(),motorcycle.getIsAvailable())));
            }
            for (RentingRequest r:rentingRequests){
                rentingRequests.add(r);
                rentingRequestOutDTOs.add(new RentingRequestDTO(r.getRequestDate(),r.getStartDate(),r.getEndDate(),r.getTotalCost()));
            }
            for (BookingCourse bc:bookingCourses){
                bookingCourses.add(bc);
                bookingCourseOutDTOs.add(new BookingCourseDTO(bc.getBookingDate(),bc.getCourseStartDate(),bc.getCourseEndDate()));
            }
            userOutDTOs.add(new UserDTO(user.getName(),user.getEmail(),user.getPhoneNumber(),user.getAge(),user.getAddress(),eventRegistrationOutDTOs,purchaseOutDTOs,rentingRequestOutDTOs,bookingCourseOutDTOs));
        }
        return userOutDTOs;
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(Integer id,User user) {
        User user1 = userRepository.findUserById(id);
        if (user1 == null){
            throw new ApiException("User not found");
        }
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setAge(user.getAge());
        user1.setAddress(user.getAddress());
        userRepository.save(user1);
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new ApiException("User not found");
        }
        userRepository.delete(user);
    }







}
