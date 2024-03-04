package com.blogapplication.blogapplication.service.impl;

import com.blogapplication.blogapplication.Repository.CatagoryRepo;
import com.blogapplication.blogapplication.entities.Catagory;
import com.blogapplication.blogapplication.entities.User;
import com.blogapplication.blogapplication.exception.ResourceNotFoundException;
import com.blogapplication.blogapplication.payload.CatagoryDto;
import com.blogapplication.blogapplication.payload.UserDto;
import com.blogapplication.blogapplication.service.CatagoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CatagoryServiceImp implements CatagoryService {
    @Autowired
    CatagoryRepo catagoryRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
//    convert the Dto type to catagory type then store into database and then convert to dto type.
    public CatagoryDto createCatagory(CatagoryDto catagoryDto) {
        Catagory catagory=this.dtoToCatagory(catagoryDto);
        catagoryRepo.save(catagory);
        return this.catagoryToDto(catagory);
    }

    @Override
//    Update the catagory for a particular catagory id
    public CatagoryDto update(CatagoryDto catagoryDto, Integer catagoryId) {
        Catagory catagory=catagoryRepo.findById(catagoryId).orElseThrow(()-> new ResourceNotFoundException("Catagory"," id ",catagoryId));
        catagory.setCatagoryTitel(catagoryDto.getCatagoryTitel());
        catagory.setCatagoryDescription(catagoryDto.getCatagoryDescription());
        Catagory updatecatagory = this.catagoryRepo.save(catagory);
        return this.catagoryToDto(updatecatagory);
    }
//To delete catagory
    @Override
    public String deletecatagory(Integer catagoryId) {
        Catagory catagory=catagoryRepo.findById(catagoryId).orElseThrow(()-> new ResourceNotFoundException("Catagory"," id ",catagoryId));
        catagoryRepo.delete(catagory);
        return "Deleted";
    }

    @Override
    public CatagoryDto getcatagory(Integer catagoryId) {
        Catagory catagory=catagoryRepo.findById(catagoryId).orElseThrow(()-> new ResourceNotFoundException("Catagory"," id ",catagoryId));
        return this.catagoryToDto(catagory);
    }
// Conver all the catagory user to CatagoriesDto
    @Override
    public List<CatagoryDto> getcatagories() {
        List<Catagory> catagories = catagoryRepo.findAll();
        List<CatagoryDto> catagoryDtos=new ArrayList<>();
        catagories.forEach(catagory -> {
            catagoryDtos.add(this.catagoryToDto(catagory));
        });
        return catagoryDtos;
    }
//    convert Catagory class to CatagoryDto class
    public Catagory dtoToCatagory(CatagoryDto catagoryDto)
    {
        Catagory catagory=this.modelMapper.map(catagoryDto,Catagory.class);
        return catagory;
    }
//    convert CatagoryDto class to Catagory
    public CatagoryDto catagoryToDto(Catagory catagory)
    {
        CatagoryDto catagoryDto=this.modelMapper.map(catagory,CatagoryDto.class);
        return catagoryDto;
    }
}
