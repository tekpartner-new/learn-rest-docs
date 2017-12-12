package net.tekpartner.learn.restdocs.repository;

import net.tekpartner.learn.restdocs.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
