package com.coderscampus.babytracker.service;

import com.coderscampus.babytracker.domain.Child;
import com.coderscampus.babytracker.repository.ChildRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildService {
    private final ChildRepository childRepository;

    public ChildService(ChildRepository childRepository) {
        this.childRepository = childRepository;
    }
    public Child addChild(Child child){
        return childRepository.save(child);
    }
    public List<Child> getChildren() {
        return childRepository.findAll();
    }

    public Child getChild(Long id) {
        return childRepository.findById(id).orElse(null);
    }

    public List<Child> getChildrenByParent(Long parentId) {
        return childRepository.findByParentId(parentId);
    }
}
