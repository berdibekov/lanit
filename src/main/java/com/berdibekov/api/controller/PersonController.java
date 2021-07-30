package com.berdibekov.api.controller;

import com.berdibekov.domain.Person;
import com.berdibekov.domain.dto.PersonDto;
import com.berdibekov.domain.mapper.PersonMapper;
import com.berdibekov.domain.validation.BirthDateValidator;
import com.berdibekov.exception.ResourceAlreadyExistsException;
import com.berdibekov.repository.PersonRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.ZoneId;


@RestController
@RequestMapping({"/api"})
public class PersonController {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final BirthDateValidator birthDateValidator;

    public PersonController(PersonRepository personRepository,
                            PersonMapper personMapper,
                            BirthDateValidator birthDateValidator) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.birthDateValidator = birthDateValidator;
    }

    @ApiOperation(value = "Saves given person")
    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public ResponseEntity<Object> savePerson(@RequestBody @Valid PersonDto personDto) {
        @Valid Person person = personMapper.toEntity(personDto);
        if (personRepository.existsById(person.getId())) {
            throw new ResourceAlreadyExistsException(getMessage(person));
        }
        LocalDate birthDate = person.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


        birthDateValidator.validate(birthDate);
        personRepository.save(person);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private String getMessage(Person person) {
        return "Person with id : " + person.getId() + " already exists.";
    }
}
