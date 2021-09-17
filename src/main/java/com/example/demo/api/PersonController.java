package com.example.demo.api;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/person")
@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public void addPerson(@Validated @NonNull @RequestBody Person person) {
        personService.addPerson(person);
    }

    @GetMapping
    public List<Person> getAllPeople() {
        return personService.getAllPeople();
    }

    @GetMapping(path = "{Id}")
    public Person getPersonById(@PathVariable("Id") UUID Id) {
        return personService.getPersonById(Id)
                .orElse(null);
    }

    @DeleteMapping(path = "{Id}")
    public void deletePersonById(@PathVariable("Id") UUID Id) {
        personService.deletePerson(Id);
    }

    @PutMapping(path = "{Id}")
    public void updatePerson(@PathVariable("Id") UUID Id,@Validated @NonNull @RequestBody Person personToUpdate) {
        personService.updatePerson(Id, personToUpdate);
    }

}
