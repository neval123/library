package com.example.application.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {// extends AbstractEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="Name")
	private String name;
	@Column(name="Author")
	private String author;
	@Column(name="Amount")
	private int amount;
	@Column(name="Time")
	private int borrowTime;
	
	public int getId() {
		return this.id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getBorrowTime() {
		return borrowTime;
	}
	public void setBorrowTime(int borrowTime) {
		this.borrowTime = borrowTime;
	}
}
