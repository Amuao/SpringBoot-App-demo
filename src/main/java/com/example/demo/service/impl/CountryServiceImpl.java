package com.example.demo.service.impl;

import com.example.demo.core.exception.NoDataFoundException;
import com.example.demo.model.Country;
import com.example.demo.repository.CountryRepository;
import com.example.demo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public Country update(Country country) {
        Optional<Country> oldCountry = countryRepository.findById(country.getId());
        oldCountry.get().setCountryCode(country.getCountryCode());
        oldCountry.get().setCountryName(country.getCountryName());
        return countryRepository.save(country);
    }

    @Override
    public Country get(Long id) {
       // return countryRepository.findById(id).orElseThrow(() -> new CountryNotFoundException(id));
        return countryRepository.findById(id).orElseThrow(() -> new NoDataFoundException());
    }

    @Override
    public boolean add(Country country) {
        Optional save = Optional.ofNullable(countryRepository.save(country));
        return save.isPresent();
    }

    @Override
    public Page<Country> findAll(Pageable pageable) {
        return countryRepository.findAll(pageable);
    }


}
