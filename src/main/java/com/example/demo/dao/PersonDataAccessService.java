package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PersonDataAccessService implements PersonDao {

    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return List.of(new Person(UUID.randomUUID(),"FROM POSTGRES DB"));
    }

    @Override
    public Optional<Person> selectPersonById(UUID Id) {
        return DB.stream()
                .filter(person -> person.getId()
                        .equals(Id))
                .findFirst();
    }
    @Override
    public int deletedPersonById(UUID Id) {
        Optional<Person>personMaybe = selectPersonById(Id);
        if (personMaybe.isEmpty()) {
            return 0;
        }
        DB.remove(personMaybe.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID Id, Person update) {
        return selectPersonById(Id)
                .map(person -> {
                    int indexOfPersonToUpdate = DB.indexOf(person);
                    if(indexOfPersonToUpdate >= 0) {
                        DB.set(indexOfPersonToUpdate, new Person(Id, update.getName()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }
}
