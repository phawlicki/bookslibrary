package pl.atos.logic.visitor;

import pl.atos.model.Book;

public interface Visitor {

    int visit(Book book, String title);

}
