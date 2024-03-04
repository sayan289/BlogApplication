package com.blogapplication.blogapplication.service;

import com.blogapplication.blogapplication.payload.CommentDto;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Integer postId);
    String deleteComment(Integer commentId);
}
