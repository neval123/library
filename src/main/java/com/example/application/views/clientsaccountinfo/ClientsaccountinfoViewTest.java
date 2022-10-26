package com.example.application.views.clientsaccountinfo;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.junit4.SpringRunner;

//import org.springframework.test.context.junit4.SpringRunner;
import com.example.application.data.entity.Book;
import com.example.application.data.entity.Borrow;
import com.example.application.data.entity.Person;
//import com.example.application.data.service.BorrowService;
import com.example.application.data.service.BorrowService;
import com.example.application.data.service.PersonService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;

//import org.springframework.boot.test.context.SpringBootTest;
//RunWith(SpringRunner.class)

public class ClientsaccountinfoViewTest {

//	@Autowired
//	private final BorrowService borrowService;
	private BorrowService borrowService = Mockito.mock(BorrowService.class);
	private PersonService personService = Mockito.mock(PersonService.class);
	@Autowired
	private ClientsaccountinfoView clientsaccountinfoView = new ClientsaccountinfoView(personService, borrowService);
	List<Borrow> borrows;
	@Before
	public void setUp() throws Exception{
		
		
//		clientsaccountinfoView = new ClientsaccountinfoView(personService, borrowService);
		borrows = new ArrayList();
		Borrow bor1 = new Borrow();
		bor1.setBook(new Book());
		bor1.setBookName("Test");
		bor1.setEnded(false);
		bor1.setFee(0);
		bor1.setFrom(LocalDate.of(2022, 8, 1));
		bor1.setTo(LocalDate.of(2022, 8, 8));
		bor1.setReservation(false);
		bor1.setPerson(new Person());
		borrows.add(bor1);
		Borrow bor2 = new Borrow();
		bor2.setBook(new Book());
		bor2.setBookName("Test2");
		bor2.setEnded(false);
		bor2.setFee(0);
		bor2.setFrom(LocalDate.of(2022, 8, 1));
		bor2.setTo(LocalDate.of(2022, 8, 2));
		bor2.setReservation(false);
		bor2.setPerson(new Person());
		borrows.add(bor2);
	}
	@Test
	public void testSetFee() {
		clientsaccountinfoView.setFee(borrows);
	//	borrows.forEach(e->System.out.println(e.getFee()));
	//	fail("Not yet implemented");
	}
	@Test
	public void buttonShownOnComboboxValueChange() {
		
		Grid<Borrow> gridTest = clientsaccountinfoView.grid;
		Set<Borrow> borrow = gridTest.getSelectedItems();
		TextField start = clientsaccountinfoView.start;
		Assert.assertFalse(start.isAttached());
	}

}
