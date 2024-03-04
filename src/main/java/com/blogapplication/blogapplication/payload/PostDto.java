package com.blogapplication.blogapplication.payload;

import com.blogapplication.blogapplication.entities.Catagory;
import com.blogapplication.blogapplication.entities.Comment;
import com.blogapplication.blogapplication.entities.User;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private int postId;
    private String titel;
    private String content;
    private  String imageName;
    private  Date addedDate;
    //@ManyToOne
    private CatagoryDto catagory;
    //@ManyToOne
    private UserDto user;
    private Set<CommentDto> comments= new HashSet<>();
}
