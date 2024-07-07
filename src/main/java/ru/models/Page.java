package ru.models;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class Page {

    private int page;
    private int booksPerPage;
    private Boolean sortByYear;

    public Page() { }

    public Page(int page, int booksPerPage, boolean sortByYear) {
        this.page = page;
        this.booksPerPage = booksPerPage;
    }

    public List<Book> findBooksOnPage(List<Book> allBooks) {
        List<Book> result = new ArrayList<>();
        int pointer = (page - 1) * booksPerPage;
        int number = booksPerPage;

        for (int i = pointer; i < allBooks.size(); i++) {
            if (number != 0) {
                result.add(allBooks.get(i));
                number--;
            } else
                break;
        }

        if (sortByYear)
            result.sort((o1, o2) -> o2.getYear() - o1.getYear());

        return result;
    }

    public boolean isSortByYear() {
        return sortByYear;
    }

    public void setSortByYear(boolean sortByYear) {
        this.sortByYear = sortByYear;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getBooksPerPage() {
        return booksPerPage;
    }

    public void setBooksPerPage(int booksPerPage) {
        this.booksPerPage = booksPerPage;
    }
}
