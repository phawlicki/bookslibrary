package pl.atos.persistence;

import pl.atos.logic.visitor.FreeBooksVisitor;
import pl.atos.logic.visitor.LoanBooksVisitor;
import pl.atos.logic.visitor.Visitor;
import pl.atos.model.Author;
import pl.atos.model.Book;
import pl.atos.model.Status;
import pl.atos.persistence.interfaces.IdGenerator;
import pl.atos.persistence.interfaces.InMemoryDatabase;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InMemoryDatabaseImpl implements IdGenerator, InMemoryDatabase {

    private List<Book> booksList = new ArrayList<>();
    private final AtomicInteger counter = new AtomicInteger(1);


    @Override
    public void add(Book book) {
        booksList.add(book);
    }

    @Override
    public void remove(int id) {
        Optional<Book> bookToRemove =
                booksList.stream().filter(book -> book.getId() == id).filter(book -> !book.getStatus().equals(Status.LOAN)).findFirst();
        if (bookToRemove.isPresent()) {
            booksList.remove(bookToRemove.get());
        }
    }

    @Override
    public List<Book> getBooksByAuthor(Author author) {
        return booksList.stream().filter(book -> book.getAuthor().equals(author)).collect(Collectors.toList());
    }

    @Override
    public List<Book> getBooksByYear(Year year) {
        return booksList.stream().filter(book -> book.getYear().equals(year)).collect(Collectors.toList());
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return booksList.stream().filter(book -> book.getTile().equals(title)).collect(Collectors.toList());
    }

    @Override
    public List<Book> getBooksByTitleAndAuthor(String title, Author author) {
        return booksList.stream().filter(bookTitle -> bookTitle.getTile().equals(title)).filter(bookAuthor -> bookAuthor.getAuthor().equals(author))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getDistinctBooks() {
        return booksList.stream().filter(distinctByKey(book -> book.getTile())).collect(Collectors.toList());
    }

    public void printBooks() {
        List<Book> distinctBooks = getDistinctBooks();
        for (Book book : distinctBooks) {
            System.out.println(book.getTile() + ": available books number:" +
                    accept(new FreeBooksVisitor(), booksList, book.getTile()) + "loan books number: " + accept(new LoanBooksVisitor(), booksList, book.getTile()));
        }

    }

    @Override
    public Book getBookById(int id) {
        Optional<Book> book = booksList.stream().filter(b -> b.getId() == id).findFirst();
        if ((book.isPresent())) {
            return book.get();
        }
        return null;
    }


    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @Override
    public int getLastId() {
        if (!booksList.isEmpty()) {
            return booksList.size();
        } else {
            return 0;
        }
    }

    public List<Book> getAllBooks() {
        return booksList;
    }

    @Override
    public int generate() {
        counter.set(getLastId());
        return counter.incrementAndGet();
    }


    public int accept(Visitor visitor, List<Book> books, String title) {
        int sum = 0;
        for (Book book : books) {
            sum = sum + (visitor.visit(book, title));
        }
        return sum;
    }

    public void removeAllBooks() {
        if (!booksList.isEmpty()) {
            booksList.clear();
        }
    }
}
