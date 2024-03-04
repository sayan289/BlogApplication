package com.blogapplication.blogapplication.Repository;

import com.blogapplication.blogapplication.entities.Catagory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatagoryRepo extends JpaRepository<Catagory,Integer> {
}
