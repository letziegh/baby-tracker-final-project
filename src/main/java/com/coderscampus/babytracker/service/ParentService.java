package com.coderscampus.babytracker.service;

import com.coderscampus.babytracker.domain.Parent;
import com.coderscampus.babytracker.repository.ParentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ParentService {

    private final ParentRepository parentRepository;

    public ParentService(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    @Transactional
    public Parent saveParent(String email, String name) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        return parentRepository.findByEmail(email)
                .map(existingParent -> {
                    // Update existing parent's name if it has changed
                    if (!existingParent.getName().equals(name)) {
                        existingParent.setName(name);
                        return parentRepository.save(existingParent);
                    }
                    return existingParent;
                })
                .orElseGet(() -> {
                    // Create new parent
                    Parent newParent = new Parent();
                    newParent.setEmail(email);
                    newParent.setName(name);
                    newParent.setChildren(new ArrayList<>()); // Initialize children list
                    return parentRepository.save(newParent);
                });
    }
    public Parent getParentById(Long id){
                return parentRepository.findById(id).orElse(null);
            }

    public Parent findByEmail(String email) {
        return parentRepository.findByEmail(email).orElse(null);
    }

    // ... other existing methods ...
}

// @Service
// public class ParentService {

//     private final ParentRepository parentRepository;

//     public ParentService(ParentRepository parentRepository) {
//         this.parentRepository = parentRepository;
//     }
//     public Parent findByEmail(String email) {
//         return parentRepository.findByEmail(email).orElse(null);
//     }


//     @Transactional
//     public Parent saveParent(String email, String name) {
//         Optional<Parent> existingParent = parentRepository.findByEmail(email);
//         if (existingParent.isPresent()) {
//             System.out.println("Parent already exists: " + email);
//             return existingParent.get(); // Return existing parent instead of failing
//         }

//         try {
//             Parent newParent = new Parent(email, name);
//             System.out.println("Saving new Parent: " + email + ", Name: " + name);
//             Parent savedParent = parentRepository.save(newParent);
//             System.out.println("Successfully saved Parent with ID: " + savedParent.getId());
//             return savedParent;
//         } catch (Exception e) {
//             System.out.println("ERROR: Failed to save parent: " + email);
//             e.printStackTrace();
//             throw new RuntimeException("Database error while saving parent.", e);
//         }
//     }

//     public Parent save(Parent parent) {
//         return parentRepository.save(parent);
//     }


//    @Transactional
//    public Parent saveParent(String email, String name) {
//        return parentRepository.findByEmail(email)
//                .orElseGet(() -> {
//                    Parent newParent = new Parent(email, name);
//                    System.out.println("Attempting to save new parent: Email=" + email + ", Name=" + name);
//                    Parent savedParent = parentRepository.save(newParent);
//                    System.out.println("Successfully saved new parent with ID: " + savedParent.getId());
//                    return savedParent;
//                });
//    }
//    @Transactional
//    public Parent saveParent(String email, String name) {
//        return parentRepository.findByEmail(email)
//                .orElseGet(() -> {
//                    Parent newParent = new Parent(email, name);
//                    System.out.println("Attempting to save new parent: Email=" + email + ", Name=" + name);
//                    return parentRepository.save(newParent);
//                });
//    }
//     public Parent getParentById(Long id){
//         return parentRepository.findById(id).orElse(null);
//     }


// }
