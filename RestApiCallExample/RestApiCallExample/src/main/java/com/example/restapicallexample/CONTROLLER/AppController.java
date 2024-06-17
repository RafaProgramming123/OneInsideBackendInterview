package com.example.restapicallexample.CONTROLLER;


import com.example.restapicallexample.POJO.Joke;
import com.example.restapicallexample.SERVICE.ApiRequestInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api")
public class AppController {


    public final ApiRequestInterface apiRequestInterface;

    public AppController(ApiRequestInterface apiRequestInterface) {
        this.apiRequestInterface = apiRequestInterface;
    }

    @GetMapping("/getJoke")
    public String getJoke(Model model) {
        Joke joke = apiRequestInterface.fetchJoke();
        model.addAttribute("joke", joke);
        System.out.println(joke);
        return "index";
    }

}
