package com.blogapplication.blogapplication.service;

import com.blogapplication.blogapplication.payload.CatagoryDto;

import java.util.List;

public interface CatagoryService {
    CatagoryDto createCatagory(CatagoryDto catagoryDto);
    CatagoryDto update(CatagoryDto catagoryDto, Integer catagoryId);
     String deletecatagory(Integer catagoryId);
     CatagoryDto getcatagory(Integer catagoryId);
    List<CatagoryDto> getcatagories();
}
