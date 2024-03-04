package com.blogapplication.blogapplication.service;

import com.blogapplication.blogapplication.entities.Post;
import com.blogapplication.blogapplication.payload.PostDto;
import com.blogapplication.blogapplication.payload.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PostService {

    PostDto create(PostDto postDto,Integer userId,Integer catagoryId);
    PostDto updatePost(PostDto postDto,Integer postId);
    String deletepost(Integer postId);
    //List<PostDto> getAll(Integer pageNumber,Integer pageSize);
    PostResponse getAll(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
    PostDto getpostById(Integer postId);
    //get all post by catagory
    List<PostDto> getPostsByCatagory(Integer catagoryId);
    //get all post by user
    List<PostDto> getPostsByUser(Integer userId);
    //search post
    List<PostDto> searchPost(String keyword);
}
