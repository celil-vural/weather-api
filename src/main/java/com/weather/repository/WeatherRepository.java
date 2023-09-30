package com.weather.repository;

import com.weather.model.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherRepository extends JpaRepository<WeatherEntity,String> {
    Optional<WeatherEntity> findFirstByRequestCityNameOrderByUpdateTimeDesc(String city);
}
