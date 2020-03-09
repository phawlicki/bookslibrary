package pl.atos.persistence;


import org.junit.Before;
import org.junit.Test;
import pl.atos.logic.BookFactory;
import pl.atos.model.Author;
import pl.atos.model.Book;
import pl.atos.model.Status;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InMemoryDatabaseImplTest {
    InMemoryDatabaseImpl inMemoryDatabase = new InMemoryDatabaseImpl();
    BookFactory bookFactory = new BookFactory(inMemoryDatabase);

    List<Book> savedBooks = new ArrayList<>();


    @Before
    public void setup() {
        if (!savedBooks.isEmpty()) {
            savedBooks.clear();
        }
    }

    @Test
    public void shouldSaveBook() {
        //given
        Book book = bookFactory.create("W pustyni i w puszczy", new Author("Henryk", "Sienkiewicz"), Status.FREE, Year.of(1890), null);

        //when
        inMemoryDatabase.add(book);
        savedBooks = inMemoryDatabase.getAllBooks();

        //then
        assertTrue(savedBooks.contains(book));
    }

    @Test
    public void shouldRemoveBook() {
        //given
        Book book = bookFactory.create("W pustyni i w puszczy", new Author("Henryk", "Sienkiewicz"), Status.FREE, Year.of(1890), null);
        inMemoryDatabase.add(book);
        Book book1 = bookFactory.create("Harry Potter", new Author("J.K", "Rowling"), Status.FREE, Year.of(2000), null);
        inMemoryDatabase.add(book1);

        //when
        inMemoryDatabase.remove(1);
        savedBooks = inMemoryDatabase.getAllBooks();

        //then
        assertTrue(savedBooks.size() == 1);
        assertTrue(savedBooks.contains(book1));
    }

    @Test
    public void shouldTestRemoveBookWhenLoan() {
        //given
        Book book = bookFactory.create("W pustyni i w puszczy", new Author("Henryk", "Sienkiewicz"), Status.LOAN, Year.of(1890), null);
        inMemoryDatabase.add(book);
        Book book1 = bookFactory.create("Harry Potter", new Author("J.K", "Rowling"), Status.FREE, Year.of(2000), null);
        inMemoryDatabase.add(book1);

        //when
        inMemoryDatabase.remove(1);
        savedBooks = inMemoryDatabase.getAllBooks();

        //then
        assertTrue(savedBooks.size() == 2);
        assertTrue(savedBooks.contains(book1) && savedBooks.contains(book));
    }


    @Test
    public void shouldGetBooksByAuthor() {
        //given
        Author author = new Author("Henryk", "Sienkiewicz");
        Book book = bookFactory.create("W pustyni i w puszczy", author, Status.LOAN, Year.of(1890), null);
        inMemoryDatabase.add(book);
        Book book1 = bookFactory.create("Harry Potter", new Author("J.K", "Rowling"), Status.FREE, Year.of(2000), null);
        inMemoryDatabase.add(book1);

        //when
        List<Book> retrievedBooks = inMemoryDatabase.getBooksByAuthor(author);

        //then
        assertEquals(book.getId(), retrievedBooks.get(0).getId());
    }

    @Test
    public void shouldGetBooksByYear() {
        //given
        Author author = new Author("Henryk", "Sienkiewicz");
        Book book = bookFactory.create("W pustyni i w puszczy", author, Status.LOAN, Year.of(1890), null);
        inMemoryDatabase.add(book);
        Book book1 = bookFactory.create("Harry Potter", new Author("J.K", "Rowling"), Status.FREE, Year.of(2000), null);
        inMemoryDatabase.add(book1);
        Book book2 = bookFactory.create("Harry Potter", new Author("J.K", "Rowling"), Status.FREE, Year.of(2000), null);
        inMemoryDatabase.add(book2);

        //when
        List<Book> retrievedBooks = inMemoryDatabase.getBooksByYear(Year.of(2000));

        //then
        assertEquals(book1.getId(), retrievedBooks.get(0).getId());
        assertTrue(retrievedBooks.size() == 2);
    }

    @Test
    public void shouldGetBooksByTitle() {
        //given
        Author author = new Author("Henryk", "Sienkiewicz");
        Book book = bookFactory.create("W pustyni i w puszczy", author, Status.LOAN, Year.of(1890), null);
        inMemoryDatabase.add(book);
        Book book1 = bookFactory.create("Harry Potter", new Author("J.K", "Rowling"), Status.FREE, Year.of(2000), null);
        inMemoryDatabase.add(book1);
        Book book2 = bookFactory.create("Harry Potter", new Author("J.K", "Rowling"), Status.FREE, Year.of(2000), null);
        inMemoryDatabase.add(book2);

        //when
        List<Book> retrievedBooks = inMemoryDatabase.getBooksByTitle("Harry Potter");

        //then
        assertEquals(book1.getId(), retrievedBooks.get(0).getId());
        assertTrue(retrievedBooks.size() == 2);
    }

    @Test
    public void shouldGetBooksByTitleAndAuthor() {
        //given
        Author author = new Author("Henryk", "Sienkiewicz");
        Book book = bookFactory.create("W pustyni i w puszczy", author, Status.LOAN, Year.of(1890), null);
        inMemoryDatabase.add(book);
        Book book1 = bookFactory.create("Harry Potter", new Author("J.K", "Rowling"), Status.FREE, Year.of(2000), null);
        inMemoryDatabase.add(book1);
        Book book2 = bookFactory.create("Harry Potter", author, Status.FREE, Year.of(2000), null);
        inMemoryDatabase.add(book2);

        //when
        List<Book> retrievedBooks = inMemoryDatabase.getBooksByTitleAndAuthor("Harry Potter", author);

        //then
        assertEquals(book2.getId(), retrievedBooks.get(0).getId());
        assertEquals(book2.getAuthor(), retrievedBooks.get(0).getAuthor());
        assertEquals(book2.getTile(), retrievedBooks.get(0).getTile());
        assertTrue(retrievedBooks.size() == 1);
    }

    @Test
    public void shouldGetDistinctBooks() {
        //given
        Author author = new Author("Henryk", "Sienkiewicz");
        Book book = bookFactory.create("W pustyni i w puszczy", author, Status.LOAN, Year.of(1890), null);
        inMemoryDatabase.add(book);
        Book book1 = bookFactory.create("Harry Potter", new Author("J.K", "Rowling"), Status.FREE, Year.of(2000), null);
        inMemoryDatabase.add(book1);
        Book book2 = bookFactory.create("Harry Potter", author, Status.FREE, Year.of(2000), null);
        inMemoryDatabase.add(book2);

        //when
        List<Book> retrievedBooks = inMemoryDatabase.getDistinctBooks();

        //then
        assertEquals(book.getTile(), retrievedBooks.get(0).getTile());
        assertEquals(book1.getTile(), retrievedBooks.get(1).getTile());
        assertTrue(retrievedBooks.size() == 2);
    }

    @Test
    public void shouldGetById() {
        //given
        Author author = new Author("Henryk", "Sienkiewicz");
        Book book = bookFactory.create("W pustyni i w puszczy", author, Status.LOAN, Year.of(1890), null);
        inMemoryDatabase.add(book);
        Book book1 = bookFactory.create("Harry Potter", new Author("J.K", "Rowling"), Status.FREE, Year.of(2000), null);
        inMemoryDatabase.add(book1);
        Book book2 = bookFactory.create("Harry Potter", author, Status.FREE, Year.of(2000), null);
        inMemoryDatabase.add(book2);

        //when
        Book retrievedBook = inMemoryDatabase.getBookById(3);

        //then
        assertEquals(book2.getId(), retrievedBook.getId());
        assertEquals(book2.getStatus(), retrievedBook.getStatus());
    }

    @Test
    public void shouldPrintBooks() {
        //given
        Author author = new Author("Henryk", "Sienkiewicz");
        Book book = bookFactory.create("W pustyni i w puszczy", author, Status.LOAN, Year.of(1890), null);
        inMemoryDatabase.add(book);
        Book book1 = bookFactory.create("Harry Potter", new Author("J.K", "Rowling"), Status.FREE, Year.of(2000), null);
        inMemoryDatabase.add(book1);
        Book book2 = bookFactory.create("Harry Potter", author, Status.FREE, Year.of(2000), null);
        inMemoryDatabase.add(book2);
        Book book3 = bookFactory.create("W pustyni i w puszczy", author, Status.FREE, Year.of(1890), null);
        inMemoryDatabase.add(book);

        //when
        Book retrievedBook = inMemoryDatabase.getBookById(3);

        //then
        assertEquals(book2.getId(), retrievedBook.getId());
        assertEquals(book2.getStatus(), retrievedBook.getStatus());
    }

}