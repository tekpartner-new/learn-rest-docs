package net.tekpartner.learn.restdocs.service;


import net.tekpartner.learn.restdocs.model.Book;

import java.util.List;
import java.util.Optional;

/**
 * @author Chandrashekar R. Gaajula
 */
public interface BookService {

    Optional<Book> findBookById(Long id);

    List<Book> getAllBooks();

    Book save(Book book);

    boolean deleteBookById(Long id);
}
