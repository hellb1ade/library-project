package ru.repositories;

import org.springframework.data.jpa.repository.Query;
import ru.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByNameContaining(String request);

    @Query(value = "select * from Book order by year_of_publication desc", nativeQuery = true)
    List<Book> findAllWithSort();
}
