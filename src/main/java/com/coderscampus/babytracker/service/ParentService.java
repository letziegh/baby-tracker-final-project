package com.coderscampus.babytracker.service;

import com.coderscampus.babytracker.domain.Parent;
import com.coderscampus.babytracker.repository.ParentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParentService {

    private final ParentRepository parentRepository;

    public ParentService(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }
    public Parent findByEmail(String email) {
        return parentRepository.findByEmail(email).orElse(null);
    }
    public Parent save(Parent parent) {
        return parentRepository.save(parent);
    }
    public Parent getParentById(Long id){
        return parentRepository.findById(id).orElse(null);
    }


}
