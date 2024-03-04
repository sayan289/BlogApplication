package com.blogapplication.blogapplication.Repository;

import com.blogapplication.blogapplication.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Integer> {
}
