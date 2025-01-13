package com.coderscampus.babytracker.service;

import com.coderscampus.babytracker.domain.Child;
import com.coderscampus.babytracker.repository.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildService {
    private final ChildRepository childRepository;

    public ChildService(ChildRepository childRepository) {
        this.childRepository = childRepository;
    }
    public Child addChild(Child child){
        return childRepository.save(child);
    }
}
