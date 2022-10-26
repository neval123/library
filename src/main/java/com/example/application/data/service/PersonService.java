package com.example.application.data.service;

import com.example.application.data.entity.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository repository;

    @Autowired
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Optional<Person> get(Long id) {
        return repository.findById(id);
    }

    public Person update(Person entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Person> list(Pageable pageable) {
        return repository.findAll(pageable);
    }
    public List<Person> getPeople() {
        return repository.findAll();
    }
    public int count() {
        return (int) repository.count();
    }

}
