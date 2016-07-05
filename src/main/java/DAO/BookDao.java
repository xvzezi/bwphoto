package DAO;

import java.util.List;

import model.db.Book;

public interface BookDao{
    public Book FindBookByISBN(String ISBN);
    public List<Book> FindBookByName(String name);
    public List<Book> FindBookByAuthor(String author);
    public List<Book> FindBookByNameAndAuthor(String name,String author);
    public void updateBook(Book book);
    public void deleteBook(Book book);
    public void saveObject(Book book);
    
}
