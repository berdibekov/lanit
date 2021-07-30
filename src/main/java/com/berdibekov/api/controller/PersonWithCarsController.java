package com.berdibekov.api.controller;

import com.berdibekov.domain.Car;
import com.berdibekov.domain.Person;
import com.berdibekov.domain.dto.CarDto;
import com.berdibekov.domain.dto.PersonDto;
import com.berdibekov.domain.dto.PersonWithCars;
import com.berdibekov.exception.ResourceNotFoundException;
import com.berdibekov.repository.CarRepository;
import com.berdibekov.repository.PersonRepository;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PersonWithCarsController {
    private final PersonRepository personRepository;
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    public PersonWithCarsController(PersonRepository personRepository,
                                    CarRepository carRepository,
                                    ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Get person with his/her cars.")
    @RequestMapping(value = "/personwithcars", method = RequestMethod.GET)
    public ResponseEntity<Object> getPersonWithCars(@RequestParam Long personId) {
        Person person = personRepository.findById(personId).
                orElseThrow(() -> new ResourceNotFoundException(getMessage(personId)));
        Collection<Car> cars = carRepository.findAllByOwnerId(personId);
        List<CarDto> carsDTOs = cars.stream().map(entity -> modelMapper.map(entity, CarDto.class)).collect(Collectors.toList());
        PersonWithCars personWithCars = new PersonWithCars(modelMapper.map(person, PersonDto.class), carsDTOs);
        return new ResponseEntity<>(personWithCars, HttpStatus.OK);
    }

    @ApiOperation(value = "delete all cars and persons.")
    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public ResponseEntity<Object> deleteAllPersonsAndCars() {
        personRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String getMessage(Long personId) {
        return "Person with id : " + personId + " does not exist.";
    }
}
