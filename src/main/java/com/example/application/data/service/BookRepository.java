package com.example.application.data.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.application.data.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
	@Transactional
	@Modifying
	@Query("UPDATE Book b SET b.amount = ?1 WHERE b.id = ?2")
	void setUserInfoById(int amount, int id);
}
