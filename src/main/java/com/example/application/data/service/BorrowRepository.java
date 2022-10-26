package com.example.application.data.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.application.data.entity.Book;
import com.example.application.data.entity.Borrow;
import com.example.application.data.entity.Person;

public interface BorrowRepository extends JpaRepository<Borrow, Long>{
//	@Modifying
//	@Query("SELECT * FROM Borrow b WHERE b.amount = ?1 WHERE b.id = ?2")
//	void 
	@Query("SELECT b FROM Borrow b WHERE b.person = ?1")
	List<Borrow> findAPerson(Person person);
	@Transactional
	@Modifying
	@Query("UPDATE Borrow b SET b.fee = ?1 WHERE b.id = ?2")
	void setFeeById(long fee, int id);
	
	@Query("SELECT b FROM Borrow b WHERE b.book = ?1 ORDER BY b.to DESC")
	List<Borrow> findABook(Book book);
	
	@Query("SELECT b.book FROM Borrow b WHERE b.id = ?1")
	Book findABook(int id);
	
	@Transactional
	@Modifying
	@Query("UPDATE Borrow b SET b.ended = true WHERE b.id = ?1")
	void setEndedByBorrow(int id);
}
