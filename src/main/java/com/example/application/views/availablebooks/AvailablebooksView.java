package com.example.application.views.availablebooks;

import com.example.application.data.entity.Book;
import com.example.application.data.entity.Person;
import com.example.application.data.service.BookService;
import com.example.application.data.service.PersonService;
import com.example.application.views.MainLayout;
import com.vaadin.collaborationengine.CollaborationAvatarGroup;
import com.vaadin.collaborationengine.CollaborationBinder;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Available books")
@Route(value = "books/:samplePersonID?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
public class AvailablebooksView extends Div{

    private Grid<Book> grid = new Grid<>(Book.class, false);

    private TextField name;
	private TextField author;
	private TextField amount;
	private TextField borrowTime;
//    private Button cancel = new Button("Cancel");
//    private Button save = new Button("Save");
    List<Book> books = new ArrayList<>();

    private Book book;
    private final BookService bookService;

  
    @Autowired
    public AvailablebooksView(BookService bookService) {
        this.bookService = bookService;
        addClassNames("availablebooks-view");

      
        SplitLayout splitLayout = new SplitLayout();


        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        grid.addColumn("name").setAutoWidth(true);
        grid.addColumn("author").setAutoWidth(true);
        grid.addColumn("amount").setAutoWidth(true);
        grid.addColumn("borrowTime").setAutoWidth(true);
        grid.setItems(bookService.getBooks());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
 
    }


    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();

        name = new TextField("Book Name");
        author = new TextField("Author");
        amount = new TextField("Amount");
        borrowTime = new TextField("Borrow Time");
        Component[] fields = new Component[]{name, author, amount, borrowTime};
        formLayout.add(fields);

        splitLayout.addToSecondary(editorLayoutDiv);
    }


    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getLazyDataView().refreshAll();
    }


}