package pl.atos.model;

import java.time.Year;

public class Book {
    private int id;
    private String tile;
    private Author author;
    private Status status;
    private Year year;
    private Customer reader;

    public Book(int id, String tile, Author author, Status status, Year year, Customer reader) {
        this.id = id;
        this.tile = tile;
        this.author = author;
        this.status = status;
        this.year = year;
        this.reader = reader;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Customer getReader() {
        return reader;
    }

    public void setReader(Customer reader) {
        this.reader = reader;
    }
}
