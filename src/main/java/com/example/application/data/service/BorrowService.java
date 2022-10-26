package com.example.application.data.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.data.entity.Book;
import com.example.application.data.entity.Borrow;
import com.example.application.data.entity.Person;

@Service
public class BorrowService {
	
	private final BorrowRepository repository;

    @Autowired
    public BorrowService(BorrowRepository repository) {
        this.repository = repository;
    }
    
    public List<Borrow> findPersonById(Person person){
    	return repository.findAPerson(person);
    }
    public Optional<Borrow> get(Long id) {
        return repository.findById(id);
    }

    public Borrow update(Borrow entity) {
        return repository.save(entity);
    }
    
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public List<Borrow> getBorrows() {
        return repository.findAll();
    }
    
    public void setFeee(long fee, int id) {
    	repository.setFeeById(fee, id);
    }
    
    public List<Borrow> findBookById(Book book) {
    	return repository.findABook(book);
    }
    
    public void setEnded(int id) {
    	repository.setEndedByBorrow(id);
    }
    
    public Book getBookById(int id) {
    	return repository.findABook(id);
    }
}
