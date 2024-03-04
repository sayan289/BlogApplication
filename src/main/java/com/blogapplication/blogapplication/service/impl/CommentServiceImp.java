package com.blogapplication.blogapplication.service.impl;

import com.blogapplication.blogapplication.Repository.CommentRepo;
import com.blogapplication.blogapplication.Repository.PostRepo;
import com.blogapplication.blogapplication.entities.Comment;
import com.blogapplication.blogapplication.entities.Post;
import com.blogapplication.blogapplication.exception.ResourceNotFoundException;
import com.blogapplication.blogapplication.payload.CommentDto;
import com.blogapplication.blogapplication.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentServiceImp implements CommentService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;
    //create comment for a particular postId
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", " post_id ", postId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        this.commentRepo.save(comment);
        return this.modelMapper.map(comment,CommentDto.class);
    }

    @Override
    //Delete comment with respect to particular commentId
    public String deleteComment(Integer commentId) {
        Comment comment=this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", " comment_id ", commentId));
        this.commentRepo.delete(comment);
        return "Deleted";
    }
}
