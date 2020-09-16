package com.example.demo.controller;

import com.example.demo.core.base.Result;
import com.example.demo.model.Country;
import com.example.demo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class CountryController {

    @Autowired
    private CountryService countryService;


    /**
     * 分页获取列表
     *
     * @return
     */
    @RequestMapping(value = "/countries", method = RequestMethod.GET)
    public Result<Page<Country>> list(Integer currentPage, Integer pageSize) {
        if(null == currentPage){
            currentPage =1;
            pageSize =10;
        }
        Pageable pageable =PageRequest.of(currentPage, pageSize,Sort.by(Sort.Direction.DESC,"id") );
        return Result.success(countryService.findAll(pageable));
    }


    /**
     * 添加
     *
     * @return
     */
    @RequestMapping(value = "/countries", method = RequestMethod.POST)
    public Result<Country> createCountry(@RequestBody @Validated Country country) {
        return Result.success(countryService.add(country));
    }

    /**
     * 修改
     *
     * @param country
     * @return
     */
    @RequestMapping(value = "/countries", method = RequestMethod.PUT)
    public Result<Country> update(Country country) {
        return Result.success(countryService.update(country));
    }

    /**
     * 获取单个
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/countries/{id}", method = RequestMethod.GET)
    public Result<Country> findOne(@PathVariable("id") Long id) {
        return Result.success(countryService.get(id));
    }


}
