package mainpack.controllers;

import mainpack.dao.BookDao;
import mainpack.models.Book;
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

    @Autowired
    public BookController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @GetMapping()
    public String show(Model model) {
        model.addAttribute("books", bookDao.getBooks());
        return "books/show";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable int id, Model model) {
        model.addAttribute("book", bookDao.getBook(id));
        return "books/showBook";
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
