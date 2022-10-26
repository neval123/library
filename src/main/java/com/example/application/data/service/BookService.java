package com.example.application.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.application.data.entity.Book;
import com.example.application.data.entity.Person;

@Service
public class BookService {
	 private final BookRepository repository;

	 @Autowired
	 public BookService(BookRepository repository) {
	    this.repository = repository;
	 }
	 
	 public void modify(int amount, int id) {
		 repository.setUserInfoById(amount, id);
	 }
	 
	 public Book update(Book entity) {
	    return repository.save(entity);
	 }	 
	 
	 public Page<Book> list(Pageable pageable) {
	    return repository.findAll(pageable);
	 }
	 
	 public List<Book> getBooks() {
	     return repository.findAll();
	 }
}
