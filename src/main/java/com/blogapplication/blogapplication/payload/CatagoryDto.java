package com.blogapplication.blogapplication.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CatagoryDto {
    private Integer catagoryId;
    private String catagoryTitel;
    private String catagoryDescription;
}
