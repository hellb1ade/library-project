package ru.services;

import ru.models.Book;
import ru.models.Person;
import ru.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> optionalBook = booksRepository.findById(id);
        return optionalBook.orElse(null);
    }

    public Person findOwnerByBookId(int bookId) {
        Person person;
        try {
            person = booksRepository.findById(bookId).orElse(null).getPerson();
        } catch (NullPointerException e) {
            return null;
        }
        return person;
    }

    @Transactional
    public void releaseBook(int id) {
        Objects.requireNonNull(booksRepository.findById(id).orElse(null)).setPerson(null);
    }

    @Transactional
    public void setOwner(Person person, int id) {
        Objects.requireNonNull(booksRepository.findById(id).orElse(null)).setPerson(person);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(Book book, int id) {
        book.setId(id);
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public List<Book> getTakenBooks(int id) {
        return booksRepository.findAll().stream().filter(book -> book.getPerson() != null).filter(book -> book.getPerson().getId() == id).toList();
    }

    public List<Book> searchBookByTitle(String title) {
        return booksRepository.findByNameContaining(title);
    }
}
