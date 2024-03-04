package com.blogapplication.blogapplication.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "catagories")
@Getter
@Setter
@NoArgsConstructor
public class Catagory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer catagoryId;
    private String catagoryTitel;
    private String catagoryDescription;
    @OneToMany(mappedBy = "catagory",cascade = CascadeType.ALL)
    private List<Post> post=new ArrayList<>();
}
