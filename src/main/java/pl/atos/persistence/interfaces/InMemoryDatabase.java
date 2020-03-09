package pl.atos.persistence.interfaces;

import pl.atos.model.Author;
import pl.atos.model.Book;

import java.time.Year;
import java.util.List;

public interface InMemoryDatabase {
    void add(Book book);

    void remove(int id);

    List<Book> getBooksByAuthor(Author author);

    List<Book> getBooksByYear(Year year);

    List<Book> getBooksByTitle(String title);

    List<Book> getBooksByTitleAndAuthor(String title, Author author);

    List<Book> getDistinctBooks();

    Book getBookById(int id);

    int getLastId();

    List<Book> getAllBooks();

}



