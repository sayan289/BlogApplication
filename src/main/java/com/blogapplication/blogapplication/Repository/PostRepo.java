package com.blogapplication.blogapplication.Repository;

import com.blogapplication.blogapplication.entities.Catagory;
import com.blogapplication.blogapplication.entities.Post;
import com.blogapplication.blogapplication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {
    public List<Post> findByUser(User user);
    public List<Post> findByCatagory(Catagory catagory);
//    To search post by keyword
    @Query("select u from Post u where u.titel Like %:keyword%")
    public List<Post> findpost(String keyword);
}
