package com.coderscampus.babytracker.domain;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "Parent")
public class Parent {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

//    private List<Child> children;
//@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
@OneToMany(mappedBy = "parent", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
private List<Child> children;

    public Parent(String email, String name) {
        this.email = email;
        this.name = name;

    }

    public Parent() {

    }


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
        return "Parent{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", children=" + children +
                '}';
    }

}
