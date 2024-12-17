package org.example.capstone3.Repository;

import org.example.capstone3.Model.Renting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentingRepository extends JpaRepository<Renting, Integer> {

    Renting findRentingById(Integer id);
}
