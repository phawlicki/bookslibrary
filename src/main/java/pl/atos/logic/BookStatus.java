package pl.atos.logic;

import pl.atos.model.Customer;
import pl.atos.model.Status;
import pl.atos.persistence.InMemoryDatabaseImpl;

public class BookStatus {

    private InMemoryDatabaseImpl database;


    public BookStatus(InMemoryDatabaseImpl inMemoryDatabaseImpl) {
        this.database = inMemoryDatabaseImpl;
    }

    public void rentBook(int id, Customer reader) {
        if (database.getBookById(id).getStatus().equals(Status.FREE)) {
            database.getBookById(id).setStatus(Status.LOAN);
            database.getBookById(id).setReader(reader);
        }
    }

    public void returnBook(int id) {
        if (database.getBookById(id).getStatus().equals(Status.LOAN)) {
            database.getBookById(id).setStatus(Status.FREE);
            database.getBookById(id).setReader(null);
        }
    }
}
