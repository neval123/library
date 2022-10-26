package com.example.application.views.clientsaccountinfo;

import java.time.Duration;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.application.data.entity.Book;
import com.example.application.data.entity.Borrow;
import com.example.application.data.entity.Person;
import com.example.application.data.service.BorrowService;
import com.example.application.data.service.PersonService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.example.application.data.entity.Person;
import com.vaadin.flow.component.textfield.TextField;


@Component
@Scope("prototype")
@PageTitle("Client's account info")
@Route(value = "account", layout = MainLayout.class)
public class ClientsaccountinfoView extends VerticalLayout {

	private TextField id = new TextField("Id");
	private TextField email = new TextField("Email");
	private TextField name = new TextField("Name");
	private TextField surname = new TextField("Surname");
	TextField start = new TextField("Start");
	private TextField end = new TextField("End");
	private TextField bookName = new TextField("Book name");
	private TextField fee = new TextField("Fee");
	private TextField ended = new TextField("Ended");
	private final PersonService personService;
	private final BorrowService borrowService;
	Grid<Borrow> grid = new Grid<>(Borrow.class, false);
	private boolean pAlreadyChosen = false;
	private LocalDate checkDate;
	Logger logger = LoggerFactory.getLogger(ClientsaccountinfoView.class);
//	private Button returnBook;
	@Autowired
	public ClientsaccountinfoView(PersonService personService, BorrowService borrowService) {
		this.personService = personService;
		this.borrowService = borrowService;
		setSpacing(false);
		
//        Image img = new Image("images/empty-plant.png", "placeholder plant");
//        img.setWidth("200px");
//        add(img);
		
		List<Borrow> checkBow = borrowService.getBorrows();
		setFee(checkBow);//, borrowService);
/*		checkBow.stream().forEach(e->{
			LocalDate now = LocalDate.now();
			LocalDate to = e.getTo();
			Duration diff = Duration.between(to.atStartOfDay(), now.atStartOfDay());
			Long diffDays = diff.toDays();
			Person person = e.getPerson();
			if(e.getTo().isBefore(now)&&e.isEnded()==false) {
				borrowService.setFeee(diffDays, person);
			}
			
		});*/
		ComboBox<Borrow> comboboxB = new ComboBox<>("Borrowed books");
		comboboxB.addValueChangeListener(e -> {

			Borrow borrow = e.getValue();
			Book book = borrow.getBook();
			String startDate = borrow.getFrom().toString();
			start.setValue(startDate);
			String endDate = borrow.getTo().toString();
			end.setValue(endDate);
			bookName.setValue(book.getName());
			start.setEnabled(false);
			end.setEnabled(false);
			bookName.setEnabled(false);
			String f = Long.toString(borrow.getFee());
			fee.setValue(f);
			String end = Boolean.toString(borrow.isEnded());
			ended.setValue(end);
			fee.setEnabled(false);
			ended.setEnabled(false);
		
			add(start);
			add(ended);
			add(bookName);
			add(ended);
			add(fee);
		});
		add(new H2("Choose a client:"));
		ComboBox<Person> comboboxP = new ComboBox<>("Client");
		comboboxP.setItems(personService.getPeople());
		comboboxP.setItemLabelGenerator(Person::getFullName);
		comboboxP.addValueChangeListener(e -> {
			// changeAccount(combobox,id,email,name,surname);
			Person person = e.getValue();
			Long idLon = person.getId();
			String idStr = Long.toString(idLon);
			id.setValue(idStr);
			id.setEnabled(false);
			email.setValue(person.getEmail());
			email.setEnabled(false);
			name.setValue(person.getFirstName());
			name.setEnabled(false);
			surname.setValue(person.getLastName());
			surname.setEnabled(false);
			List<Borrow> borrows = borrowService.findPersonById(person);
			grid.setItems(borrows);
			if (!pAlreadyChosen) {
				pAlreadyChosen = true;
				add(id);				
				add(email);				
				add(name);				
				add(surname);
				add(new H2("Borrowed books:"));
				grid.addColumn("bookName").setAutoWidth(true);
				grid.addColumn("reservation").setAutoWidth(true);
				grid.addColumn("from").setAutoWidth(true);
				grid.addColumn("to").setAutoWidth(true);
				grid.addColumn("fee").setAutoWidth(true);
				grid.addColumn("ended").setAutoWidth(true);
				
				
				/*
				 * grid.addColumn("Book name").setAutoWidth(true);
				 * grid.addColumn("Author").setAutoWidth(true);
				 * grid.addColumn("Start").setAutoWidth(true);
				 * grid.addColumn("End").setAutoWidth(true);
				 */
				
				
		//		List<Book> books = borrows.stream().map(a -> a.getBook()).collect(Collectors.toList());
			//	List<String> bookNames = books.stream().map(a -> a.getName()).collect(Collectors.toList());
			/*	if (borrows.isEmpty()) {
					add(new H3("Client didn't borrow any books"));
				} else {
					comboboxB.setItems(borrows);
//                comboboxB.setItemLabelGenerator(Book::getName);
					add(comboboxB);
				}*/
				add(grid);
			}
		});
//		returnBook.addClickListener(e->{
			
//		});
		add(comboboxP);
//        add(new Paragraph("It’s a place where you can grow your own UI 🤗"));

		setSizeFull();
		// setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		getStyle().set("text-align", "center");
	}
	
	public void setFee(List<Borrow> checkBow){// BorrowService borrowService) {
		checkBow.stream().forEach(e->{
			LocalDate now = LocalDate.now();
			LocalDate to = e.getTo();
			Duration diff = Duration.between(to.atStartOfDay(), now.atStartOfDay());
			Long diffDays = diff.toDays();
//			Person person = e.getPerson();
			if(e.getTo().isBefore(now)&&e.isEnded()==false) {
				borrowService.setFeee(diffDays, e.getId());
			}
			//System.out.println(diffDays + " " + e.getId());
			logger.info("Fee: " + diffDays + " Id: " + e.getId());
		});
	}
	/*
	 * public void changeAccount(ComboBox<Person> combobox, TextField id, TextField
	 * email, TextField name, TextField surname) {
	 * if(!combobox.getElement().equals(null)) { Person person =
	 * combobox.getValue(); Long idLon = person.getId(); String idStr =
	 * Long.toString(idLon); id.setValue(idStr); add(id);
	 * email.setValue(person.getEmail()); add(email);
	 * name.setValue(person.getFirstName()); add(name);
	 * surname.setValue(person.getLastName()); add(surname);
	 * 
	 * } }
	 */
}
