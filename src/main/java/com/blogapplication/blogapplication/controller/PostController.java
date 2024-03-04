package com.blogapplication.blogapplication.controller;

import com.blogapplication.blogapplication.config.AppConstant;
import com.blogapplication.blogapplication.entities.Post;
import com.blogapplication.blogapplication.payload.PostDto;
import com.blogapplication.blogapplication.payload.PostResponse;
import com.blogapplication.blogapplication.service.FileService;
import com.blogapplication.blogapplication.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;
    @PostMapping("/user/{userId}/catagory/{catagoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,@PathVariable Integer catagoryId)
    {
        PostDto createpost = this.postService.create(postDto, userId, catagoryId);
        return new ResponseEntity<PostDto>(createpost, HttpStatus.CREATED);
    }
    @GetMapping("/catagory/{catagoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCatagory(@PathVariable Integer catagoryId)
    {
        List<PostDto> postsByCatagory = this.postService.getPostsByCatagory(catagoryId);
        return new ResponseEntity<>(postsByCatagory,HttpStatus.OK);
    }
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId)
    {
        List<PostDto> postsByCatagory = this.postService.getPostsByUser(userId);
        return new ResponseEntity<>(postsByCatagory,HttpStatus.OK);
    }
//    In pagination the default index value start from 0
    @GetMapping("/fetch")
    public ResponseEntity<PostResponse> getAll(@RequestParam(value="pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required =false)Integer pageNumber,
                                                     @RequestParam(value="pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false)Integer pageSize,
                                               @RequestParam(value = "sortBy",defaultValue = AppConstant.SORT_BY,required = false)String sortBy,
                                               @RequestParam(value="sortDir",defaultValue = AppConstant.SORT_DIR,required = false)String sortDir)
    {
        PostResponse postResponse = this.postService.getAll(pageNumber, pageSize,sortBy,sortDir);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }
    @GetMapping("/fetch/{postId}")
    public ResponseEntity<PostDto> getbyid(@PathVariable Integer postId)
    {
        PostDto postDto = this.postService.getpostById(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{postId}")
    public String deletepost(@PathVariable Integer postId)
    {
        return this.postService.deletepost(postId);
    }
    @PutMapping("/update/{postId}")
    public ResponseEntity<PostDto>updatepost(@RequestBody PostDto postDto,@PathVariable Integer postId)
    {
        PostDto update = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(update,HttpStatus.OK);
    }
    // search post based on titel keyword
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<PostDto>> search(@PathVariable String keyword)
    {
        List<PostDto> postDtos = this.postService.searchPost(keyword);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }
    //Api to update image name with random name
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image,
                                                         @PathVariable Integer postId) throws IOException {
        PostDto postDto = this.postService.getpostById(postId);
        String fileName = this.fileService.uploadImage(path, image);
        postDto.setImageName(fileName);
        PostDto updatePost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);

    }
    //Api to show image
    @GetMapping(value="/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)throws IOException
    {
        InputStream resource=this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
