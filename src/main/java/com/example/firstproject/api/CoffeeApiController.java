package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CoffeeApiController {
    @Autowired
    private CoffeeRepository coffeeRepository;

    @GetMapping("/api/coffees")
    public List<Coffee> index(){
        return coffeeRepository.findAll();
    }
    @GetMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> show(@PathVariable Long id){
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if(target == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(target);
    }

    @PostMapping("/api/coffees")
    public Coffee create(@RequestBody CoffeeDto dto){
        Coffee coffee = dto.toEntity();
        return coffeeRepository.save(coffee);
    }

    @PatchMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> update(@PathVariable Long id, @RequestBody CoffeeDto dto){
        Coffee coffee = dto.toEntity();
        log.info("toEntity : "+ coffee.toString());

        Coffee target = coffeeRepository.findById(id).orElse(null);
        if(target==null || id != coffee.getId()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        target.patch(coffee);
        coffeeRepository.save(target);
        return ResponseEntity.ok(target);
    }

    @DeleteMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> delete(@PathVariable Long id){
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if(target == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        coffeeRepository.delete(target);
        return ResponseEntity.ok(target);
    }
}