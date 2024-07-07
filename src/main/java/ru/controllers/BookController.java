package ru.controllers;

import jakarta.validation.Valid;
import ru.models.Book;
import ru.models.Page;
import ru.models.Person;
import ru.services.BooksService;
import ru.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    PeopleService peopleService;
    BooksService booksService;
    Page webPage;

    @Autowired
    public BookController(PeopleService peopleService, BooksService booksService, Page page) {
        this.peopleService = peopleService;
        this.booksService = booksService;
        this.webPage = page;
    }

    @GetMapping()
    public String show(@RequestParam(value = "page", required = false) Integer page,
                       @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                       @RequestParam(value = "sort_by_year", required = false) Boolean sortByYear,
                       Model model) {
        if (page == null) {
            page = 1;
            booksPerPage = 3;
        }

        webPage.setPage(page);
        webPage.setBooksPerPage(booksPerPage);
        webPage.setSortByYear(sortByYear != null);

        model.addAttribute("books", webPage.findBooksOnPage(booksService.findAll()));
        model.addAttribute("page", page);
        return "books/show";
    }

    @GetMapping("/{id}")
    public String showBook(@ModelAttribute("person") Person person, @PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findOne(id));
        model.addAttribute("people", peopleService.findAll());
        model.addAttribute("owner", booksService.findOwnerByBookId(id));

        return "books/showBook";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id) {
        booksService.releaseBook(id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/add-owner")
    public String addOwner(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        booksService.setOwner(person, id);

        return "redirect:/books/{id}";
    }

    @GetMapping("/new")
    public String addBook(@ModelAttribute("book") Book book) {
        return "books/newBook";
    }

    @PostMapping("/new")
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/newBook";

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute(booksService.findOne(id));
        return "books/editBook";
    }

    @PatchMapping("/{id}/edit")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/editBook";

        booksService.update(book, book.getId());
        return "redirect:/books";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteBook(@ModelAttribute("book") Book book) {
        booksService.delete(book.getId());
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String searchPage(@ModelAttribute("request") String request) {
        return "books/search";
    }

    @GetMapping("/search/{request}")
    public String searchResult(@RequestParam("request") String request, Model model) {
        model.addAttribute("result", booksService.searchBookByTitle(request));
        model.addAttribute("request", request);
        return "books/search";
    }
}
