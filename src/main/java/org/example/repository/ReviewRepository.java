package org.example.repository;

import org.example.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByEmployeeId(Long employeeId);

    void deleteById(Long id);
}
