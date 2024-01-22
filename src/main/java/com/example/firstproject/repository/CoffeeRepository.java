package com.example.firstproject.repository;

import com.example.firstproject.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee,Long> {
}
