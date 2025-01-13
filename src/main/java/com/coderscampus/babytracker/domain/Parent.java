package com.coderscampus.babytracker.domain;

import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "parent")
public class Parent {
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

//    private List<Child> children;
//@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
@OneToMany(mappedBy = "parent", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
private List<Child> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", children=" + children +
                '}';
    }

}
