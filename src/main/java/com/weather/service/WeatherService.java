package com.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.dto.WeatherDto;
import com.weather.dto.WeatherResponse;
import com.weather.model.WeatherEntity;
import com.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRepository repository;
    private final RestTemplate restTemplate;
    @Value("${request.base-url}")
    private String baseUrl;
    @Value("${request.access_key}")
    private String accessKey;
    private final ObjectMapper objectMapper=new ObjectMapper();
    public WeatherDto getWeatherByCityName(String city){
        Optional<WeatherEntity> weatherEntityOptional=repository.findFirstByRequestCityNameOrderByUpdateTimeDesc(city);
        return weatherEntityOptional.map(w->{
            if(w.getUpdateTime().isBefore(LocalDateTime.now().minusMinutes(30))){
                return WeatherDto.convert(getWeatherFromWeatherStack(city));
            }
            return WeatherDto.convert(w);
        }).orElseGet(()-> WeatherDto.convert(getWeatherFromWeatherStack(city)));
    }
    private String getUrl(){
        return baseUrl + "?access_key=" + accessKey + "&query=";
    }
    private WeatherEntity getWeatherFromWeatherStack(String city) {
        String uri=getUrl()+city;
        ResponseEntity<String> responseEntity=restTemplate.getForEntity(uri,String.class);
        try {
            WeatherResponse weatherResponse=objectMapper.readValue(responseEntity.getBody(),WeatherResponse.class);
            return saveWeatherEntity(city,weatherResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private WeatherEntity saveWeatherEntity(String city,WeatherResponse weatherResponse){
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        WeatherEntity weatherEntity=new WeatherEntity(
                city,
                weatherResponse.location().name(),
                weatherResponse.location().country(),
                weatherResponse.current().temperature(),
                LocalDateTime.now(),
                LocalDateTime.parse(weatherResponse.location().localtime(),dateTimeFormatter)
                );
        return repository.save(weatherEntity);
    }
}
