package com.xin.commons.multi.jpa.scanpackage.service.impl;

import com.xin.commons.multi.jpa.scanpackage.dao.second.CityRepository;
import com.xin.commons.multi.jpa.scanpackage.model.City;
import com.xin.commons.multi.jpa.scanpackage.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class CityServiceImpl implements ICityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public City save(String name) {
        City city = new City();
        city.setName(name);
        return cityRepository.save(city);
    }
}
