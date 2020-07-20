package com.example.city.controller;

import com.example.city.model.City;
import com.example.city.model.Country;
import com.example.city.service.CityService;
import com.example.city.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class CityController {
    @Autowired
    private CityService cityService;
    @Autowired
    private CountryService countryService;

    @ModelAttribute("country")
    public List<Country> countries(){
        return countryService.findAll();
    }

    @GetMapping("/create-city")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("city", new City());
        return modelAndView;
    }

    @PostMapping("/create-city")
    public ModelAndView saveCity(@Validated  @ModelAttribute("city") City city , BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()){
            return new ModelAndView("create");
        }else{
            cityService.save(city);
            ModelAndView modelAndView = new ModelAndView("create");
            modelAndView.addObject("city", new City());
            modelAndView.addObject("message", "New city created successfully");
            return modelAndView;
        }

    }
    @GetMapping("/city")
    public ModelAndView listCities(){
        List<City> cities = cityService.findAllCity();
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("cities", cities);
        return modelAndView;
    }
    @GetMapping("/edit-city/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Optional<City> city = cityService.findById(id);
        ModelAndView modelAndView;
        if (city.isPresent()) {
            modelAndView = new ModelAndView("edit", "city", city.get());
            return modelAndView;
        }else {
            modelAndView = new ModelAndView("/error.404");
        }
        return modelAndView;
    }
    @PostMapping("/edit-city")
    public ModelAndView updateCity(@Validated @ModelAttribute("city") City city ,BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()){
            return new ModelAndView("edit");
        }else{
            cityService.save(city);
            ModelAndView modelAndView = new ModelAndView("edit");
            modelAndView.addObject("city", new City());
            modelAndView.addObject("message", "New city edit successfully");
            return modelAndView;
        }
    }
    @GetMapping("/delete-city/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Optional<City> city = cityService.findById(id);
        ModelAndView modelAndView;
        if (city.isPresent()) {
            modelAndView = new ModelAndView("delete", "city", city.get());
            return modelAndView;
        }else {
            modelAndView = new ModelAndView("/error.404");
        }
        return modelAndView;
    }

    @PostMapping("/delete-city")
    public String deleteCity(@ModelAttribute("city") City city){
        cityService.remove(city);
        return "redirect:city";
    }
    @GetMapping("/detail-city/{id}")
    public ModelAndView showDetailForm(@PathVariable Long id){
        Optional<City> city = cityService.findById(id);
        ModelAndView modelAndView;
        if (city.isPresent()) {
            modelAndView = new ModelAndView("detail", "city", city.get());
            return modelAndView;
        }else {
            modelAndView = new ModelAndView("/error.404");
        }
        return modelAndView;
    }

}
