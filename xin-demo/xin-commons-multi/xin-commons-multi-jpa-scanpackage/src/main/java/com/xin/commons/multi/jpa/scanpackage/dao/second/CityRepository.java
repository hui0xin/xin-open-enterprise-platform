package com.xin.commons.multi.jpa.scanpackage.dao.second;

import com.xin.commons.multi.jpa.scanpackage.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * City Repository
 */
public interface CityRepository extends JpaRepository<City, Integer> {
}
