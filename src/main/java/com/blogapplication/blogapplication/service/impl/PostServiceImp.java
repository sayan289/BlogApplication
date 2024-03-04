package com.blogapplication.blogapplication.service.impl;

import com.blogapplication.blogapplication.Repository.CatagoryRepo;
import com.blogapplication.blogapplication.Repository.PostRepo;
import com.blogapplication.blogapplication.Repository.UserRepo;
import com.blogapplication.blogapplication.entities.Catagory;
import com.blogapplication.blogapplication.entities.Post;
import com.blogapplication.blogapplication.entities.User;
import com.blogapplication.blogapplication.exception.ResourceNotFoundException;
import com.blogapplication.blogapplication.payload.PostDto;
import com.blogapplication.blogapplication.payload.PostResponse;
import com.blogapplication.blogapplication.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class PostServiceImp implements PostService {
    @Autowired
    private PostRepo postRepo;
//    To convert Post repo to PostDao and vice versa
    @Autowired
    private ModelMapper modelMapper;
//    To fetch userdetails for a particular userId
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CatagoryRepo catagoryRepo;
    //create post
    @Override
    public PostDto create(PostDto postDto,Integer userId,Integer catagoryId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," user_id ",userId));
        Catagory catagory=catagoryRepo.findById(catagoryId).orElseThrow(()->new ResourceNotFoundException("Catagory"," catagory_id ",catagoryId));
        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.avif");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCatagory(catagory);
        Post updatedpost = this.postRepo.save(post);
        return  this.modelMapper.map(updatedpost,PostDto.class);
    }
//update post
    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postId));
        post.setTitel(postDto.getContent());
        post.setTitel(postDto.getTitel());
        post.setImageName(postDto.getImageName());
        Post save = postRepo.save(post);
        return this.modelMapper.map(save,PostDto.class);
    }

    @Override
    public String deletepost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postId));
        this.postRepo.delete(post);
        return "Deleted";
    }
//    Get all post
    @Override
    public PostResponse getAll(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
        Sort sort=null;
        if(sortDir.equalsIgnoreCase("asc"))
        {
                sort=Sort.by(sortBy).ascending();
        }
        else {
            sort=Sort.by(sortBy).descending();
        }
        Pageable p= PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> pagePost = this.postRepo.findAll(p);
        List<Post> listofpost = pagePost.getContent();
        List<PostDto> postDtoList=new ArrayList<>();
        for (Post post : listofpost) {
            PostDto postDto = this.modelMapper.map(post, PostDto.class);
            postDtoList.add(postDto);
        }
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }
//    Get post by postId
    @Override
    public PostDto getpostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postId));
        PostDto postdto = this.modelMapper.map(post, PostDto.class);
        return postdto;
    }

    @Override
    public List<PostDto> getPostsByCatagory(Integer catagoryId) {
        Catagory catagory = this.catagoryRepo.findById(catagoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category_id", catagoryId));

        List<Post> listofpost = this.postRepo.findByCatagory(catagory);
        List<PostDto> postDtoList = new ArrayList<>();
         listofpost.forEach(list->{
             postDtoList.add(this.modelMapper.map(list,PostDto.class));
         });

        return postDtoList;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user_id", userId));

        List<Post> listofpost = this.postRepo.findByUser(user);
        List<PostDto> postDtoList = new ArrayList<>();

        for (Post post : listofpost) {
            PostDto postDto = this.modelMapper.map(post, PostDto.class);
            postDtoList.add(postDto);
        }

        return postDtoList;
    }
//    Search post with respect to keyword.
    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> findpost = this.postRepo.findpost(keyword);
        List<PostDto> postDtoList = new ArrayList<>();
        findpost.forEach(list->{
            postDtoList.add(this.modelMapper.map(list,PostDto.class));
        });
        return postDtoList;
    }
}
