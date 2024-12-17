package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.capstone3.ApiResponse.ApiException;
import org.example.capstone3.Model.Admin;
import org.example.capstone3.OutDTO.AdminDTO;
import org.example.capstone3.Repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public List<AdminDTO> getAllAdmins(){
        List<Admin> admins = adminRepository.findAll();
        List<AdminDTO> adminDTOS = new ArrayList<>();

        for(Admin admin:admins){
            AdminDTO adminDTO = new AdminDTO(admin.getName(),admin.getEmail());
            adminDTOS.add(adminDTO);
        }
        return adminDTOS;
    }


    public void addAdmin(Admin admin){
        adminRepository.save(admin);
    }

    public void updateAdmin(Integer id,Admin admin){
        Admin oldAdmin = adminRepository.findAdminById(id);
        if(oldAdmin==null){
            throw new ApiException("Admin not found");
        }
        oldAdmin.setEmail(admin.getEmail());
        oldAdmin.setName(admin.getName());
        adminRepository.save(oldAdmin);
    }


    public void deleteAdmin(Integer id){
        Admin admin = adminRepository.findAdminById(id);
        if(admin==null){
            throw new ApiException("Admin not found");
        }
        adminRepository.delete(admin);
    }
}
