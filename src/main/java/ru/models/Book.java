package ru.models;

import jakarta.validation.constraints.*;

import jakarta.persistence.*;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person person;

    @Column(name = "book_name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String name;

    @Column(name = "book_author")
    @NotEmpty(message = "Author should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String author;

    @Column(name = "year_of_publication")
    @Min(value = 0, message = "Year should be grater than 0")
    private int year;

    public Book () { }

    public Book(Person person, String name, String author, int year) {
        this.person = person;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
