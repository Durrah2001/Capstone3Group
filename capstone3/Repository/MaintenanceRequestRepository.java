package org.example.capstone3.Repository;

import org.example.capstone3.Model.MaintenanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Integer> {


    MaintenanceRequest findMaintenanceRequestById(Integer id);
}
