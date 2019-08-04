package com.bgr.fileupload.controller;

import com.bgr.fileupload.domain.Response;
import com.bgr.fileupload.model.Person;
import com.bgr.fileupload.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
public class PersonController {

    @Autowired
    PersonService personService;

    @PostMapping(value = "/saveUserProfile")
    public ResponseEntity<Response> saveuserprofile(@RequestParam("file")MultipartFile file, @RequestParam("user") String user) throws IOException {

        Person person = new ObjectMapper().readValue(user, Person.class);
        person.setFile(file.getBytes());
        person.setFileName(file.getOriginalFilename());
        person.setCreatedDate(new Date());
        Person dbPerson = personService.save(person);
        if(dbPerson!=null) {
            return new ResponseEntity<Response>(new Response("User is saved successfully"), HttpStatus.OK);
        }else {
            return new ResponseEntity<Response>(new Response("User is not saved"), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "/persons")
    public ResponseEntity<List<Person>> getPersons(){

        List<Person> persons = personService.getPersons();
        return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Person> save(@RequestBody Person person) throws Exception{

        if (person==null){
            throw new NullPointerException("Person object cannot be null");
        }
        person.setCreatedDate(new Date());
        Person dbPerson = personService.save(person);
        return new ResponseEntity<Person>(dbPerson, HttpStatus.OK);
    }

    @GetMapping(value = "/getPerson/{id}")
    public ResponseEntity<Person> getEmployee(@PathVariable("id") Long id) throws Exception{

        if (id==null){
            throw new NullPointerException("Person Id cannot be null");
        }
        Person dbPerson = personService.findOne(id);
        return new ResponseEntity<Person>(dbPerson, HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Person> update(@RequestBody Person person) throws Exception{

        if (person==null){
            throw new NullPointerException("Person object cannot be null");
        }
        person.setUpdatedDate(new Date());
        Person dbPerson = personService.update(person);
        return new ResponseEntity<Person>(dbPerson, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) throws Exception{

        if (id==null){
            throw new NullPointerException("Person Id cannot be null");
        }
        personService.delete(id);
        return new ResponseEntity<String>("Person is deleted", HttpStatus.OK);
    }
}
