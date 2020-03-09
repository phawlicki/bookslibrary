package pl.atos.logic;


import org.junit.Before;
import org.junit.Test;
import pl.atos.model.Author;
import pl.atos.model.Book;
import pl.atos.model.Customer;
import pl.atos.model.Status;
import pl.atos.persistence.InMemoryDatabaseImpl;

import java.time.Year;

import static org.junit.Assert.assertTrue;

public class BookStatusTest {
    private InMemoryDatabaseImpl inMemoryDatabase = new InMemoryDatabaseImpl();
    private BookStatus bookStatus = new BookStatus(inMemoryDatabase);
    private BookFactory bookFactory = new BookFactory(inMemoryDatabase);


    @Before
    public void setup() {
        inMemoryDatabase.removeAllBooks();
        Author author = new Author("Henryk", "Sienkiewicz");
        Book book = bookFactory.create("W pustyni i w puszczy", author, Status.FREE, Year.of(1890), null);
        inMemoryDatabase.add(book);
        Book book1 = bookFactory.create("Harry Potter", new Author("J.K", "Rowling"), Status.FREE, Year.of(2000), null);
        inMemoryDatabase.add(book1);
        Book book2 = bookFactory.create("Harry Potter", new Author("J.K", "Rowling"), Status.LOAN, Year.of(2000), null);
        inMemoryDatabase.add(book2);
    }


    @Test
    public void shouldRentBook() {
        //given
        Customer reader = new Customer("Adam", "Paluch");
        //when
        bookStatus.rentBook(1, reader);
        Book book = inMemoryDatabase.getBookById(1);

        //then
        assertTrue(book.getStatus() == Status.LOAN);
        assertTrue(book.getReader().equals(reader));
    }

    @Test(expected = NullPointerException.class)
    public void shouldReturnBook() {
        //given

        //when
        bookStatus.returnBook(3);
        Book book = inMemoryDatabase.getBookById(3);

        //then
        assertTrue(book.getStatus() == Status.FREE);
        assertTrue(book.getReader().equals(null));
    }

}