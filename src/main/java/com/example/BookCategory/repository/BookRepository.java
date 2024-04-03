package com.example.BookCategory.repository;

import com.example.BookCategory.entity.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BookRepository extends JpaRepository<Book, Long> , JpaSpecificationExecutor<Book> {
    @Query(value = "SELECT * FROM books WHERE title LIKE %:title% AND author LIKE %:author% LIMIT 1", nativeQuery = true)
    Book findBooksByTitleAndAuthor(@Param("title") String title, @Param("author") String author);
    Optional<Book> findBookByTitle(String title);
}
