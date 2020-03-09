package pl.atos.logic.visitor;

import pl.atos.model.Book;
import pl.atos.model.Status;

public class FreeBooksVisitor implements Visitor {
    @Override
    public int visit(Book book, String title) {
        int counter = 0;
        if (book.getTile().equals(title) && book.getStatus().equals(Status.FREE)) {
            counter = 1;
        }
        return counter;
    }
}
