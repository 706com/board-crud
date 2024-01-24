package com.example.firstproject.service;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CoffeeService {
    
    @Autowired
    private CoffeeRepository coffeeRepository;
    
    public List<Coffee> index() {
        return coffeeRepository.findAll();
    }

    public Coffee show(Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    public Coffee create(CoffeeDto dto) {
        Coffee created = dto.toEntity();
        if(created.getId() != null){    //id는 적혀있으면 안됨
            return null;
        }
        return coffeeRepository.save(created);
    }

    public Coffee update(Long id, CoffeeDto dto) {
        Coffee coffee = dto.toEntity();
        log.info("toEntity : "+ coffee.toString());

        Coffee target = coffeeRepository.findById(id).orElse(null);
        if(target==null || id != coffee.getId()){
            return null;
        }
        target.patch(coffee);
        Coffee updated = coffeeRepository.save(target);
        return updated;
    }

    public Coffee delete(Long id) {
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if(target == null){
            return null;
        }
        coffeeRepository.delete(target);
        return target;
    }

    @Transactional
    public List<Coffee> createCoffees(List<CoffeeDto> dtos) {

        List<Coffee> coffeeList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

        coffeeList.stream().
                forEach(coffee -> coffeeRepository.save(coffee));
        //강제 예외발생
        coffeeRepository.findById(-1L).orElseThrow(() -> new IllegalArgumentException("실패!"));

        return coffeeList;
    }
}
