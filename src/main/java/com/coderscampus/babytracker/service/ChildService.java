package com.coderscampus.babytracker.service;

import com.coderscampus.babytracker.domain.Child;
import com.coderscampus.babytracker.domain.Parent;
import com.coderscampus.babytracker.repository.ChildRepository;
import com.coderscampus.babytracker.repository.ParentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChildService {
    private final ChildRepository childRepository;
    private final ParentRepository parentRepository;

    public ChildService(ChildRepository childRepository, ParentRepository parentRepository) {
        this.childRepository = childRepository;
        this.parentRepository = parentRepository;
    }

    public Child addChild(Child child) {
        // Validate parent exists
        Parent parent = parentRepository.findById(child.getParent().getId())
                .orElseThrow(() -> new IllegalArgumentException("Parent not found"));
        
        // Set the parent and initialize activities
        child.setParent(parent);
        if (child.getActivities() == null) {
            child.setActivities(new java.util.HashSet<>());
        }
        
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

    public void deleteChild(Long id) {
        childRepository.deleteById(id);
    }
}
