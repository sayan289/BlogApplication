package com.blogapplication.blogapplication.Repository;

import com.blogapplication.blogapplication.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {
}
