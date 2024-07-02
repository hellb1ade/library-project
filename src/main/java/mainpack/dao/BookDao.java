package mainpack.dao;

import mainpack.mappers.BookMapper;
import mainpack.mappers.PersonMapper;
import mainpack.models.Book;
import mainpack.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDao {

    JdbcTemplate template;

    @Autowired
    public BookDao(JdbcTemplate template) {
        this.template = template;
    }

    public List<Book> getBooks() {
        return template.query("select * from Book", new BookMapper());
    }

    public void addBook(Book book) {
        template.update("insert into Book (book_name, book_author, year_of_publication) values (?, ?, ?)", book.getName(), book.getAuthor(), book.getYear());
    }

    public Book getBook(int id) {
        return template.query("select * from Book where book_id = ?", new Object[]{id}, new BookMapper()).stream().findAny().orElse(null);
    }

    public void updateBook(Book book) {
        template.update("update Book set book_name = ?, book_author = ?, year_of_publication = ? where book_id = ?", book.getName(), book.getAuthor(), book.getYear(), book.getId());
    }

    public void deleteBook(Book book) {
        template.update("delete from Book where book_id = ?", book.getId());
    }
}
