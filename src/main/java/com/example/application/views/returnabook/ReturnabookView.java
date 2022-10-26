package com.example.application.views.returnabook;

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
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Return a book")
@Route(value = "return", layout = MainLayout.class)
public class ReturnabookView extends VerticalLayout {

	private final BookService bookService;
	private final PersonService personService;
	private final BorrowService borrowService;
	private H2 info;
	private List<Book> books;
	private boolean created = false;
	private TextField name = new TextField("Book's title");
	private TextField fee = new TextField("Fee");
	private TextField to = new TextField("To");
	private Button returnBook = new Button("Return");
	private boolean alreadyChosen = false;

	@Autowired
	public ReturnabookView(BookService bookService, PersonService personService, BorrowService borrowService) {
		this.bookService = bookService;
		this.personService = personService;
		this.borrowService = borrowService;
		setSpacing(false);

		ComboBox<Person> comboboxP = new ComboBox("Clients");
		comboboxP.setItems(personService.getPeople());
		comboboxP.setItemLabelGenerator(Person::getFullName);
		ComboBox<Borrow> comboboxB = new ComboBox("Borrows");

		comboboxP.addValueChangeListener(e -> {
			Person per = e.getValue();
			List<Borrow> borrows = borrowService.findPersonById(per);// .stream().filter(x->x.getPerson().equals(per)).collect(Collectors.toList());
			// books = borrows.stream().map(a->a.getBook()).collect(Collectors.toList());
			comboboxB.clear();
			List<Borrow> unfinishedBorrows = borrows.stream().filter(x->x.isEnded()==false).collect(Collectors.toList());

			comboboxB.setItems(unfinishedBorrows);
			comboboxB.setItemLabelGenerator(Borrow::getBookName);
			if (!created) {
				created = true;
				add(comboboxB);
			}
		});

		comboboxB.addValueChangeListener(e -> {
			Borrow bs = e.getValue();
			if (!(bs==null)) {				
				String n = bs.getBookName();
				name.setValue(n);
				fee.setValue(Long.toString(bs.getFee()));
				to.setValue(bs.getTo().toString());
				name.setEnabled(false);
				fee.setEnabled(false);
				to.setEnabled(false);
				if (!alreadyChosen) {
					alreadyChosen = true;
					add(name);
					add(fee);
					add(to);
					add(returnBook);
				}
			} else {
				name.setValue("None");
				fee.setValue("None");
				to.setValue("None");
			}
		});
		returnBook.addClickListener(e -> {
			Person p = comboboxP.getValue();
			Borrow b = comboboxB.getValue();
			if (!(p==null) && !(b==null)){//(!p.equals(null) && !b.equals(null)) {
				int borrowId = b.getId();
				borrowService.setEnded(borrowId);
				Book book = borrowService.getBookById(borrowId);
				int am = book.getAmount() + 1;
				int bookId = book.getId();
				bookService.modify(am, bookId);
			}
		});
		add(new H2("Choose a client:"));
		add(comboboxP);

		setSizeFull();
//      setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		getStyle().set("text-align", "center");
	}
}
