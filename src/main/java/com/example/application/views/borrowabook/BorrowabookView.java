package com.example.application.views.borrowabook;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.data.entity.Book;
import com.example.application.data.entity.Borrow;
import com.example.application.data.entity.Person;
import com.example.application.data.service.BookService;
import com.example.application.data.service.BorrowService;
import com.example.application.data.service.PersonService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Borrow a book")
@Route(value = "borrow", layout = MainLayout.class)
public class BorrowabookView extends VerticalLayout {

	private final BookService bookService;
	private final PersonService personService;
	private final BorrowService borrowService;
	private boolean alreadyChosen = false;
	H2 message = new H2();
	Button borrowReserve;
	private boolean canBorrow = true;
	Checkbox checkbox = new Checkbox("Reservation?");
	DatePicker chooseDate = new DatePicker("Choose a date");
	LocalDate available;
	@Autowired
	public BorrowabookView(BookService bookService, PersonService personService, BorrowService borrowService) {
		this.bookService = bookService;
		this.personService = personService;
		this.borrowService = borrowService;
		setSpacing(false);
		add(new H2("Choose a client:"));
		ComboBox<Person> comboboxP = new ComboBox<>("Client");
		comboboxP.setItems(personService.getPeople());
		comboboxP.setItemLabelGenerator(Person::getFullName);
		add(comboboxP);
		add(new H2("Choose an available book:"));
		ComboBox<Book> combobox = new ComboBox<>("Book");
		List<Book> books = bookService.getBooks();
		borrowReserve = new Button("Borrow/Reserve");
		borrowReserve.setEnabled(true);
//        List<Book> booksAvailable = books.stream().filter(b -> b.getAmount() > 0).collect(Collectors.toList());
		combobox.setItems(books);
		LocalDate currentTime = LocalDate.now();
//        books.stream().forEach(e->{
//        	
//       });
//		LocalDate sooner = LocalDate.of(0, 0, 0);
		combobox.setItemLabelGenerator(Book::getName);
		combobox.addValueChangeListener(e -> {
			Book book = e.getValue();
			List<Borrow> borw = borrowService.findBookById(book);
//			Borrow bor = new Borrow();
//			sooner = borw.get(0).getTo();
			Borrow bor = borw.stream().min(Comparator.comparing(Borrow::getTo)).orElse(null);
			

			if (bor == null) {
				canBorrow = true;
				message.setText("This book is currently available!");
//				borrowReserve.setEnabled(true);
			} else {
				int am = book.getAmount();
				String date = bor.getTo().toString();
				if (am == 0) {
					if(checkbox.getValue().equals(true)&&chooseDate.getValue().isAfter(bor.getTo())) {
						borrowReserve.setEnabled(true);
						canBorrow = true;
					}else {
						canBorrow = false;
						message.setText("This book will be available on " + date);
						borrowReserve.setEnabled(true);
						available = bor.getTo();
					}
				} else {
					borrowReserve.setEnabled(true);
					canBorrow = true;
					message.setText("This book is currently available!");
				}
			}
			if (!alreadyChosen) {
				alreadyChosen = true;
				add(message);
			}
/*			if (canBorrow) {
				if (checkbox.getValue().equals(false)) {
					if (chooseDate.getValue().equals(currentTime)) {
						borrowReserve.setEnabled(true);
					} else {
						borrowReserve.setEnabled(false);
					}
				} else {
					if (chooseDate.getValue().isAfter(currentTime)) {
						borrowReserve.setEnabled(true);
					} else {
						borrowReserve.setEnabled(false);
					}
				}
			}*/
		});

		add(combobox);
//		Checkbox checkbox = new Checkbox("Reservation?");
		add(checkbox);
//		LocalDate currentTime = LocalDate.now();
		/*
		 * DatePicker datePicker = new DatePicker("Reservation date");
		 * datePicker.setInitialPosition(currentTime);
		 * datePicker.addValueChangeListener(e->{) Date date = datePicker.getva });
		 * add(date);
		 */
		
//        DatePicker returnDate = new DatePicker("Return date");
	//	DatePicker chooseDate = new DatePicker("Choose a date");
		borrowReserve.addClickListener(e -> {
			Borrow bor = new Borrow();
			Book book = combobox.getValue();
			Person person = comboboxP.getValue();						
			
			
			
			if(chooseDate.getValue().isAfter(currentTime) && checkbox.getValue().equals(true)&&book.getAmount()>0) {
				bor.setFrom(chooseDate.getValue());
				int time = book.getBorrowTime();
				LocalDate d = chooseDate.getValue().plusDays(time);
				bor.setTo(d);
				bor.setBookName(book.getName());
				bor.setBook(book);
				bor.setPerson(person);
				bor.setEnded(false);
				bor.setFee(0);
				bor.setReservation(true);
				int amount = book.getAmount() - 1;
				int id = book.getId();
				// book.setAmount(amount - 1);
				bookService.modify(amount, id);
				borrowService.update(bor);
				message.setText("Reservation succeded!");
			}else if((chooseDate.getValue().atStartOfDay().isEqual(currentTime.atStartOfDay()) || 
					chooseDate.getValue().isBefore(currentTime)) && checkbox.getValue().equals(false)&&
					book.getAmount()>0){
				bor.setFrom(chooseDate.getValue());
				int time = book.getBorrowTime();
				LocalDate d = chooseDate.getValue().plusDays(time);
				bor.setTo(d);
				bor.setBookName(book.getName());
				bor.setBook(book);
				bor.setPerson(person);
				bor.setEnded(false);
				bor.setFee(0);
				bor.setReservation(false);
				int amount = book.getAmount() - 1;
				int id = book.getId();
				// book.setAmount(amount - 1);
				bookService.modify(amount, id);
				borrowService.update(bor);
				message.setText("Book borrowed!");
			}else if(chooseDate.getValue().isAfter(available) && checkbox.getValue().equals(true)
					&&book.getAmount()>0){
				bor.setFrom(chooseDate.getValue());
				int time = book.getBorrowTime();
				LocalDate d = chooseDate.getValue().plusDays(time);
				bor.setTo(d);
				bor.setBookName(book.getName());
				bor.setBook(book);
				bor.setPerson(person);
				bor.setEnded(false);
				bor.setFee(0);
				bor.setReservation(false);
				int amount = book.getAmount() - 1;
				int id = book.getId();
				// book.setAmount(amount - 1);
				bookService.modify(amount, id);
				borrowService.update(bor);
				message.setText("Reservation done!");
			}else {
				message.setText("You have entered wrong values!");
			}
	/*		Borrow bor = new Borrow();
			Book book = combobox.getValue();
			Person person = comboboxP.getValue();
			bor.setFrom(chooseDate.getValue());
			int time = book.getBorrowTime();
			LocalDate d = chooseDate.getValue().plusDays(time);
			bor.setTo(d);
			if (checkbox.getValue().equals(true)) {
				bor.setReservation(true);
			} else {
				bor.setReservation(false);
			}
			bor.setBookName(book.getName());
			bor.setBook(book);
			bor.setPerson(person);
			bor.setEnded(false);
			bor.setFee(0);
			int amount = book.getAmount() - 1;
			int id = book.getId();
			// book.setAmount(amount - 1);
			bookService.modify(amount, id);
			borrowService.update(bor);*/
		});
/*		chooseDate.addValueChangeListener(e -> {
			
			if (canBorrow) {
				if (checkbox.getValue().equals(false)) {
					if (e.getValue().equals(currentTime)) {
						borrowReserve.setEnabled(true);
					} else {
						borrowReserve.setEnabled(false);
					}
				} else {
					if (e.getValue().isAfter(currentTime)) {
						borrowReserve.setEnabled(true);
					} else {
						borrowReserve.setEnabled(false);
					}
				}
			}
//        	returnDate.setMin(e.getValue());
		});*/
//        returnDate.addValueChangeListener(e -> departureDate.setMax(e.getValue()));
		add(chooseDate);
		add(borrowReserve);
//        add(returnDate);
		setSizeFull();
//        setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		getStyle().set("text-align", "center");
	}

}
