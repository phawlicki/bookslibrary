package pl.atos.logic;

import pl.atos.model.Author;
import pl.atos.model.Book;
import pl.atos.model.Customer;
import pl.atos.model.Status;
import pl.atos.persistence.interfaces.IdGenerator;

import java.time.Year;

public class BookFactory {
    private IdGenerator idGenerator;

    public BookFactory(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public Book create(String title, Author author, Status status, Year year, Customer reader) {
        int generate = idGenerator.generate();
        int id = generate;
        return new Book(id, title, author, status, year, reader);
    }

}
