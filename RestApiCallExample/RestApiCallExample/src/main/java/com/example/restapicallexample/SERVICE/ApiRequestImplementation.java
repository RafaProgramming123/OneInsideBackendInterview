package com.example.restapicallexample.SERVICE;

import com.example.restapicallexample.POJO.Joke;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class ApiRequestImplementation implements  ApiRequestInterface{

    private final RestTemplate restTemplate;

    public ApiRequestImplementation(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Joke fetchJoke() {
        String url = "https://official-joke-api.appspot.com/random_joke";
        return restTemplate.getForObject(url, Joke.class);
    }
}
