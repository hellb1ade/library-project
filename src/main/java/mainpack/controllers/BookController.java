package mainpack.controllers;

import mainpack.dao.BookDao;
import mainpack.dao.PersonDao;
import mainpack.models.Book;
import mainpack.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@ComponentScan("dao")
@RequestMapping("/books")
public class BookController {

    private final BookDao bookDao;
    private final PersonDao personDao;

    public BookController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping()
    public String show(Model model) {
        model.addAttribute("books", bookDao.getBooks());
        return "books/show";
    }

    @GetMapping("/{id}")
    public String showBook(@ModelAttribute("person") Person person, @PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.getBook(id));
        model.addAttribute("people", personDao.getPeople());
        model.addAttribute("owner", bookDao.getOwner(id));

        return "books/showBook";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id) {
        bookDao.releaseBook(id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/add-owner")
    public String addOwner(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        bookDao.setOwner(person, id);

        return "redirect:/books/{id}";
    }

    @GetMapping("/new")
    public String addBook() {
        return "books/newBook";
    }

    @PostMapping("/new")
    public String createBook(@ModelAttribute("book") Book book) {
        bookDao.addBook(book);

        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute(bookDao.getBook(id));

        return "books/editBook";
    }

    @PatchMapping("/{id}/edit")
    public String updateBook(@ModelAttribute("book") Book book) {
        bookDao.updateBook(book);

        return "redirect:/books";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteBook(@ModelAttribute("book") Book book) {
        bookDao.deleteBook(book);

        return "redirect:/books";
    }

}
