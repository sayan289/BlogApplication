package com.blogapplication.blogapplication.controller;

import com.blogapplication.blogapplication.payload.CommentDto;
import com.blogapplication.blogapplication.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping("/add/{postId}")
    public ResponseEntity<CommentDto> create(@RequestBody CommentDto commentDto,@PathVariable Integer postId)
    {
        CommentDto comment = commentService.createComment(commentDto, postId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{commentId}")
    public String deletecomment(@PathVariable Integer commentId)
    {
        return commentService.deleteComment(commentId);
    }
}
