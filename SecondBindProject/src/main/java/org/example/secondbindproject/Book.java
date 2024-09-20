package org.example.secondbindproject;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

// Book Model Class
public class Book {

    // Variable Fields & Constructor (JavaFX Properties)
    private final SimpleStringProperty title;
    private final SimpleStringProperty author;
    private final SimpleStringProperty genre;
    private final SimpleStringProperty date;
    private final SimpleDoubleProperty isbn;


    public Book(String title, String author, String genre, String date, Double isbn) {
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.genre = new SimpleStringProperty(genre);
        this.date = new SimpleStringProperty(date);
        this.isbn = new SimpleDoubleProperty(isbn);
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public SimpleStringProperty genreProperty() {
        return genre;
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public SimpleDoubleProperty isbnProperty() {
        return isbn;
    }

    // Getters
    public SimpleStringProperty getAuthor() {
        return author;
    }

    public SimpleStringProperty getDate() {
        return date;
    }

    public SimpleStringProperty getGenre() {
        return genre;
    }

    public SimpleDoubleProperty getIsbn() {
        return isbn;
    }

    public SimpleStringProperty getTitle() {
        return title;
    }
}
