package com.example.application.data.entity;


import java.time.LocalDate;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Borrow {// extends AbstractEntity{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
/*	@Column(name="Book_Name")
	private String bookName;*/
	@Column(name="Reservation")
	private boolean reservation;
/*	@Column(name="Client_Name")
	private String clientName;*/
	//@Column(name="From", columnDefinition = "DATE")
	//@DateTimeFormat(pattern="yyy-MM-dd")
	@Column(name="Start_Time")
	//@Temporal(TemporalType.DATE)
//	public java.sql.Date from;
	private LocalDate from;
	//@Column(name="To", columnDefinition = "DATE")
	//@DateTimeFormat(pattern="yyy-MM-dd")
	@Column(name="End_Time")
	//@Temporal(TemporalType.DATE)
//	public java.sql.Date to;
	private LocalDate to;
	@Column(name="Ended")
	private boolean ended;
	@Column(name="Fee")
	private long fee;
	@ManyToOne
	private Person person;
	@OneToOne
	private Book book;
	@Column(name="Book_name")
	private String bookName;
	/*	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}*/
	public int getId() {
		return this.id;
	}
	
	public boolean isReservation() {
		return this.reservation;
	}
	public void setReservation(boolean reservation) {
		this.reservation = reservation;
	}
	
	public boolean isEnded() {
		return this.ended;
	}
	public void setEnded(boolean ended) {
		this.ended = ended;
	}
	
	public long getFee() {
		return fee;
	}
	public void setFee(long fee) {
		this.fee = fee;
	}
	/*	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}*/
	public LocalDate getFrom() {
		return from;
	}
	public void setFrom(LocalDate from) {
		this.from = from;
	}
	public LocalDate getTo() {
		return to;
	}
	public void setTo(LocalDate to) {
		this.to = to;
	}	
	public Person getPerson() {
		return this.person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Book getBook() {
		return this.book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public String getBookName() {
		return this.bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
}
