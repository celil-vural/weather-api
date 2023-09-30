package com.weather.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WeatherEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String requestCityName;
    private String cityName;
    private String country;
    private Integer temperature;
    private LocalDateTime updateTime;
    private LocalDateTime responseLocalTime;

    public WeatherEntity(String requestCityName, String cityName, String country, Integer temperature, LocalDateTime updateTime, LocalDateTime responseLocalTime) {
        this.id="";
        this.requestCityName = requestCityName;
        this.cityName = cityName;
        this.country = country;
        this.temperature = temperature;
        this.updateTime = updateTime;
        this.responseLocalTime = responseLocalTime;
    }
}