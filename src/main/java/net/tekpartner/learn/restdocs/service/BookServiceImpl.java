package net.tekpartner.learn.restdocs.service;


import net.tekpartner.learn.restdocs.model.Book;
import net.tekpartner.learn.restdocs.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Chandrashekar R. Gaajula
 */
@Service("bookService")
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Optional<Book> findBookById(Long id) {
        Assert.notNull(id, "ID must not be null");
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Book save(Book book) {
        Assert.notNull(book, "Book must not be null");
        return bookRepository.saveAndFlush(book);
    }

    @Override
    @Transactional
    public boolean deleteBookById(Long id) {
        Assert.notNull(id, "ID must not be null");
        Optional<Book> book = bookRepository.findById(id);
        bookRepository.delete(book.get());

        return true;
    }
}
