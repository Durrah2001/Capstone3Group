package org.example.capstone3.OutDTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.capstone3.Model.RentingRequest;

import java.util.List;

@Getter
@Setter

public class UserDTO {

    private String name;

    private String email;

    private String phoneNumber;

    private Integer age;

    private String address;


    private List<EventRegistrationDTO> eventRegistrations;

    private List<PurchaseDTO> purchases;

    private List<BookingCourseDTO> bookingCourses;

    private List<RentingRequest> rentingRequests;


    public UserDTO(@NotEmpty(message = "Name not valid") @Size(max = 35, message = "Name must not exceed 35 characters") String name, @NotEmpty(message = "Email not valid") @Email(message = "Invalid email format") String email, @NotEmpty(message = "Phone number not valid") @Pattern(regexp = "^05\\d{8}$", message = "Phone number must start with 05 and be 10 digits") String phoneNumber, @Positive(message = "Age must be positive") @Min(value = 18, message = "You must be at least 18 years old to register") Integer age, @Size(max = 255, message = "Address length must not exceed 255 characters") String address, List<EventRegistrationDTO> eventRegistrationOutDTOs, List<PurchaseDTO> purchaseOutDTOs, List<RentingRequestDTO> rentingRequestUserOutDTOs, List<BookingCourseDTO> bookingCourseOutDTOs) {
    }
}
