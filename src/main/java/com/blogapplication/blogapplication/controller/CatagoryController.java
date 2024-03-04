package com.blogapplication.blogapplication.controller;

import com.blogapplication.blogapplication.Repository.CatagoryRepo;
import com.blogapplication.blogapplication.entities.Catagory;
import com.blogapplication.blogapplication.payload.CatagoryDto;
import com.blogapplication.blogapplication.service.CatagoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catagory")
public class CatagoryController {
    @Autowired
    CatagoryService catagoryService;
    @PostMapping("/add")
    public ResponseEntity<CatagoryDto> createcatagory(@RequestBody CatagoryDto catagoryDto)
    {
        CatagoryDto addcatagory = this.catagoryService.createCatagory(catagoryDto);
        return new ResponseEntity<>(addcatagory, HttpStatus.CREATED);
    }
    @PutMapping("/update/{catagoryId}")
    public ResponseEntity<CatagoryDto> updatecatagory(@RequestBody CatagoryDto catagoryDto,@PathVariable Integer catagoryId)
    {
        CatagoryDto update = this.catagoryService.update(catagoryDto, catagoryId);
        return new ResponseEntity<>(update,HttpStatus.OK);
    }
    @GetMapping("/fetch")
    public ResponseEntity<List<CatagoryDto>>getcatagories()
    {
        List<CatagoryDto> getcatagories = this.catagoryService.getcatagories();
        return new ResponseEntity<>(getcatagories,HttpStatus.OK);

    }
    @GetMapping("/fetch/{catagoryId}")
    public  ResponseEntity<CatagoryDto> getcatagorybyId(@PathVariable Integer catagoryId)
    {
        CatagoryDto getcatagory = this.catagoryService.getcatagory(catagoryId);
        return new ResponseEntity<>(getcatagory,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{catagoryId}")
    public String deletecatagory(@PathVariable Integer catagoryId)
    {
        return  this.catagoryService.deletecatagory(catagoryId);
    }

}
