package com.bgr.fileupload.service;

import com.bgr.fileupload.model.Person;
import com.bgr.fileupload.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public List<Person> getPersons() {
        return (List<Person>) personRepository.findAll();
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Person findOne(Long id) {
        return personRepository.findById(id).get();
    }

    public Person update(Person person) {
        return personRepository.save(person);
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }
}
