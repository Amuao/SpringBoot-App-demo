package com.example.demo.service;

import com.example.demo.model.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CountryService {

    Country update(Country country);

    Country get(Long id);

    boolean add(Country country);

    Page<Country> findAll(Pageable pageable);

}
